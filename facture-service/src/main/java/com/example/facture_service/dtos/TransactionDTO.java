package com.example.facture_service.dtos;

import com.example.facture_service.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal montant;
    private String type; // "paiement", "reception", "transfert"
    private String description;
    private Date date;
    private String statut; // "effectuée", "échouée"
    private Long sourceUserId;       // ID de l'expéditeur
    private Long destinationUserId;  // ID du destinataire
    private TransactionType transactionType;
    private Long compteId;           // ID of the associated Compte
}
