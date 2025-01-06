package com.example.facture_service.services;

import com.example.facture_service.entities.Operator;
import com.example.facture_service.repositories.OperatorRepository;
import com.example.operators_api.web.MarocTelecomSoapService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorService {

    private final OperatorRepository operatorRepository;
    private final MarocTelecomSoapService marocTelecomSoapService;

    public OperatorService(OperatorRepository operatorRepository, MarocTelecomSoapService marocTelecomSoapService) {
        this.operatorRepository = operatorRepository;
        this.marocTelecomSoapService = marocTelecomSoapService;
    }

    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    public Operator getOperatorById(Long id) {
        return operatorRepository.findById(id).orElse(null);
    }

    public boolean rechargePhone(String number, String offer, double amount, Long operatorId, Long clientId) {
        try {
            // Convert amount to double

            // Call the recharge method of the SOAP service
            String response = marocTelecomSoapService.recharge(1L, number,amount );

            // Check the response and return appropriate result
            if (response.contains("successful")) {
                System.out.println("Recharge successful: " + response);
                return true;
            } else {
                System.out.println("Recharge failed: " + response);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while processing recharge");
            return false;
        }
    }

    public Operator saveOperator(Operator operator) {
        return operatorRepository.save(operator);
    }

    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}
