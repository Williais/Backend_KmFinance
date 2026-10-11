package com.will.kmFinance.service;

import com.will.kmFinance.repository.LancamentoFinanceiroDao;
import com.will.kmFinance.repository.ManutencaoLogDao;
import org.springframework.stereotype.Service;

@Service
public class FinanceiroService {
    private final LancamentoFinanceiroDao lancamentoFinanceiroDao;
    private final ManutencaoLogDao manutencaoLogDao;

    public FinanceiroService(LancamentoFinanceiroDao lancamentoFinanceiroDao,  ManutencaoLogDao manutencaoLogDao) {
        this.lancamentoFinanceiroDao = lancamentoFinanceiroDao;
        this.manutencaoLogDao = manutencaoLogDao;
    }


}
