package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.Acessorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessorioRepository extends JpaRepository<Acessorio, Integer> {
}
