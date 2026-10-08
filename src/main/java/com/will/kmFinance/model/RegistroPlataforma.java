package com.will.kmFinance.model;

import com.will.kmFinance.model.enums.Plataforma;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class RegistroPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private Plataforma plataforma;

    private BigDecimal valorFaturado;
    private BigDecimal kmRodadoApp;

    private Integer quantidadeCorridas;
}
