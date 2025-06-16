package com.sistemaOficina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaOficina.backend.entidade.ItensPeca;

@Repository
public interface ItensPecaRepository extends JpaRepository<ItensPeca, Integer> {
	List<ItensPeca>findByNumeroOs_Numero(Integer numeroOs);
}
