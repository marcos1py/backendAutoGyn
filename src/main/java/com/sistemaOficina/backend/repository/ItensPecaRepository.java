package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.ItensPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPecaRepository extends JpaRepository<ItensPeca, Integer> {
    List<ItensPeca> findByNumeroOsNumero(Integer numeroOs);
}
