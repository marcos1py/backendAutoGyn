package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Modelo;

public interface ModeloService {
    void salvar(Modelo modelo);
    void atualizar(Modelo modelo);
    void deletar(Integer id);
    Modelo buscarPorId(Integer id);
    List<Modelo> buscarTodos();
}
