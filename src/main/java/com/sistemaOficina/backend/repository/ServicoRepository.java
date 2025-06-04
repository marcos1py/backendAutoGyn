package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
