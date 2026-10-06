package com.example.Car.Services.service.GarageOwner;

import com.example.Car.Services.Repository.OwnerSubscriptionRepository;
import com.example.Car.Services.entities.OWNER_SUBSCRIPTION;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeSubscriptionService {

    private final OwnerSubscriptionRepository ownerSubscriptionRepository;

    public String createCheckoutSession(OWNER_SUBSCRIPTION subscription) throws StripeException {

        if (subscription.getAmount().signum() == 0) {
            subscription.setPaymentStatus("FREE");
            ownerSubscriptionRepository.save(subscription);
            return null;
        }

        long amountInMinorUnit = subscription.getAmount()
                .movePointRight(3)
                .longValue();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/payment-success")
                .setCancelUrl("http://localhost:4200/payment-cancel")
                .setClientReferenceId(subscription.getPaymentReference())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("omr")
                                                .setUnitAmount(amountInMinorUnit)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(subscription.getPlan().getPlanName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        subscription.setStripeSessionId(session.getId());
        subscription.setPaymentStatus("PENDING");

        ownerSubscriptionRepository.save(subscription);

        return session.getUrl();
    }
}
