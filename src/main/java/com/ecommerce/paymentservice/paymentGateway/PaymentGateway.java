package com.ecommerce.paymentservice.paymentGateway;

import org.springframework.stereotype.Component;


public interface PaymentGateway {
    String generatePayLink(String name, String email, String phoneNumber, String orderId, Long amount);
}
