package com.will.kmFinance.controller;

import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Void> salvarVeiculo(@RequestBody Veiculo veiculo) {
        veiculoService.salvarVeiculo(veiculo);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Veiculo> obterVeiculo() {
        return ResponseEntity.ok(veiculoService.obterVeiculo());
    }
}