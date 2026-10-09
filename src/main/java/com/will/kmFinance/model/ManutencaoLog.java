package com.will.kmFinance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ManutencaoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Veiculo veiculo;

    @OneToOne
    private LancamentoFinanceiro lancamentoFinanceiro;

    private String tipoItem;
    private Integer odometroNoMomento;
    private LocalDate data_manutencao;
    private String observacao;
}
