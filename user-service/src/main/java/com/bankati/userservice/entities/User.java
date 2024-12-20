package com.bankati.userservice.entities;

import jakarta.persistence.*;
import lombok.*;
import com.bankati.userservice.enums.Role;
import com.bankati.userservice.enums.TypePieceIdentite;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Enumerated(EnumType.STRING)
    private TypePieceIdentite typePieceIdentite;

    private String numeroPieceIdentite;
    private LocalDate dateDeNaissance;
    private String adresse;
    @Column(unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String numeroTelephone;
    private String numeroImmatriculation;
    private String numeroPatente;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String imageRecto;
    private String imageVerso;

    private boolean isActive = true;
}
