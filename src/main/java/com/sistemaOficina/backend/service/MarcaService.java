package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Marca;

public interface MarcaService {
    void salvar(Marca marca);
    void atualizar(Marca marca);
    void deletar(Integer id);
    Marca buscarPorId(Integer id);
    List<Marca> buscarTodos();
}
