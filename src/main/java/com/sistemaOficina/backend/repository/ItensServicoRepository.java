package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.ItensServico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensServicoRepository extends JpaRepository<ItensServico, Integer> {
    @Query("SELECT i FROM ItensServico i WHERE UPPER(i.funcionario.nome) = UPPER(:nomeLike)")
    List<ItensServico> findByFuncionarioNome(@Param("nomeLike") String nomeLike);
    
    @Query("SELECT i FROM ItensServico i WHERE MONTH(i.horarioInicio) = :mes AND YEAR(i.horarioInicio) = YEAR(CURRENT_DATE)")
    List<ItensServico> findByMesAnoAtual(@Param("mes") int mes);
    
    @Query("SELECT i FROM ItensServico i WHERE MONTH(i.horarioInicio) = :mes AND YEAR(i.horarioInicio) = YEAR(CURRENT_DATE) AND UPPER(i.funcionario.nome) = UPPER(:nomeFuncionario)")
    List<ItensServico> findByMesAnoAtualAndFuncionarioNome(@Param("mes") int mes, @Param("nomeFuncionario") String nomeFuncionario);
}