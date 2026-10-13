package com.will.kmFinance.service;

import com.will.kmFinance.dto.response.ResumoDashboardDTO;
import com.will.kmFinance.model.PausaTurno;
import com.will.kmFinance.model.Turno;
import com.will.kmFinance.model.enums.Status;
import com.will.kmFinance.model.enums.TipoFinanceiro;
import com.will.kmFinance.repository.LancamentoFinanceiroDao;
import com.will.kmFinance.repository.PausaTurnoDao;
import com.will.kmFinance.repository.RegistroPlataformaDao;
import com.will.kmFinance.repository.TurnoDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {
    private final TurnoDao turnoDao;
    private final PausaTurnoDao pausaTurnoDao;
    private final LancamentoFinanceiroDao lancamentoFinanceiroDao;
    private final RegistroPlataformaDao registroPlataformaDao;

    public DashboardService(TurnoDao turnoDao, PausaTurnoDao pausaTurnoDao, LancamentoFinanceiroDao lancamentoFinanceiroDao, RegistroPlataformaDao registroPlataformaDao) {
        this.turnoDao = turnoDao;
        this.pausaTurnoDao = pausaTurnoDao;
        this.lancamentoFinanceiroDao = lancamentoFinanceiroDao;
        this.registroPlataformaDao = registroPlataformaDao;
    }

    public ResumoDashboardDTO montarResumoMensal(LocalDateTime inicio, LocalDateTime fim) {
        List<Turno> turnosFinalizados = turnoDao.findByStatusAndDataHoraInicioBetween(Status.FINALIZADO, inicio, fim);

        Integer kmOdometro = turnosFinalizados.stream()
                .mapToInt(t -> t.getOdometroFinal() - t.getOdometroInicial())
                .sum();

        BigDecimal kmViagemAppDecimal = registroPlataformaDao.sumKmByDataBetween(inicio, fim);
        Integer kmViagemApp = kmViagemAppDecimal != null ? kmViagemAppDecimal.intValue() : 0;

        Integer kmMorta = kmOdometro - kmViagemApp;
        Double ineficiencia = kmOdometro > 0 ? ((double) kmMorta / kmOdometro) * 100 : 0.0;

        BigDecimal faturamentoApps = registroPlataformaDao.sumFaturamentoByDataBetween(inicio, fim);
        if (faturamentoApps == null) faturamentoApps = BigDecimal.ZERO;

        BigDecimal receitasAvulsas = lancamentoFinanceiroDao.sumValorByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.RECEITA, inicio, fim);
        if (receitasAvulsas == null) receitasAvulsas = BigDecimal.ZERO;

        BigDecimal faturamentoTotal = faturamentoApps.add(receitasAvulsas);

        BigDecimal custosTotais = lancamentoFinanceiroDao.sumValorByTipoFinanceiroAndDataLancamentoBetween(TipoFinanceiro.DESPESA, inicio, fim);
        if (custosTotais == null) custosTotais = BigDecimal.ZERO;

        BigDecimal lucroReal = faturamentoTotal.subtract(custosTotais);

        long minutosTrabalhados = calcularMinutosTrabalhados(turnosFinalizados);
        BigDecimal horasTrabalhadasDecimal = BigDecimal.valueOf(minutosTrabalhados).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        String horasFormatadas = (minutosTrabalhados / 60) + "h" + (minutosTrabalhados % 60);

        BigDecimal valorPorKm = kmOdometro > 0 ? faturamentoTotal.divide(BigDecimal.valueOf(kmOdometro), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal valorPorHora = horasTrabalhadasDecimal.compareTo(BigDecimal.ZERO) > 0 ? faturamentoTotal.divide(horasTrabalhadasDecimal, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        return new ResumoDashboardDTO(
                faturamentoTotal,
                custosTotais,
                lucroReal,
                valorPorKm,
                valorPorHora,
                kmViagemApp,
                kmOdometro,
                kmMorta,
                ineficiencia,
                horasFormatadas
        );
    }

    private long calcularMinutosTrabalhados(List<Turno> turnos) {
        return turnos.stream().mapToLong(turno -> {
            long duracaoTurno = Duration.between(turno.getDataHoraInicio(), turno.getDataHoraFim()).toMinutes();

            List<PausaTurno> pausas = pausaTurnoDao.findByTurno(turno);
            long tempoPausado = pausas.stream()
                    .filter(p -> p.getDataHoraFim() != null)
                    .mapToLong(p -> Duration.between(p.getDataHoraInicio(), p.getDataHoraFim()).toMinutes())
                    .sum();

            return duracaoTurno - tempoPausado;
        }).sum();
    }
}