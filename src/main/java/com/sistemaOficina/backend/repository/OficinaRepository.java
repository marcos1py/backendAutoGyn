package com.sistemaOficina.backend.repository;

import com.sistemaOficina.backend.entidade.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {
}
