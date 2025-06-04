package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {
    Optional<Veiculo> findByPlaca(String placa);
}
