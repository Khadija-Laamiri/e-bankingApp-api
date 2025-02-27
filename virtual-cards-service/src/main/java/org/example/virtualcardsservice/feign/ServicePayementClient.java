package org.example.virtualcardsservice.feign;


import org.example.virtualcardsservice.dtos.Compte;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "ServicePaymentTransaction", url = "http://localhost:8085/")
public interface ServicePayementClient {

    @PostMapping("/comptes/add-virtual-card")
    public ResponseEntity<String> addVirtualCardByUserId(
            @RequestParam Long userId,
            @RequestParam String newCard);


    @GetMapping("/comptes/user/{userId}")
    public ResponseEntity<Compte> getCompteByUserId(@PathVariable Long userId);

    @PostMapping("/paiements/{compteId}/toCard")
    public ResponseEntity<String> transferToCard(@PathVariable Long compteId, @RequestParam BigDecimal montant) ;


}
