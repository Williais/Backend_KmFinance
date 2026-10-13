package com.will.kmFinance.controller;

import com.will.kmFinance.dto.request.LancamentoRequestDTO;
import com.will.kmFinance.model.enums.Categoria;
import com.will.kmFinance.service.FinanceiroService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/financeiro")
public class FinanceiroController {
    private final FinanceiroService financeiroService;

    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @PostMapping("/receita")
    public ResponseEntity<Void> lancarReceita(@RequestBody LancamentoRequestDTO request) {
        financeiroService.lancarReceita(request.valor(), request.descricao(), request.dataLancamento());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/despesa")
    public ResponseEntity<Void> lancarDespesa(@RequestBody LancamentoRequestDTO request) {
        financeiroService.lancarDespesa(request.valor(), request.categoria(), request.descricao(), request.tipoItem(), request.dataLancamento());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/distribuicao-despesas")
    public ResponseEntity<Map<Categoria, BigDecimal>> obterDistribuicaoDeDespesas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(financeiroService.obterDistribuicaoDeDespesas(inicio, fim));
    }
}