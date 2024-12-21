package com.bankati.userservice.FeignCompte;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "payment-service", url = "http://localhost:8080/comptes")
public interface CompteClient {
    @PostMapping("/creer")
    ResponseEntity<Compte> creerCompte(@RequestParam Long userId, @RequestParam BigDecimal soldeInitial);
}
