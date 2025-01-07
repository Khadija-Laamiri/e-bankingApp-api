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
                .route(r -> r.path("/api/operators/**")
                        .uri("lb://facture-service"))
                .route(r -> r.path("/api/agences/**")
                        .uri("lb://user-service"))
                .route(r -> r.path("/auth/**")
                        .uri("lb://user-service"))
                .route(r -> r.path("/api/sms/**")
                        .uri("lb://user-service"))
                .route(r -> r.path("/api/users/**")
                        .uri("lb://user-service"))
                .route(r -> r.path("/comptes/**")
                        .uri("lb://service-payment-transaction"))
                .route(r -> r.path("/paiements/**")
                        .uri("lb://service-payment-transaction"))
                .route(r -> r.path("/transactions/**")
                        .uri("lb://service-payment-transaction"))
                .route(r -> r.path("/api/virtual-cards/**")
                        .uri("lb://virtual-cards-service"))
                .build();
    }

}
