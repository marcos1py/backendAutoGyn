package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {
    
    @Query("SELECT MAX(o.numero) FROM OrdemServico o")
    Integer findMaxId();
}
