package com.will.kmFinance.model;

import com.will.kmFinance.model.enums.Categoria;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class LancamentoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Turno turno;

    private TipoFinanceiro tipoFinanceiro;

    private Categoria categoria;

    private BigDecimal valor;
    private String descricao;
    private LocalDateTime dataLancamento;
}
