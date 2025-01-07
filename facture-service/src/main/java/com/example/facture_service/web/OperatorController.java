package com.example.facture_service.web;

import com.example.facture_service.entities.Operator;
import com.example.facture_service.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operators")
@CrossOrigin("http://localhost:4200")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping
    public List<Operator> getAllOperators() {
        return operatorService.getAllOperators();
    }

    @GetMapping("/{id}")
    public Operator getOperatorById(@PathVariable Long id) {
        return operatorService.getOperatorById(id);
    }

    @GetMapping("recharge/{id}")
    public Map<String, Object> rechargePhone(
            @PathVariable Long id,
            @RequestParam String number,
            @RequestParam String offer,
            @RequestParam double amount,
            @RequestParam Long clientId) {

        Map<String, Object> response = new HashMap<>();

        try {
            String result = operatorService.rechargePhone(number, offer, amount, id, clientId);

            // You can structure the response based on the outcome
            if (result.contains("Recharge successful")) {
                response.put("status", "success");
                response.put("message", "La recharge a été effectuée avec succès !");
            } else if (result.contains("Recharge failed: Phone number not found in database")) {
                response.put("status", "error");
                response.put("message", "Le numéro de téléphone n'a pas été trouvé dans la base de données.");
            } else if (result.contains("Insufficient balance for recharge")) {
                response.put("status", "warning");
                response.put("message", "Vous n'avez pas suffisamment de solde pour effectuer cette recharge.");
            } else {
                response.put("status", "info");
                response.put("message", "Réponse inconnue: " + result);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Une erreur est survenue lors de la recharge.");
        }

        return response;
    }


//    @PostMapping
//    public Operator createOperator(@RequestBody Operator operator) {
//        return operatorService.saveOperator(operator);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOperator(@PathVariable Long id) {
//        operatorService.deleteOperator(id);
//    }
}