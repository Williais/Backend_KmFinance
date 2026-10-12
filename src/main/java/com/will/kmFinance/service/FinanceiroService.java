package com.will.kmFinance.service;

import com.will.kmFinance.model.LancamentoFinanceiro;
import com.will.kmFinance.model.ManutencaoLog;
import com.will.kmFinance.model.Turno;
import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.model.enums.Categoria;
import com.will.kmFinance.model.enums.Status;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import com.will.kmFinance.model.enums.TipoItem;
import com.will.kmFinance.repository.LancamentoFinanceiroDao;
import com.will.kmFinance.repository.ManutencaoLogDao;
import com.will.kmFinance.repository.TurnoDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FinanceiroService {

    private final LancamentoFinanceiroDao lancamentoFinanceiroDao;
    private final ManutencaoLogDao manutencaoLogDao;
    private final TurnoDao turnoDao;

    public FinanceiroService(TurnoDao turnoDao, LancamentoFinanceiroDao lancamentoFinanceiroDao, ManutencaoLogDao manutencaoLogDao) {
        this.lancamentoFinanceiroDao = lancamentoFinanceiroDao;
        this.manutencaoLogDao = manutencaoLogDao;
        this.turnoDao = turnoDao;
    }

    public void lancarReceita(BigDecimal valor, String descricao, LocalDateTime dataLancamento) {
        Optional<Turno> turnoAtivo = turnoDao.findByStatus(Status.EM_ANDAMENTO);
        LancamentoFinanceiro lancamentoFinanceiro = new LancamentoFinanceiro();

        turnoAtivo.ifPresent(lancamentoFinanceiro::setTurno);

        lancamentoFinanceiro.setDescricao(descricao);
        lancamentoFinanceiro.setValor(valor);
        lancamentoFinanceiro.setDataLancamento(dataLancamento);
        lancamentoFinanceiro.setTipoFinanceiro(TipoFinanceiro.RECEITA);

        lancamentoFinanceiroDao.save(lancamentoFinanceiro);
    }

    public void lancarDespesa(Veiculo veiculo, BigDecimal valor, Categoria categoria, String descricao, TipoItem tipoItem, LocalDateTime dataLancamento) {
        Optional<Turno> turnoAtivo = turnoDao.findByStatus(Status.EM_ANDAMENTO);
        LancamentoFinanceiro lancamentoFinanceiro = new LancamentoFinanceiro();

        turnoAtivo.ifPresent(lancamentoFinanceiro::setTurno);

        lancamentoFinanceiro.setDescricao(descricao);
        lancamentoFinanceiro.setValor(valor);
        lancamentoFinanceiro.setCategoria(categoria);
        lancamentoFinanceiro.setDataLancamento(dataLancamento);
        lancamentoFinanceiro.setTipoFinanceiro(TipoFinanceiro.DESPESA);

        LancamentoFinanceiro lancamentoSalvo = lancamentoFinanceiroDao.save(lancamentoFinanceiro);

        if (Categoria.MANUTENCAO_PECA.equals(categoria) || Categoria.MANUTENCAO_SERVICO.equals(categoria) || Categoria.TROCA_OLEO.equals(categoria)) {
            ManutencaoLog manutencaoLog = new ManutencaoLog();

            manutencaoLog.setVeiculo(veiculo);
            manutencaoLog.setLancamentoFinanceiro(lancamentoSalvo);
            manutencaoLog.setOdometroNoMomento(veiculo.getOdometroAtual());
            manutencaoLog.setData_manutencao(dataLancamento.toLocalDate());

            if (tipoItem != null) {
                manutencaoLog.setTipoItem(String.valueOf(tipoItem));
            }

            manutencaoLogDao.save(manutencaoLog);
        }
    }

    public BigDecimal obterLucroLiquidoPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        BigDecimal totalReceitas = lancamentoFinanceiroDao.sumValorByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.RECEITA, dataInicio, dataFim);
        BigDecimal totalDespesas = lancamentoFinanceiroDao.sumValorByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.DESPESA, dataInicio, dataFim);

        if (totalReceitas == null) totalReceitas = BigDecimal.ZERO;
        if (totalDespesas == null) totalDespesas = BigDecimal.ZERO;

        return totalReceitas.subtract(totalDespesas);
    }

    public Map<Categoria, BigDecimal> obterDistribuicaoDeDespesas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<LancamentoFinanceiro> despesas = lancamentoFinanceiroDao.findByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.DESPESA, dataInicio, dataFim);

        return despesas.stream()
                .collect(Collectors.groupingBy(
                        LancamentoFinanceiro::getCategoria,
                        Collectors.reducing(BigDecimal.ZERO, LancamentoFinanceiro::getValor, BigDecimal::add)
                ));
    }
}