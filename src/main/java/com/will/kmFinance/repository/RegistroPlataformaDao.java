package com.will.kmFinance.repository;

import com.will.kmFinance.model.RegistroPlataforma;
import com.will.kmFinance.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RegistroPlataformaDao extends JpaRepository<RegistroPlataforma, UUID> {
    Optional<RegistroPlataforma> findByTurno(Turno turno);

    BigDecimal sumKmByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    BigDecimal sumFaturamentoByDataBetween(LocalDateTime inicio, LocalDateTime fim);
}
