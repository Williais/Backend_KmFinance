package com.will.kmFinance.repository;

import com.will.kmFinance.model.LancamentoFinanceiro;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LancamentoFinanceiroDao extends JpaRepository<LancamentoFinanceiro, UUID> {
    List<LancamentoFinanceiro> findByTipoFinanceiro(TipoFinanceiro tipoFinanceiro);

    List<LancamentoFinanceiro> findByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro tipoFinanceiro, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT SUM(l.valor) FROM LancamentoFinanceiro l WHERE l.tipoFinanceiro = :tipo AND l.dataLancamento BETWEEN :inicio AND :fim")
    BigDecimal sumValorByTipoFinanceiroAndDataLancamentoBetween(
        @Param("tipo") TipoFinanceiro tipo,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);
}
