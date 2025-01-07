package org.example.servicepaymenttransaction.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.servicepaymenttransaction.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montant;
    private String type; // "paiement", "reception", "transfert"
    private String description;
    private Date date;
    private String statut; // "effectuée", "échouée"

    private Long sourceUserId;       // ID de l'expéditeur
    private Long destinationUserId;  // ID du destinataire

    @Enumerated(EnumType.STRING) // Store the enum as a string in the database
    private TransactionType transactionType;

    @ManyToOne
    private Compte compte;
    @Transient
    private Long operatorId;
}
