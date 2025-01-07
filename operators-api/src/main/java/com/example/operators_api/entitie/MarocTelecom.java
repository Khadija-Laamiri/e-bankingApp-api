package com.example.operators_api.entitie;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class MarocTelecom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> phoneNumbers; // List of phone numbers
}


