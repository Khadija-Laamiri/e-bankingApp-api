package org.example.virtualcardsservice.controllers;



import com.stripe.model.issuing.Card;
import org.example.virtualcardsservice.dtos.CardDTO;
import org.example.virtualcardsservice.services.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/virtual-cards")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    // Create a virtual card
    @PostMapping("/create")
    public ResponseEntity<CardDTO> createCard(
            @RequestParam Long userId,
            @RequestParam String currency,
            @RequestParam BigDecimal solde) {
        try {
            // Convert solde to metadata map or directly pass solde if your service handles it differently
            Map<String, String> metadata = new HashMap<>();
            metadata.put("solde", solde.toString());

            // Call the service with the userId, currency, and metadata containing solde
            CardDTO card = stripeService.createCard(userId, currency, metadata);
            return ResponseEntity.ok(card);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // Retrieve a card by its ID
    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable String cardId) {
        try {
            CardDTO card = stripeService.getCardById(cardId);
            return ResponseEntity.ok(card);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all cards for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardDTO>> getUserCards(@PathVariable Long userId) {
        try {
            List<CardDTO> cards = stripeService.getUserCards(userId);
            return ResponseEntity.ok(cards);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update the status of a card
    @PutMapping("/{cardId}/status")
    public ResponseEntity<Card> updateCardStatus(
            @PathVariable String cardId,
            @RequestParam String status) {
        try {
            Card updatedCard = stripeService.updateCardStatus(cardId, status);
            return ResponseEntity.ok(updatedCard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update the metadata of a card
    @PostMapping("/update-card-metadata")
    public ResponseEntity<Card> updateCardMetadata(
            @RequestParam String cardId,
            @RequestParam Long userId,
            @RequestParam BigDecimal montant) {
        try {
            Card updatedCard = stripeService.updateCardMetadata(cardId, userId, montant);
            return ResponseEntity.ok(updatedCard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
