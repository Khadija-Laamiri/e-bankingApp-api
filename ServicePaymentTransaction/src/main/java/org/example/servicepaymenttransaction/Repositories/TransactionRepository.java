package org.example.servicepaymenttransaction.Repositories;

import org.example.servicepaymenttransaction.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByCompteId(Long compteId);
// Trouver les transactions par ID utilisateur
List<Transaction> findByCompteUserId(Long userId);
}
