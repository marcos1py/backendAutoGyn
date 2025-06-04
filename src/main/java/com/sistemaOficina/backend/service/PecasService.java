package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Pecas;

public interface PecasService {
    void salvar(Pecas pecas);
    void atualizar(Pecas pecas);
    void deletar(Integer id);
    Pecas buscarPorId(Integer id);
    List<Pecas> buscarTodos();
}
