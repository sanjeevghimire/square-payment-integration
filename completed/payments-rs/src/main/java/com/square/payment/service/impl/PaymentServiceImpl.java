package com.square.payment.service.impl;

import com.square.payment.dto.PaymentRequest;
import com.square.payment.dto.PaymentResponse;
import com.square.payment.service.PaymentService;
import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.api.PaymentsApi;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.CreatePaymentRequest;
import com.squareup.square.models.CreatePaymentResponse;
import com.squareup.square.models.Money;
import com.squareup.square.models.RetrieveLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${square.environment}")
    private String squareEnvironment;

    @Value("${square.app.id}")
    private String squareAppId;

    @Value("${square.location.id}")
    private String squareLocationId;

    @Value("${square.access.token}")
    private String squareAccessToken;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws ExecutionException,
            InterruptedException {

        /**
         * 1. Create square client
         */
        SquareClient squareClient = new SquareClient.Builder()
                .environment(Environment.SANDBOX)
                .accessToken(squareAccessToken)
                .build();

        /**
         * 2. Retrieve currency based on your location id
         */
        RetrieveLocationResponse locationResponse = getLocationInformation(squareClient, request.getLocationId()).get();
        String currency = locationResponse.getLocation().getCurrency();
        log.debug("Request to charge {} in currency {}", request.getAmount(), currency);

        /**
         * 3. build money object
         */
        Money amountMoney = new Money.Builder()
                .amount(request.getAmount())
                .currency(currency)
                .build();

        /**
         * 4. Create Payment request
         * params it takes are: token, Idempotent key, amount to charge
         */
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(
                request.getToken(),
                UUID.randomUUID().toString(),
                amountMoney)
                .build();

        /**
         * 5. call create payment API using the sdk
         */
        PaymentsApi paymentsApi = squareClient.getPaymentsApi();
        PaymentResponse response = null;
        try {
            CreatePaymentResponse createPaymentResponse = paymentsApi.createPayment(createPaymentRequest);
            if(createPaymentResponse.getErrors() == null || createPaymentResponse.getErrors().size() > 0){
                response = new PaymentResponse("FAILURE", createPaymentResponse.getErrors());
            }
            response = new PaymentResponse("SUCCESS", null);
        } catch (ApiException e) {
            log.error("API error : {}",e.getMessage());
            response = new PaymentResponse("FAILURE", e.getErrors());
        } catch (IOException e) {
            log.error("API error : {}",e.getMessage());
            response = new PaymentResponse("FAILURE", null);
        }
        return response;
    }

    /**
     * Helper method that makes a retrieveLocation API call using the configured locationId and
     * returns the future containing the response
     *
     * @param squareClient the API client
     * @return a future that holds the retrieveLocation response
     */
    private CompletableFuture<RetrieveLocationResponse> getLocationInformation(
            SquareClient squareClient, String squareLocationId) {
        return squareClient.getLocationsApi().retrieveLocationAsync(squareLocationId)
                .thenApply(result -> result)
                .exceptionally(exception -> {
                    log.error("Failed to make the request");
                    log.error("Exception: {}", exception.getMessage());
                    return null;
                });
    }
}
