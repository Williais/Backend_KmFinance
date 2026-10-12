package com.will.kmFinance.repository;

import com.will.kmFinance.model.LancamentoFinanceiro;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LancamentoFinanceiroDao extends JpaRepository<LancamentoFinanceiro, UUID> {
    List<LancamentoFinanceiro> findByTipoFinanceiro(TipoFinanceiro tipoFinanceiro);

    List<LancamentoFinanceiro> findByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro tipoFinanceiro, LocalDateTime dataInicio, LocalDateTime dataFim);

    BigDecimal sumValorByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro tipoFinanceiro, LocalDateTime dataInicio, LocalDateTime dataFim);
}
