package com.ecommerce.paymentservice.controllers;

import com.ecommerce.paymentservice.dtos.InitiatePaymentRequestDTO;
import com.ecommerce.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/getPayLink")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDTO initiatePaymentRequestDTO){
        String payLink = paymentService.initiatePayment(initiatePaymentRequestDTO.getName(),
                initiatePaymentRequestDTO.getEmail(),
                initiatePaymentRequestDTO.getPhoneNumber(),
                initiatePaymentRequestDTO.getOrderId(),
                initiatePaymentRequestDTO.getAmount());

        return payLink;
    }

}
