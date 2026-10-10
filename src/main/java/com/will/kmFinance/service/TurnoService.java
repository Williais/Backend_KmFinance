package com.will.kmFinance.service;

import com.will.kmFinance.model.PausaTurno;
import com.will.kmFinance.model.Turno;
import com.will.kmFinance.model.Veiculo;
import com.will.kmFinance.model.enums.Status;
import com.will.kmFinance.repository.PausaTurnoDao;
import com.will.kmFinance.repository.TurnoDao;
import com.will.kmFinance.repository.VeiculoDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private TurnoDao turnoDao;
    private PausaTurnoDao pausaTurnoDao;
    private VeiculoDao veiculoDao;

    public TurnoService(TurnoDao turnoDao, PausaTurnoDao pausaTurnoDao, VeiculoDao veiculoDao) {
        this.turnoDao = turnoDao;
        this.pausaTurnoDao = pausaTurnoDao;
        this.veiculoDao = veiculoDao;
    }

    public void iniciarTurno(Veiculo veiculo){
        Optional<Turno> turnoEmAndamento = turnoDao.findByStatus(Status.EM_ANDAMENTO);

        if(turnoEmAndamento.isPresent()){
            throw new RuntimeException("Já existe um turno em andamento!");
        }

        if(!veiculo.getAtivo()){
            throw new RuntimeException("Veiculo não Ativo");
        }

        Turno turno = new Turno();
        turno.setVeiculo(veiculo);
        turno.setDataHoraInicio(LocalDateTime.now());
        turno.setOdometroInicial(veiculo.getOdometroAtual());
        turno.setStatus(Status.EM_ANDAMENTO);
        turnoDao.save(turno);
    }

    public void pausarTurno(){
        Turno turnoEncontrado = turnoDao.findByStatus(Status.EM_ANDAMENTO)
                .orElseThrow(() -> new RuntimeException("Não há turno em andamento!"));

        PausaTurno pausaTurno = new PausaTurno();

        pausaTurno.setDataHoraInicio(LocalDateTime.now());
        pausaTurno.setTurno(turnoEncontrado);
        pausaTurnoDao.save(pausaTurno);

    }

    public void retornarTurno(){
        Turno turnoEncontrado = turnoDao.findByStatus(Status.EM_ANDAMENTO)
                .orElseThrow(() -> new RuntimeException("Não há turno em andamento!"));

        Optional<PausaTurno> pausa = pausaTurnoDao.findByTurnoAndDataHoraFimIsNull(turnoEncontrado);
        PausaTurno pausaTurnoEncontrado = pausa.get();

        pausaTurnoEncontrado.setDataHoraFim(LocalDateTime.now());
        pausaTurnoDao.save(pausaTurnoEncontrado);
    }

    public void finalizarTurno(Integer odomentroFinal){
        Optional<Turno> turno = turnoDao.findByStatus(Status.EM_ANDAMENTO);
        Turno turnoEncontrado = turno.get();
        Veiculo veiculo = turnoEncontrado.getVeiculo();

        turnoEncontrado.setDataHoraFim(LocalDateTime.now());
        turnoEncontrado.setStatus(Status.FINALIZADO);
        turnoEncontrado.setOdometroFinal(odomentroFinal);
        veiculo.setOdometroAtual(odomentroFinal);
        turnoDao.save(turnoEncontrado);
        veiculoDao.save(veiculo);
    }


}
