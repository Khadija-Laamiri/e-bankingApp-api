
package com.bankati.userservice.service;

import jakarta.annotation.PostConstruct;
import com.bankati.userservice.entities.User;
import com.bankati.userservice.enums.Role;
import com.bankati.userservice.enums.TypePieceIdentite;
import com.bankati.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    private final String uploadDir = "C:/Users/User/Desktop/PROJ-2-V2/e-bankingApp-api/user-service/src/main/resources/uploads/";
    @PostConstruct
    public void init() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    //Agent
    public User addAgent(String nom,
                         String prenom,
                         String typePieceIdentite,
                         String numeroPieceIdentite,
                         LocalDate dateDeNaissance,
                         String adresse,
                         String email,
                         String numeroTelephone,
                         String numeroImmatriculation,
                         String numeroPatente,

                         MultipartFile imageRecto,
                         MultipartFile imageVerso) throws IOException {

        // Générer un mot de passe aléatoire
        String generatedPassword = generateRandomPassword(8);

        // Créer une instance de l'utilisateur
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setTypePieceIdentite(Enum.valueOf(TypePieceIdentite.class, typePieceIdentite));
        user.setNumeroPieceIdentite(numeroPieceIdentite);
        user.setDateDeNaissance(dateDeNaissance);
        user.setAdresse(adresse);
        user.setEmail(email);
        user.setNumeroTelephone(numeroTelephone);
        user.setNumeroImmatriculation(numeroImmatriculation);
        user.setNumeroPatente(numeroPatente);
        user.setPassword(passwordEncoder.encode(generatedPassword));
        user.setRole(Role.AGENT);

        // Sauvegarder l'image recto avec un nom unique
        if (imageRecto != null && !imageRecto.isEmpty()) {
            String uniqueRectoName = System.currentTimeMillis() + "_" + imageRecto.getOriginalFilename();
            String rectoPath = uploadDir + uniqueRectoName;
            imageRecto.transferTo(new File(rectoPath));
            user.setImageRecto(rectoPath);
        }

        // Sauvegarder l'image verso avec un nom unique
        if (imageVerso != null && !imageVerso.isEmpty()) {
            String uniqueVersoName = System.currentTimeMillis() + "_" + imageVerso.getOriginalFilename();
            String versoPath = uploadDir + uniqueVersoName;
            imageVerso.transferTo(new File(versoPath));
            user.setImageVerso(versoPath);
        }

        // Enregistrer l'utilisateur
        User savedUser = userRepository.save(user);

        // Envoyer l'email avec le mot de passe généré
        String subject = "Bienvenue ! Voici vos identifiants de connexion";
        String message = "Bonjour " + prenom + " " + nom + ",\n\n" +
                "Votre compte a été créé avec succès.\n" +
                "Voici vos identifiants de connexion :\n" +
                "Email : " + email + "\n" +
                "Mot de passe : " + generatedPassword + "\n\n" +
                "Veuillez vous connecter et changer votre mot de passe dès que possible.\n\n" +
                "Cordialement,\nL'équipe de support.";

        emailService.sendEmail(email, subject, message);

        return savedUser;
    }
    // Récupérer tous les agents
    public List<User> getAllAgents() {
        return userRepository.findByRole(Role.AGENT);
    }

    // Récupérer un agent par son ID
    public User getAgentById(Long id) {
        return userRepository.findByIdAndRole(id, Role.AGENT).orElse(null);
    }

    // Mettre à jour un agent
    public User updateAgent(Long id, String nom, String prenom, String typePieceIdentite, String numeroPieceIdentite,
                            LocalDate dateDeNaissance, String adresse, String email, String numeroTelephone,
                            String numeroImmatriculation, String numeroPatente) throws IOException {

        // Récupérer l'agent existant
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agent non trouvé avec l'ID : " + id));

        // Mettre à jour les champs
        existingUser.setNom(nom);
        existingUser.setPrenom(prenom);
        existingUser.setTypePieceIdentite(TypePieceIdentite.valueOf(typePieceIdentite));
        existingUser.setNumeroPieceIdentite(numeroPieceIdentite);
        existingUser.setDateDeNaissance(dateDeNaissance);
        existingUser.setAdresse(adresse);
        existingUser.setEmail(email);
        existingUser.setNumeroTelephone(numeroTelephone);
        existingUser.setNumeroImmatriculation(numeroImmatriculation);
        existingUser.setNumeroPatente(numeroPatente);
        return userRepository.save(existingUser);
    }
    @Transactional
    public User toggleUserActiveStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + id));

        user.setActive(!user.isActive());

        return userRepository.save(user);
    }


    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
    // Méthode pour calculer le nombre total d'utilisateurs selon le rôle
    public long getTotalUsersByRole(Role role) {
        return userRepository.countByRole(role);
    }


}
