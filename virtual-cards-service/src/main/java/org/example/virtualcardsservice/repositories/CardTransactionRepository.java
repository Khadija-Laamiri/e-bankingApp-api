package org.example.virtualcardsservice.repositories;

import org.example.virtualcardsservice.entities.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardTransactionRepository extends JpaRepository<CardTransaction,Long> {

    List<CardTransaction> findByCardId(String cardId);

}
