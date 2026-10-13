package com.will.kmFinance.controller;

import com.will.kmFinance.model.RegistroPlataforma;
import com.will.kmFinance.service.RegistroPlataformaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros")
public class RegistroPlataformaController {
    private final RegistroPlataformaService registroPlataformaService;

    public RegistroPlataformaController(RegistroPlataformaService registroPlataformaService) {
        this.registroPlataformaService = registroPlataformaService;
    }

    @PostMapping
    public ResponseEntity<Void> salvarRegistro(@RequestBody RegistroPlataforma registro) {
        registroPlataformaService.salvarRegistro(registro);
        return ResponseEntity.ok().build();
    }
}