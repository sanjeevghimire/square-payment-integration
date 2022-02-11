package com.square.payment.service;

import com.square.payment.dto.PaymentRequest;
import com.square.payment.dto.PaymentResponse;

import java.util.concurrent.ExecutionException;


public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request) throws InterruptedException, ExecutionException;
}
