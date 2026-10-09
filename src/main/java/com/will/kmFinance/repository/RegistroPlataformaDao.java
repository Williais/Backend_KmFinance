package com.will.kmFinance.repository;

import com.will.kmFinance.model.RegistroPlataforma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistroPlataformaDao extends JpaRepository<RegistroPlataforma, UUID> {
}
