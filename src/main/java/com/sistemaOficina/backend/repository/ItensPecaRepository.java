package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.ItensPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensPecaRepository extends JpaRepository<ItensPeca, Integer> {
}
