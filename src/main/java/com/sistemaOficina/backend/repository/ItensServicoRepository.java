package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.ItensServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensServicoRepository extends JpaRepository<ItensServico, Integer> {
}
