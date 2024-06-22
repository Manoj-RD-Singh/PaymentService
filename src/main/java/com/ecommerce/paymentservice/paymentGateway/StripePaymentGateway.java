package com.ecommerce.paymentservice.paymentGateway;

import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StripePaymentGateway implements PaymentGateway{

    @Value("${stripe.api.secret}")
    private String stripApiSecret;


    @Override
    public String generatePayLink(String name, String email, String phoneNumber, String orderId,
                                  Long amount) {

        try{
            Stripe.apiKey = stripApiSecret;
            Price price = createPrice(amount, orderId);
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                            .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                            .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                    .setUrl("https://scaler.com/")
                                                    .build())
                                            .build())
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        }catch(Exception ex){
            log.error("Strip api error while creating pay link :", ex);
            throw new RuntimeException(ex);
        }
    }

    private Price createPrice(Long amount, String orderId){
        try{
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName(orderId).build()
                            )
                            .build();

            Price price = Price.create(params);
            return price;
        }catch(Exception ex){
            log.error("Error while strip price creation : ", ex);
            throw new RuntimeException(ex);
        }

    }
}
