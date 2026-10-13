package com.will.kmFinance.service;

import com.will.kmFinance.model.PausaTurno;
import com.will.kmFinance.model.Turno;
import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.model.enums.Status;
import com.will.kmFinance.repository.PausaTurnoDao;
import com.will.kmFinance.repository.TurnoDao;
import com.will.kmFinance.repository.VeiculoDao;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TurnoService {
    private final TurnoDao turnoDao;
    private final PausaTurnoDao pausaTurnoDao;
    private final VeiculoDao veiculoDao;

    public TurnoService(TurnoDao turnoDao, PausaTurnoDao pausaTurnoDao, VeiculoDao veiculoDao) {
        this.turnoDao = turnoDao;
        this.pausaTurnoDao = pausaTurnoDao;
        this.veiculoDao = veiculoDao;
    }

    public void iniciarTurno() {
        turnoDao.findByStatus(Status.EM_ANDAMENTO).ifPresent(turno -> {
            throw new RuntimeException("Já existe um turno em andamento!");
        });

        Veiculo veiculo = veiculoDao.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        Turno turno = new Turno();
        turno.setVeiculo(veiculo);
        turno.setDataHoraInicio(LocalDateTime.now());
        turno.setOdometroInicial(veiculo.getOdometroAtual());
        turno.setStatus(Status.EM_ANDAMENTO);

        turnoDao.save(turno);
    }

    public void pausarTurno() {
        Turno turnoEncontrado = turnoDao.findByStatus(Status.EM_ANDAMENTO)
                .orElseThrow(() -> new RuntimeException("Não há turno em andamento!"));

        pausaTurnoDao.findByTurnoAndDataHoraFimIsNull(turnoEncontrado).ifPresent(pausa -> {
            throw new RuntimeException("O turno já está pausado!");
        });

        PausaTurno pausaTurno = new PausaTurno();
        pausaTurno.setDataHoraInicio(LocalDateTime.now());
        pausaTurno.setTurno(turnoEncontrado);

        pausaTurnoDao.save(pausaTurno);
    }

    public void retornarTurno() {
        Turno turnoEncontrado = turnoDao.findByStatus(Status.EM_ANDAMENTO)
                .orElseThrow(() -> new RuntimeException("Não há turno em andamento!"));

        PausaTurno pausaTurnoEncontrada = pausaTurnoDao.findByTurnoAndDataHoraFimIsNull(turnoEncontrado)
                .orElseThrow(() -> new RuntimeException("Não há pausa em andamento!"));

        pausaTurnoEncontrada.setDataHoraFim(LocalDateTime.now());
        pausaTurnoDao.save(pausaTurnoEncontrada);
    }

    public void finalizarTurno(Integer odometroFinal) {
        Turno turnoEncontrado = turnoDao.findByStatus(Status.EM_ANDAMENTO)
                .orElseThrow(() -> new RuntimeException("Não há turno em andamento!"));

        Veiculo veiculo = turnoEncontrado.getVeiculo();

        if (odometroFinal < turnoEncontrado.getOdometroInicial()) {
            throw new RuntimeException("Odômetro final inválido.");
        }

        turnoEncontrado.setDataHoraFim(LocalDateTime.now());
        turnoEncontrado.setStatus(Status.FINALIZADO);
        turnoEncontrado.setOdometroFinal(odometroFinal);
        turnoDao.save(turnoEncontrado);

        veiculo.setOdometroAtual(odometroFinal);
        veiculoDao.save(veiculo);
    }
}