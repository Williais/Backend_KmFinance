package com.will.kmFinance.repository;

import com.will.kmFinance.model.PausaTurno;
import com.will.kmFinance.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PausaTurnoDao extends JpaRepository<PausaTurno, UUID> {
    Optional<PausaTurno> findByTurnoAndDataHoraFimIsNull(Turno turno);

    List<PausaTurno> findByTurno(Turno turno);
}
