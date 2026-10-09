package com.will.kmFinance.repository;

import com.will.kmFinance.model.ManutencaoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManutencaoLogDao extends JpaRepository<ManutencaoLog, UUID> {
}
