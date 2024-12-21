package org.example.servicepaymenttransaction.Controllers;

import org.example.servicepaymenttransaction.Models.Transaction;
import org.example.servicepaymenttransaction.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paiements")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{compteId}/payer-facture/{creancierId}")
    public ResponseEntity<Transaction> payerFacture(@PathVariable Long compteId,
                                                    @PathVariable Long creancierId,
                                                    @RequestParam BigDecimal montant) {
        return ResponseEntity.ok(paymentService.payerFacture(compteId, montant, creancierId));
    }

    @PostMapping("/{compteId}/payer")
    public ResponseEntity<Transaction> payer(@PathVariable Long compteId, @RequestParam BigDecimal montant, @RequestParam String typePaiement) {
        return ResponseEntity.ok(paymentService.effectuerPaiement(compteId, montant, typePaiement));
    }

    @PostMapping("/{compteId}/recevoir")
    public ResponseEntity<Transaction> recevoir(@PathVariable Long compteId, @RequestParam BigDecimal montant) {
        return ResponseEntity.ok(paymentService.recevoirPaiement(compteId, montant));
    }

    @PostMapping("/{sourceId}/transfert/{destinationId}")
    public ResponseEntity<List<Transaction>> transferer(@PathVariable Long sourceId,
                                                        @PathVariable Long destinationId,
                                                        @RequestParam BigDecimal montant) {
        return ResponseEntity.ok(paymentService.transfererArgent(sourceId, destinationId, montant));
    }

    @GetMapping("/{compteId}/solde")
    public ResponseEntity<BigDecimal> consulterSolde(@PathVariable Long compteId) {
        return ResponseEntity.ok(paymentService.consulterSolde(compteId));
    }

//    @GetMapping("/{compteId}/transactions")
//    public ResponseEntity<List<Transaction>> listerTransactions(@PathVariable Long compteId) {
//        return ResponseEntity.ok(paymentService.listerTransactions(compteId));
//    }
// Endpoint pour lister les transactions par ID utilisateur

    // Endpoint pour lister les transactions par ID utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> listerTransactionsParUserId(@PathVariable Long userId) {
        List<Transaction> transactions = paymentService.listerTransactionsParUserId(userId);
        return ResponseEntity.ok(transactions);
    }


    // Gestion globale des exceptions pour afficher un message clair
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<
            Map<String, String>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
