package com.will.kmFinance.repository;

import com.will.kmFinance.model.Turno;
import com.will.kmFinance.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TurnoDao extends JpaRepository<Turno, UUID> {
    Optional<Turno> findByStatus(Status status);
}
