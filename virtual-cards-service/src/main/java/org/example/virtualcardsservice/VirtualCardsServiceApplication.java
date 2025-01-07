package org.example.virtualcardsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VirtualCardsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualCardsServiceApplication.class, args);
    }

}
