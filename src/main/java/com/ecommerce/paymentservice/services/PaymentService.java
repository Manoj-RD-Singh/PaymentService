package com.ecommerce.paymentservice.services;

import com.ecommerce.paymentservice.paymentGateway.PaymentGatewayStrategyChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentGatewayStrategyChooser paymentGatewayStrategyChooser;

    public String initiatePayment(String name, String email, String phoneNumber, String orderId, Long amount){
       return paymentGatewayStrategyChooser.getPaymentGateway().generatePayLink(name, email, phoneNumber, orderId, amount);

    }
}
