package com.example.operators_api.repositories;

import com.example.operators_api.entitie.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {}