package org.example.servicepaymenttransaction.Services;

import org.example.servicepaymenttransaction.Models.Compte;
import org.example.servicepaymenttransaction.Models.Transaction;
import org.example.servicepaymenttransaction.Repositories.CompteRepository;
import org.example.servicepaymenttransaction.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private CompteRepository compteRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    // Simuler une liste de créanciers
    private static final Map<Long, String> CREANCIERS = new HashMap<>() {{
        put(1L, "Electricité Nationale");
        put(2L, "Compagnie des Eaux");
        put(3L, "Opérateur Télécom");
    }};

    // Payer une facture à un créancier
    public Transaction payerFacture(Long compteId, BigDecimal montant, Long creancierId) {
        Compte compte = compteRepo.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        String nomCreancier = CREANCIERS.get(creancierId);
        if (nomCreancier == null) {
            throw new RuntimeException("Créancier non trouvé");
        }

        if (compte.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant. Solde actuel : "
                    + compte.getSolde() + ", Montant demandé : " + montant);
        }

        // Débiter le solde du compte
        compte.setSolde(compte.getSolde().subtract(montant));
        compteRepo.save(compte);

        // Créer la transaction pour le paiement du créancier
        Transaction transaction = new Transaction();
        transaction.setMontant(montant.negate());
        transaction.setType("paiement");
        transaction.setDescription("Paiement effectué au créancier : " + nomCreancier);
        transaction.setDate(new Date());
        transaction.setStatut("effectuée");
        transaction.setCompte(compte);
        transaction.setSourceUserId(compte.getUserId()); // Définir sourceUserId
        transaction.setDestinationUserId(creancierId);   // Définir destinationUserId

        return transactionRepo.save(transaction);
    }


    // Effectuer un paiement
    public Transaction effectuerPaiement(Long compteId, BigDecimal montant, String typePaiement) {
        Compte compte = compteRepo.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (compte.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant. Solde actuel : "
                    + compte.getSolde() + ", Montant demandé : " + montant);
        }

        // Mettre à jour le solde du compte
        compte.setSolde(compte.getSolde().subtract(montant));
        compteRepo.save(compte);

        // Créer une transaction avec sourceUserId défini
        Transaction transaction = new Transaction();
        transaction.setMontant(montant.negate());
        transaction.setType(typePaiement);
        transaction.setDescription("Paiement effectué");
        transaction.setDate(new Date());
        transaction.setStatut("effectuée");
        transaction.setCompte(compte);
        transaction.setSourceUserId(compte.getUserId()); // Définir le sourceUserId

        return transactionRepo.save(transaction);
    }


    // Réception de fonds
    public Transaction recevoirPaiement(Long compteId, BigDecimal montant) {
        Compte compte = compteRepo.findById(compteId).orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        compte.setSolde(compte.getSolde().add(montant));
        compteRepo.save(compte);

        Transaction transaction = new Transaction();
        transaction.setMontant(montant); // Enregistrer en positif
        transaction.setType("reception");
        transaction.setDescription("Fonds reçus");
        transaction.setDate(new Date());
        transaction.setStatut("effectuée");
        transaction.setCompte(compte);

        return transactionRepo.save(transaction);
    }

    // Méthode pour transférer de l'argent entre deux comptes
    public List<Transaction> transfererArgent(Long sourceId, Long destinationId, BigDecimal montant) {
        Compte source = compteRepo.findById(sourceId)
                .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));
        Compte destination = compteRepo.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Compte destination non trouvé"));

        if (source.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant. Solde actuel : " + source.getSolde() + ", Montant demandé : " + montant);
        }

        // Débiter le compte source
        source.setSolde(source.getSolde().subtract(montant));
        compteRepo.save(source);

        Transaction transactionDebit = new Transaction();
        transactionDebit.setMontant(montant.negate());
        transactionDebit.setType("transfert");
        transactionDebit.setDescription("Transfert à l'utilisateur ID : " + destination.getUserId());
        transactionDebit.setDate(new Date());
        transactionDebit.setStatut("effectuée");
        transactionDebit.setCompte(source);
        transactionDebit.setSourceUserId(source.getUserId());
        transactionDebit.setDestinationUserId(destination.getUserId());
        transactionRepo.save(transactionDebit);

        // Créditer le compte destination
        destination.setSolde(destination.getSolde().add(montant));
        compteRepo.save(destination);

        Transaction transactionCredit = new Transaction();
        transactionCredit.setMontant(montant);
        transactionCredit.setType("reception");
        transactionCredit.setDescription("Réception de l'utilisateur ID : " + source.getUserId());
        transactionCredit.setDate(new Date());
        transactionCredit.setStatut("effectuée");
        transactionCredit.setCompte(destination);
        transactionCredit.setSourceUserId(source.getUserId());
        transactionCredit.setDestinationUserId(destination.getUserId());
        transactionRepo.save(transactionCredit);

        return List.of(transactionDebit, transactionCredit);
    }

//    public List<Transaction> listerTransactions(Long compteId) {
//        return transactionRepo.findByCompteId(compteId);
//    }
// Liste des transactions pour un utilisateur spécifique
public List<Transaction> listerTransactionsParUserId(Long userId) {
    return transactionRepo.findByCompteUserId(userId);
}

    public BigDecimal consulterSolde(Long compteId) {
        Compte compte = compteRepo.findById(compteId).orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return compte.getSolde();
    }
}
