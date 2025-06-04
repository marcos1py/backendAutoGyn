package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.*;

public interface VeiculoService {
    void salvar(Veiculo veiculo);
    void atualizar(String placa, Veiculo veiculo);
    void deletar(String id);
    Veiculo buscarPorPlaca(String placa);
    List<Veiculo> buscarTodos();
}
