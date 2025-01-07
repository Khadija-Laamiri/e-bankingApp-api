package org.example.servicepaymenttransaction.Services;

import org.example.servicepaymenttransaction.Models.Compte;
import org.example.servicepaymenttransaction.Models.Hssab;
import org.example.servicepaymenttransaction.Repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.List;
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

        // Déterminer la valeur de `hssab` en fonction du soldeInitial
        Hssab hssabType;
        if (soldeInitial.compareTo(BigDecimal.valueOf(200)) == 0) {
            hssabType = Hssab.HSSAB_200;
        } else if (soldeInitial.compareTo(BigDecimal.valueOf(5000)) == 0) {
            hssabType = Hssab.HSSAB_5000;
        } else if (soldeInitial.compareTo(BigDecimal.valueOf(20000)) == 0) {
            hssabType = Hssab.HSSAB_20000;
        } else {
            throw new RuntimeException("Le solde initial ne correspond à aucun type de compte valide.");
        }

        // Créer un nouveau compte si l'utilisateur n'en a pas déjà un
        Compte compte = new Compte();
        compte.setUserId(userId);
        compte.setSolde(soldeInitial);
        compte.setHssab(hssabType); // Définir le type de compte

        return compteRepo.save(compte);
    }

    // Supprimer un compte par son ID
    public void supprimerCompte(Long compteId) {
        Compte compte = compteRepo.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + compteId));

        compteRepo.delete(compte);
    }

    public Compte addVirtualCard(Long userId, String newCard) {
        // Rechercher le compte par ID
        Compte compte = compteRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + userId));

        // Ajouter la nouvelle carte à la liste existante
        List<String> virtualCards = compte.getVirtual_cards();
        virtualCards.add(newCard);
        compte.setVirtual_cards(virtualCards);

        // Enregistrer les modifications dans la base de données
        return compteRepo.save(compte);
    }

    public Optional<Compte> getCompteByUserId(Long userId){
        return compteRepo.findByUserId(userId);
    }


}





























