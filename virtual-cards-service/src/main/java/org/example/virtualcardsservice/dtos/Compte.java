package org.example.virtualcardsservice.dtos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Compte {
    private Long id;

    private Long userId; // ID de l'utilisateur associé au compte
    private BigDecimal solde;

    private String telephone; // Numéro de téléphone associé au compte

    private Hssab hssab;

    private List<String> virtual_cards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Hssab getHssab() {
        return hssab;
    }

    public void setHssab(Hssab hssab) {
        this.hssab = hssab;
    }

    public List<String> getVirtual_cards() {
        return virtual_cards;
    }

    public void setVirtual_cards(List<String> virtual_cards) {
        this.virtual_cards = virtual_cards;
    }
}
