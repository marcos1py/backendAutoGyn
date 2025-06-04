package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.Cliente;

public interface ClienteService {
    void salvar(Cliente cliente);
    List<Cliente> buscarTodos();
    Cliente buscarPorId(Integer id);
    void deletar(Integer id);
}
