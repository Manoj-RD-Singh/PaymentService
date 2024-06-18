package com.ecommerce.paymentservice.paymentGateway;

import com.razorpay.PaymentLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
@Slf4j
public class RazorpayPaymentGateway implements PaymentGateway{

    @Autowired
    RazorpayClient razorpay;

    @Override
    public String generatePayLink(String name, String email, String phoneNumber, String orderId, Long amount) {

        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", true);
            paymentLinkRequest.put("first_min_partial_amount", 100);
            paymentLinkRequest.put("expire_by", 1721241000); //Important - 18 July 24 expiry
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for order no "+orderId);
            JSONObject customer = new JSONObject();
            customer.put("name", phoneNumber);
            customer.put("contact", name);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
            notes.put("policy_name", "Jeevan Bima");
            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            return payment.get("short_url").toString(); //Important
        }catch(RazorpayException razorpayException){
            log.error("Razorpay error while generating pay link");
            throw new RuntimeException(razorpayException);

        }

    }
}
