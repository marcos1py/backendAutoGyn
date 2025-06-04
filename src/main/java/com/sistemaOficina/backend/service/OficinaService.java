package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Oficina;

public interface OficinaService {
    void salvar(Oficina oficina);
    void atualizar(Oficina oficina);
    void deletar(Integer id);
    Oficina buscarPorId(Integer id);
    List<Oficina> buscarTodos();
}
