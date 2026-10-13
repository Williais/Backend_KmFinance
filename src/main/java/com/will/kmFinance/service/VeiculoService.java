package com.will.kmFinance.service;

import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.repository.VeiculoDao;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeiculoService {
    private final VeiculoDao veiculoDao;

    public VeiculoService(VeiculoDao veiculoDao) {
        this.veiculoDao = veiculoDao;
    }

    public void salvarVeiculo(Veiculo veiculo) {
        veiculoDao.save(veiculo);
    }

    public Veiculo obterVeiculo() {
        return veiculoDao.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));
    }
}