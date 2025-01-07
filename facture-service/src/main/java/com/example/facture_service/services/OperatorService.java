package com.example.facture_service.services;

import com.example.facture_service.entities.Operator;
import com.example.facture_service.repositories.OperatorRepository;
import com.example.operators_api.web.MarocTelecomSoapService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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

    public String rechargePhone(String number, String offer, double amount, Long operatorId, Long clientId) {
        try {
            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Step 1: Call the REST endpoint to get the user's balance
            String soldeUrl = "http://localhost:8085/comptes/solde/" + clientId;
            ResponseEntity<BigDecimal> responseEntity = restTemplate.getForEntity(soldeUrl, BigDecimal.class);

            BigDecimal userBalance = responseEntity.getBody();
            BigDecimal totalCost = BigDecimal.valueOf(amount); // Apply 2% discount

            // Step 2: Check if the balance is sufficient
            if (userBalance == null || userBalance.compareTo(totalCost) < 0) {
                return "Insufficient balance for recharge. Required: " + totalCost + " MAD, Available: " + userBalance + " MAD";
            }

            // Step 3: Debit the amount from the user's account
            String debitUrl = "http://localhost:8085/comptes/debit/" + clientId + "?amount=" + totalCost;
            ResponseEntity<String> debitResponse = restTemplate.exchange(debitUrl, HttpMethod.PUT, null, String.class);

            if (debitResponse.getStatusCode() != HttpStatus.OK) {
                return "Failed to debit amount from user's account. " + debitResponse.getBody();
            }

            // Step 4: Call the recharge method of the SOAP service
            String response = marocTelecomSoapService.recharge(1L, number, amount);

            // Step 5: Return the response directly
            if (response.contains("successful")) {
                System.out.println("Recharge successful: " + response);
            } else {
                System.out.println("Recharge failed: " + response);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Error while processing recharge";
            System.out.println(errorMessage);
            return errorMessage;
        }
    }



    public Operator saveOperator(Operator operator) {
        return operatorRepository.save(operator);
    }

    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}
