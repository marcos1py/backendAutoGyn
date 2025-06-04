package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Servico;

public interface ServicoService {
    void salvar(Servico servico);
    void atualizar(Servico servico);
    void deletar(Integer id);
    Servico buscarPorId(Integer id);
    List<Servico> buscarTodos();
}
