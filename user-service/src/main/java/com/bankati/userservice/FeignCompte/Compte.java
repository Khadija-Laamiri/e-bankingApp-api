package com.bankati.userservice.FeignCompte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;


@Setter  @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Compte {

    private Long userId; // ID de l'utilisateur associé au compte
    private BigDecimal solde;


}
