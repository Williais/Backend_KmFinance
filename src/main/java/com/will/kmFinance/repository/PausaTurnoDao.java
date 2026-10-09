package com.will.kmFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PausaTurnoDao extends JpaRepository<PausaTurnoDao, UUID> {
}
