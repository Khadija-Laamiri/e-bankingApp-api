package com.example.operators_api.services;

import com.example.operators_api.entitie.Collaborator;
import com.example.operators_api.entitie.MarocTelecom;
import com.example.operators_api.repositories.CollaboratorRepository;
import com.example.operators_api.repositories.MarocTelecomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RechargeService {

    private final MarocTelecomRepository marocTelecomRepo;
    private final CollaboratorRepository collaboratorRepo;

    public RechargeService(MarocTelecomRepository marocTelecomRepo, CollaboratorRepository collaboratorRepo) {
        this.marocTelecomRepo = marocTelecomRepo;
        this.collaboratorRepo = collaboratorRepo;
    }

    @Transactional
    public String recharge(Long collaboratorId, String phoneNumber, double amount) {
        Collaborator collaborator = collaboratorRepo.findById(collaboratorId)
                .orElseThrow(() -> new IllegalArgumentException("Collaborator not found"));

        MarocTelecom marocTelecom = marocTelecomRepo.findById(1L) // Assuming one MarocTelecom entity
                .orElseThrow(() -> new IllegalArgumentException("MarocTelecom not found"));

        if (!marocTelecom.getPhoneNumbers().contains(phoneNumber)) {
            return "Phone number not found in database";
        }

        double totalCost = amount * 0.98; // 2% reduction
        if (collaborator.getBalance() < totalCost) {
            return "Insufficient balance";
        }

        collaborator.setBalance(collaborator.getBalance() - totalCost);
        collaboratorRepo.save(collaborator);

        return "Recharge successful. Amount: " + amount + " MAD, Discounted: " + totalCost + " MAD";
    }
}