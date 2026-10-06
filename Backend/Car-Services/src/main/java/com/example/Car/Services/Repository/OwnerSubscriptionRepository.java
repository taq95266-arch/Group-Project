package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.OWNER_SUBSCRIPTION;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerSubscriptionRepository extends JpaRepository<OWNER_SUBSCRIPTION, Long> {

    Optional<OWNER_SUBSCRIPTION> findByPaymentReference(String paymentReference);

    Optional<OWNER_SUBSCRIPTION> findByStripeSessionId(String stripeSessionId);

    Optional<OWNER_SUBSCRIPTION> findByStripePaymentIntentId(String stripePaymentIntentId);
}
