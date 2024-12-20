
package com.bankati.userservice.web;

import com.bankati.userservice.entities.User;
import com.bankati.userservice.enums.Role;
import com.bankati.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-agent")
    public ResponseEntity<User> addAgent(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String typePieceIdentite,
            @RequestParam String numeroPieceIdentite,
            @RequestParam String dateDeNaissance,
            @RequestParam String adresse,
            @RequestParam String email,
            @RequestParam String numeroTelephone,
            @RequestParam String numeroImmatriculation,
            @RequestParam String numeroPatente,

            @RequestParam(required = false) MultipartFile imageRecto,
            @RequestParam(required = false) MultipartFile imageVerso
    ) {
        try {
            LocalDate birthDate = LocalDate.parse(dateDeNaissance);
            User savedUser = userService.addAgent(nom, prenom, typePieceIdentite, numeroPieceIdentite,
                    birthDate, adresse, email, numeroTelephone, numeroImmatriculation, numeroPatente, imageRecto, imageVerso);
            return ResponseEntity.ok(savedUser);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // Récupérer tous les agents
    @GetMapping("/agents")
    public ResponseEntity<List<User>> getAllAgents() {
        List<User> agents = userService.getAllAgents();
        return ResponseEntity.ok(agents);
    }

    // Récupérer un agent par ID
    @GetMapping("/agent/{id}")
    public ResponseEntity<User> getAgentById(@PathVariable Long id) {
        User agent = userService.getAgentById(id);
        if (agent != null) {
            return ResponseEntity.ok(agent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mettre à jour un agent
    @PutMapping("/update-agent/{id}")
    public ResponseEntity<User> updateAgent(
            @PathVariable Long id,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String typePieceIdentite,
            @RequestParam String numeroPieceIdentite,
            @RequestParam String dateDeNaissance,
            @RequestParam String adresse,
            @RequestParam String email,
            @RequestParam String numeroTelephone,
            @RequestParam String numeroImmatriculation,
            @RequestParam String numeroPatente
    ) {
        try {
            LocalDate birthDate = LocalDate.parse(dateDeNaissance);
            User updatedUser = userService.updateAgent(id, nom, prenom, typePieceIdentite, numeroPieceIdentite,
                    birthDate, adresse, email, numeroTelephone, numeroImmatriculation, numeroPatente);
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/toggle-active/{id}")
    public ResponseEntity<User> toggleUserActiveStatus(@PathVariable Long id) {
        User updatedUser = userService.toggleUserActiveStatus(id);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/count/{role}")
    public long getTotalUsersByRole(@PathVariable Role role) {
        return userService.getTotalUsersByRole(role);
    }

}
