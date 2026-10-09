package com.will.kmFinance.repository;

import com.will.kmFinance.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeiculoDao extends JpaRepository<Veiculo, UUID> {

}
