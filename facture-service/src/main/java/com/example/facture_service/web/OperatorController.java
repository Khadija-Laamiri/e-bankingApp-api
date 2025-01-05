package com.example.facture_service.web;

import com.example.facture_service.entities.Operator;
import com.example.facture_service.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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