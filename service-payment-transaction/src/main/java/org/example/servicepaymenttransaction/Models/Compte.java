package org.example.servicepaymenttransaction.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // ID de l'utilisateur associé au compte
    private BigDecimal solde;
    @Column(unique = true)
    private String telephone; // Numéro de téléphone associé au compte

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
