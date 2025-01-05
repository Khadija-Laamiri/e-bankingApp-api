package com.example.facture_service.repositories;

import com.example.facture_service.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
