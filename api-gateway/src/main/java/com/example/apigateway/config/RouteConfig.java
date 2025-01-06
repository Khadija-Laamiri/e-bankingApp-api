package com.example.apigateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/factures/**")
                        .uri("lb://facture-service"))
                .route(r -> r.path("/api/users/**")
                        .uri("lb://user-service"))
                .route(r -> r.path("/api/payment-transaction/**")
                        .uri("lb://service-payment-transaction"))
                .build();
    }

}
