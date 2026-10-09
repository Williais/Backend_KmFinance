package com.will.kmFinance.repository;

import com.will.kmFinance.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioDao extends JpaRepository<Usuario, UUID> {
}
