package com.will.kmFinance.dto.response;

import java.math.BigDecimal;

public record ResumoDashboardDTO(
        BigDecimal faturamentoTotal,
        BigDecimal custoTotais,
        BigDecimal lucroReal,
        BigDecimal valorPorKm,
        BigDecimal valorPorHora,
        Integer kmViagemApp,
        Integer kmOdometro,
        Integer kmMorta,
        Double porcentagemIneficiencia,
        String horasTrabalhadas

        ) {
}
