package org.example.virtualcardsservice.feign;

import org.example.servicepaymenttransaction.Services.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8099/api/users", fallback = UserServiceFallback.class)
public interface UserServiceClient {


    @GetMapping("/users/{userId}/cardholderId")
    String getCardholderIdByUserId(@PathVariable("userId") Long userId);



}
