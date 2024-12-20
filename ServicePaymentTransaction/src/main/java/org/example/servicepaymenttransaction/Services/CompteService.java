package org.example.servicepaymenttransaction.Services;

import org.example.servicepaymenttransaction.Models.Compte;
import org.example.servicepaymenttransaction.Repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.Optional;


@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepo;

    // Créer un compte pour un utilisateur avec un solde initial
    public Compte creerCompte(Long userId, BigDecimal soldeInitial) {
        // Vérifier si un compte existe déjà pour cet utilisateur
        Optional<Compte> compteExistant = compteRepo.findByUserId(userId);
        if (compteExistant.isPresent()) {
            throw new RuntimeException("L'utilisateur avec l'ID " + userId + " possède déjà un compte.");
        }

        // Créer un nouveau compte si l'utilisateur n'en a pas déjà un
        Compte compte = new Compte();
        compte.setUserId(userId);
        compte.setSolde(soldeInitial);

        return compteRepo.save(compte);
    }

    // Supprimer un compte par son ID
    public void supprimerCompte(Long compteId) {
        Compte compte = compteRepo.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + compteId));

        compteRepo.delete(compte);
    }
}
