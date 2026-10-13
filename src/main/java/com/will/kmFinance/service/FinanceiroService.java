package com.will.kmFinance.service;

import com.will.kmFinance.model.LancamentoFinanceiro;
import com.will.kmFinance.model.ManutencaoLog;
import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.model.enums.Categoria;
import com.will.kmFinance.model.enums.Status;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import com.will.kmFinance.model.enums.TipoItem;
import com.will.kmFinance.repository.LancamentoFinanceiroDao;
import com.will.kmFinance.repository.ManutencaoLogDao;
import com.will.kmFinance.repository.TurnoDao;
import com.will.kmFinance.repository.VeiculoDao;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinanceiroService {
    private final LancamentoFinanceiroDao lancamentoFinanceiroDao;
    private final ManutencaoLogDao manutencaoLogDao;
    private final TurnoDao turnoDao;
    private final VeiculoDao veiculoDao;

    public FinanceiroService(TurnoDao turnoDao, LancamentoFinanceiroDao lancamentoFinanceiroDao, ManutencaoLogDao manutencaoLogDao, VeiculoDao veiculoDao) {
        this.lancamentoFinanceiroDao = lancamentoFinanceiroDao;
        this.manutencaoLogDao = manutencaoLogDao;
        this.turnoDao = turnoDao;
        this.veiculoDao = veiculoDao;
    }

    public void lancarReceita(BigDecimal valor, String descricao, LocalDateTime dataLancamento) {
        LancamentoFinanceiro lancamento = new LancamentoFinanceiro();
        turnoDao.findByStatus(Status.EM_ANDAMENTO).ifPresent(lancamento::setTurno);

        lancamento.setDescricao(descricao);
        lancamento.setValor(valor);
        lancamento.setDataLancamento(dataLancamento);
        lancamento.setTipoFinanceiro(TipoFinanceiro.RECEITA);
        lancamentoFinanceiroDao.save(lancamento);
    }

    public void lancarDespesa(BigDecimal valor, Categoria categoria, String descricao, TipoItem tipoItem, LocalDateTime dataLancamento) {
        LancamentoFinanceiro lancamento = new LancamentoFinanceiro();
        turnoDao.findByStatus(Status.EM_ANDAMENTO).ifPresent(lancamento::setTurno);

        lancamento.setDescricao(descricao);
        lancamento.setValor(valor);
        lancamento.setCategoria(categoria);
        lancamento.setDataLancamento(dataLancamento);
        lancamento.setTipoFinanceiro(TipoFinanceiro.DESPESA);
        LancamentoFinanceiro lancamentoSalvo = lancamentoFinanceiroDao.save(lancamento);

        if (Categoria.MANUTENCAO_PECA.equals(categoria) || Categoria.MANUTENCAO_SERVICO.equals(categoria) || Categoria.TROCA_OLEO.equals(categoria)) {
            Veiculo veiculo = veiculoDao.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

            ManutencaoLog log = new ManutencaoLog();
            log.setVeiculo(veiculo);
            log.setLancamentoFinanceiro(lancamentoSalvo);
            log.setOdometroNoMomento(veiculo.getOdometroAtual());
            log.setData_manutencao(dataLancamento.toLocalDate());

            if (tipoItem != null) {
                log.setTipoItem(String.valueOf(tipoItem));
            }
            manutencaoLogDao.save(log);
        }
    }

    public Map<Categoria, BigDecimal> obterDistribuicaoDeDespesas(LocalDateTime inicio, LocalDateTime fim) {
        return lancamentoFinanceiroDao.findByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.DESPESA, inicio, fim)
                .stream()
                .collect(Collectors.groupingBy(
                        LancamentoFinanceiro::getCategoria,
                        Collectors.reducing(BigDecimal.ZERO, LancamentoFinanceiro::getValor, BigDecimal::add)
                ));
    }
}