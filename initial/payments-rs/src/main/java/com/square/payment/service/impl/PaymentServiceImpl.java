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

    /**
     * Access the values from application properties
     */
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
        

        /**
         * 2. Retrieve currency based on your location id
         */
        

        /**
         * 3. build money object
         */
        

        /**
         * 4. Create Payment request
         * params it takes are: token, Idempotent key, amount to charge
         */
        

        /**
         * 5. call create payment API using the sdk
         */
        
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
