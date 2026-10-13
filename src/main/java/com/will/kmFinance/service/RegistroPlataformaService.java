package com.will.kmFinance.service;

import com.will.kmFinance.model.RegistroPlataforma;
import com.will.kmFinance.repository.RegistroPlataformaDao;
import org.springframework.stereotype.Service;

@Service
public class RegistroPlataformaService {
    private final RegistroPlataformaDao registroPlataformaDao;

    public RegistroPlataformaService(RegistroPlataformaDao registroPlataformaDao) {
        this.registroPlataformaDao = registroPlataformaDao;
    }

    public void salvarRegistro(RegistroPlataforma registro) {
        registroPlataformaDao.save(registro);
    }
}