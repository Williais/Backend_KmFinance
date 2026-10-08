package com.will.kmFinance.model;

import com.will.kmFinance.model.enums.Plataforma;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PausaTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Turno turno_id;

    private Plataforma plataforma;
    private BigDecimal valorFaturado;
    private BigDecimal kmRodadoApp;
    private Integer quantidadeCorridas;
}
