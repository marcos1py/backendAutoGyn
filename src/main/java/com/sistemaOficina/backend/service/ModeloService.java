package com.sistemaOficina.backend.service;

import com.sistemaOficina.backend.entidade.Modelo;

import java.util.List;

public interface ModeloService {
    void salvar(Modelo modelo);
    void atualizar(Modelo modelo);
    void deletar(Integer id);
    Modelo buscarPorId(Integer id);
    List<Modelo>  buscarPorIdMarca(Integer id);
    List<Modelo> buscarTodos();
}
