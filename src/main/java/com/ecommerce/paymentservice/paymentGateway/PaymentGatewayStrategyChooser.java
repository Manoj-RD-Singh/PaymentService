package com.ecommerce.paymentservice.paymentGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayStrategyChooser {

    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    public PaymentGateway getPaymentGateway(){
        return razorpayPaymentGateway;
    }

}
