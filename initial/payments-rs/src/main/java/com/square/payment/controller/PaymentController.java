package com.square.payment.controller;


import com.square.payment.dto.PaymentRequest;
import com.square.payment.dto.PaymentResponse;
import com.square.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/payment")
public class PaymentController {

    private final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process-payment")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) throws ExecutionException, InterruptedException {
      log.info("Request to charge {}", request.getAmount());
      return paymentService.processPayment(request);
    }
}
