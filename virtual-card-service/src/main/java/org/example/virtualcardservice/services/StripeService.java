package org.example.virtualcardservice.services;

import com.stripe.Stripe;
import com.stripe.model.issuing.Card;
import com.stripe.param.issuing.CardCreateParams;
import org.example.virtualcardservice.feign.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Autowired
    private UserServiceClient userServiceClient;

    // Inject API key from application.properties or application.yml
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public Card createCard(Long userId, String currency, String type, String status, Map<String, String> metadata) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;
        String cardholderId=userServiceClient.getCardholderIdByUserId(userId);

        try {
            // Define parameters for card creation
            CardCreateParams params = CardCreateParams.builder()
                    .setCardholder(cardholderId)  // Associate card with the specified cardholder
                    .setCurrency(currency)        // Currency for the card
                    .setType(CardCreateParams.Type.VIRTUAL)                // Type of card (virtual)
                    .setStatus(CardCreateParams.Status.ACTIVE)            // Status of the card (active)
                    .putAllMetadata(metadata)     // Additional metadata
                    .build();

            // Create the card using Stripe API
            return Card.create(params);
        } catch (Exception e) {
            throw new RuntimeException("Error creating card", e);
        }
    }
}
