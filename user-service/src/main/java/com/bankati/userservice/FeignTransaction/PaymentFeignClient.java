package com.bankati.userservice.FeignTransaction;

import com.bankati.userservice.Models.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-service", url = "http://localhost:8080/paiements")
public interface PaymentFeignClient {
    @GetMapping("/{compteId}/transactions")
    ResponseEntity<List<Transaction>> getTransactionsByCompteId(@PathVariable Long compteId);
}
