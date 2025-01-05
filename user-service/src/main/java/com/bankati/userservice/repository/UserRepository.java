package com.bankati.userservice.repository;



import com.bankati.userservice.entities.Agence;
import com.bankati.userservice.entities.User;
import com.bankati.userservice.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findByEmail(String email);
        Optional<User> findByNumeroTelephone(String numeroTelephone);

        List<User> findByRole(Role role);

        Optional<User> findByIdAndRole(Long id, Role role);

        boolean existsByIdAndRole(Long id, Role role);
        long countByRole(Role role);
        List<User> findByAgent(User agent);
        @Query("SELECT u.agence FROM User u WHERE u.id = :id AND u.role = 'AGENT'")
        Optional<Agence> findAgenceByAgentId(@Param("id") Long id);


}
