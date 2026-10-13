package com.will.kmFinance.controller;

import com.will.kmFinance.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {
    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Void> iniciarTurno() {
        turnoService.iniciarTurno();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pausar")
    public ResponseEntity<Void> pausarTurno() {
        turnoService.pausarTurno();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/retomar")
    public ResponseEntity<Void> retornarTurno() {
        turnoService.retornarTurno();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Void> finalizarTurno(@RequestParam Integer odometroFinal) {
        turnoService.finalizarTurno(odometroFinal);
        return ResponseEntity.ok().build();
    }
}