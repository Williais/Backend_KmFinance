package com.will.kmFinance.model;

import com.will.kmFinance.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Veiculo veiculo;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private Integer odometroInicial;
    private Integer odometroFinal;

    private Status status;
}
