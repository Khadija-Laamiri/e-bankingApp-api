package org.example.virtualcardsservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Card;
import com.stripe.param.issuing.CardCreateParams;
import com.stripe.param.issuing.CardUpdateParams;
import jakarta.transaction.Transactional;
import org.example.virtualcardsservice.dtos.CardDTO;
import org.example.virtualcardsservice.dtos.Compte;
import org.example.virtualcardsservice.dtos.Hssab;
import org.example.virtualcardsservice.entities.CardTransaction;
import org.example.virtualcardsservice.feign.ServicePayementClient;
import org.example.virtualcardsservice.feign.UserServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StripeService {

    private final RestTemplate restTemplate;
    private final UserServiceClient userServiceClient;
    private final ServicePayementClient servicePayementClient;
    private final CardTransactionService cardTransactionService;

    // Inject API key from application.properties or application.yml
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public StripeService(RestTemplate restTemplate, UserServiceClient userServiceClient, ServicePayementClient servicePayementClient, CardTransactionService cardTransactionService) {
        this.restTemplate = restTemplate;
        this.userServiceClient = userServiceClient;
        this.servicePayementClient = servicePayementClient;
        this.cardTransactionService = cardTransactionService;
    }


    public CardDTO createCard(Long userId, String currency, Map<String, String> metadata) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;
        System.out.println(userId);

        String cardholderId = userServiceClient.getCardholderIdByUserId(userId);
        System.out.println(cardholderId);
        // Fetch the Compte associated with the user
        Compte compte = servicePayementClient.getCompteByUserId(userId).getBody();
        System.out.println(compte);

        if (compte == null) {
            throw new RuntimeException("Compte not found for userId: " + userId);
        }

        // Check the user's Hssab type and the number of existing virtual cards
        Hssab hssab = compte.getHssab();
        List<String> virtualCards = compte.getVirtual_cards();
        int maxCardsAllowed = switch (hssab) {
            case HSSAB_200 -> 1;
            case HSSAB_5000 -> 3;
            case HSSAB_20000 -> 5;
            default -> throw new RuntimeException("Unknown Hssab type for userId: " + userId);
        };
        System.out.println(virtualCards);
        // Convert solde from metadata to BigDecimal
        BigDecimal solde = new BigDecimal(metadata.get("solde"));

        // Perform the transfer with the BigDecimal amount
        servicePayementClient.transferToCard(userId, solde);

        if (virtualCards != null && virtualCards.size() >= maxCardsAllowed) {
            throw new RuntimeException("Maximum number of virtual cards reached for userId: " + userId);
        }

        // Proceed to create the card if the user has not exceeded the limit

        try {
            CardCreateParams params = CardCreateParams.builder()
                    .setCardholder(cardholderId)
                    .setCurrency(currency)
                    .setType(CardCreateParams.Type.VIRTUAL)
                    .setStatus(CardCreateParams.Status.ACTIVE)
                    .putAllMetadata(metadata)
                    .build();

            Card card = Card.create(params);
            CardDTO cardDTO=CardDTO.convertToDTO(card);

            // Create and save the card transaction
            CardTransaction cardTransaction = new CardTransaction();
            cardTransaction.setAmount(solde); // Use BigDecimal solde
            cardTransaction.setCardId(cardDTO.getId());

            cardTransactionService.add(cardTransaction);

            // Add the generated card to the user's Compte
            servicePayementClient.addVirtualCardByUserId(userId, cardDTO.getId());

            return cardDTO;
        } catch (StripeException e) {
            throw new RuntimeException("Error creating card with Stripe API", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating card", e);
        }
    }



    public CardDTO getCardById(String cardId) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;

        try {

            // Retrieve the card by its ID
            Card card = Card.retrieve(cardId);
            // Convert the Card object to a JsonNode
            return CardDTO.convertToDTO(card);  // Converts the Card to a JsonNode
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving card by ID: " + cardId, e);
        }
    }

    public CardDTO getCardByIdWithCredentials(String cardId) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("expand", List.of("cvc","number"));

            // Retrieve the card by its ID
            Card card = Card.retrieve(cardId,params,null);
            CardDTO cardDTO = CardDTO.convertToDTO(card);
            // Convert the Card object to a JsonNode
            return cardDTO;  // Converts the Card to a JsonNode
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving card by ID: " + cardId, e);
        }
    }



    public List<CardDTO> getUserCards(Long userId) {
        // Retrieve the Compte by User ID
        Compte compte = servicePayementClient.getCompteByUserId(userId).getBody();

        // Initialize an empty list to store the user's cards
        ArrayList<CardDTO> cards = new ArrayList<>();

        // Check if the Compte object and virtual_cards list are not null
        if (compte != null && compte.getVirtual_cards() != null) {
            for (String cardId : compte.getVirtual_cards()) {
                try {
                    // Retrieve the card by its ID
                    CardDTO card = getCardById(cardId);

                    // If card is found, add it to the list
                    if (card != null) {
                        cards.add(card);
                    }
                } catch (Exception e) {
                    // Log the error and continue with the next card
                    System.err.println("Error retrieving card with ID " + cardId + ": " + e.getMessage());
                }
            }
        } else {
            System.err.println("Compte or virtual_cards list is null for userId: " + userId);
        }

        // Return the list of cards
        return cards;
    }


    // Method to update the card status
    public Card updateCardStatus(String cardId, String newStatus) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;

        try {
            // Define parameters to update the card status
            CardUpdateParams params = CardUpdateParams.builder()
                    .setStatus(CardUpdateParams.Status.valueOf(newStatus.toUpperCase())) // Set the new status
                    .build();

            // Retrieve the card and update its status
            Card card = Card.retrieve(cardId);
            card = card.update(params);

            return card;
        } catch (Exception e) {
            throw new RuntimeException("Error updating card status", e);
        }
    }

    @Transactional
    public Card updateCardMetadata( String cardId,Long userId, BigDecimal montant) {
        // Set the Stripe API key
        Stripe.apiKey = stripeApiKey;

        // Fetch the Compte associated with the user
        Compte compte = servicePayementClient.getCompteByUserId(userId).getBody();

        if (compte == null) {
            throw new RuntimeException("Compte not found for userId: " + userId);
        }

        // Perform the transfer with the BigDecimal amount
        servicePayementClient.transferToCard(userId, montant);

        // Create and save the card transaction
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount(montant);
        cardTransaction.setCardId(cardId);
        cardTransactionService.add(cardTransaction);

        try {
            // Retrieve the card to access its current metadata
            Card card = Card.retrieve(cardId);

            // Retrieve the current solde from metadata and convert to BigDecimal
            String currentSoldeStr = card.getMetadata().get("solde");
            BigDecimal currentSolde = new BigDecimal(currentSoldeStr);

            // Subtract montant from the current solde
            BigDecimal newSolde = currentSolde.subtract(montant);

            // Define parameters to update the card metadata with the new solde
            Map<String, String> updatedMetadata = new HashMap<>(card.getMetadata());
            updatedMetadata.put("solde", newSolde.toString());

            CardUpdateParams params = CardUpdateParams.builder()
                    .putAllMetadata(updatedMetadata) // Set the new metadata
                    .build();

            // Update the card with the new metadata
            card = card.update(params);

            return card;
        } catch (Exception e) {
            throw new RuntimeException("Error updating card metadata", e);
        }
    }


}




