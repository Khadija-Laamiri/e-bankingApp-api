package org.example.servicepaymenttransaction.Controllers;

import org.example.servicepaymenttransaction.Models.Compte;
import org.example.servicepaymenttransaction.Models.Hssab;
import org.example.servicepaymenttransaction.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/comptes")
public class CompteController {
    @Autowired
    private CompteService compteService;

    // Endpoint pour créer un compte avec un solde initial
    @PostMapping("/creer")
    public ResponseEntity<Compte> creerCompte(@RequestParam Long userId,
                                              @RequestParam BigDecimal soldeInitial,
                                              @RequestParam(required = false, defaultValue = "HSSAB_200") String hssab) {
        // Convertir le paramètre `hssab` en type Hssab
        Hssab typeCompte;
        try {
            typeCompte = Hssab.valueOf(hssab.toUpperCase()); // Conversion en majuscules pour éviter les erreurs
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null); // Si la valeur est invalide, retourner une erreur
        }

        // Créer le compte avec le type (hssab)
        Compte compte = compteService.creerCompte(userId, soldeInitial, typeCompte);
        return ResponseEntity.ok(compte);
    }

    // Endpoint pour supprimer un compte par son ID
    @DeleteMapping("/supprimer/{compteId}")
    public ResponseEntity<String> supprimerCompte(@PathVariable Long compteId) {
        compteService.supprimerCompte(compteId);
        return ResponseEntity.ok("Compte supprimé avec succès");
    }
    // Gestion des exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
