package com.will.kmFinance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PausaTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @ManyToOne
    private Turno turno;
}
