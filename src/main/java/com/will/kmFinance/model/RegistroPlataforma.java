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
public class RegistroPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Plataforma plataforma;

    @ManyToOne
    private Turno turno;

    private BigDecimal valorFaturado;
    private BigDecimal kmRodadoApp;

    private Integer quantidadeCorridas;
}
