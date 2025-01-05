package com.example.facture_service.services;

import com.example.facture_service.entities.Operator;
import com.example.facture_service.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorService {

    private final OperatorRepository operatorRepository;

    public OperatorService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    public Operator getOperatorById(Long id) {
        return operatorRepository.findById(id).orElse(null);
    }

    public Operator saveOperator(Operator operator) {
        return operatorRepository.save(operator);
    }

    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}
