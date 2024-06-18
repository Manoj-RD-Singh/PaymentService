package com.ecommerce.paymentservice.configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.client.key}")
    private String razorpayKey;

    @Value("${razorpay.client.secret}")
    private String razorpaySecret;

    @Bean
    public RazorpayClient getRazorPayClient() throws RazorpayException {
        return new RazorpayClient(razorpayKey, razorpaySecret);
    }
}
