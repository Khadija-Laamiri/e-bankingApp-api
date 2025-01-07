package org.example.virtualcardsservice.services;

import org.example.virtualcardsservice.entities.CardTransaction;
import org.example.virtualcardsservice.repositories.CardTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardTransactionService {

    private final CardTransactionRepository cardTransactionRepository;

    public CardTransactionService(CardTransactionRepository cardTransactionRepository) {
        this.cardTransactionRepository = cardTransactionRepository;
    }

    // Method to add a new CardTransaction
    public CardTransaction add(CardTransaction cardTransaction) {
        return cardTransactionRepository.save(cardTransaction);
    }

    // Method to get all transactions by cardId
    public List<CardTransaction> getByCardId(String cardId) {
        return cardTransactionRepository.findByCardId(cardId);
    }
}
