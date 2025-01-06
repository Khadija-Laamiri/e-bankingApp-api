package com.example.facture_service.entities;

import com.example.facture_service.enums.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double sold;
    private String category;
    private String name;
    private String img;

    @ElementCollection
    @CollectionTable(name = "operator_details", joinColumns = @JoinColumn(name = "operator_id"))
    @Column(name = "detail")
    private List<String> details;

    @ElementCollection(targetClass = ServiceType.class)
    @CollectionTable(name = "operator_services", joinColumns = @JoinColumn(name = "operator_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "service")
    private List<ServiceType> services;
}
