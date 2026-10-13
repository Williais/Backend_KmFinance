package com.will.kmFinance.repository;

import com.will.kmFinance.model.RegistroPlataforma;
import com.will.kmFinance.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RegistroPlataformaDao extends JpaRepository<RegistroPlataforma, UUID> {
    Optional<RegistroPlataforma> findByTurno(Turno turno);

    @Query("SELECT SUM(r.kmRodadoApp) FROM RegistroPlataforma r WHERE r.turno.dataHoraInicio BETWEEN :inicio AND :fim")
    BigDecimal sumKmByDataBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT SUM(r.valorFaturado) FROM RegistroPlataforma r WHERE r.turno.dataHoraInicio BETWEEN :inicio AND :fim")
    BigDecimal sumFaturamentoByDataBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
