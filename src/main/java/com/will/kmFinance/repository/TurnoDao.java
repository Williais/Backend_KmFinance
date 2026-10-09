package com.will.kmFinance.repository;

import com.will.kmFinance.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TurnoDao extends JpaRepository<Turno, UUID> {
}
