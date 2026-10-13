package com.will.kmFinance.dto.request;

import com.will.kmFinance.model.enums.Categoria;
import com.will.kmFinance.model.enums.TipoItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LancamentoRequestDTO(
        BigDecimal valor,
        String descricao,
        Categoria categoria,
        TipoItem tipoItem,
        LocalDateTime dataLancamento
) {}