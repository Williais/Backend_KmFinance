package com.will.kmFinance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Usuario usuario;

    private String apelido;
    private String modelo;
    private Integer odometroAtual;
    private Integer metaOleo;
    private Integer metaRelacao;
    private Integer metaPastilha;
    private Integer metaFluido;
    private Boolean ativo;


}
