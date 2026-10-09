package com.will.kmFinance.repository;

import com.will.kmFinance.model.LancamentoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LancamentoFinanceiroDao extends JpaRepository<LancamentoFinanceiro, UUID> {
}
