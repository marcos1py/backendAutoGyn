package com.sistemaOficina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaOficina.backend.entidade.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
	List<Modelo> findByMarcaId(Integer marcaId);
}
