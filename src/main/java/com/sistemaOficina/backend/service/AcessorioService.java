package com.sistemaOficina.backend.service;
import java.util.List;

import com.sistemaOficina.backend.entidade.Acessorio;


public interface AcessorioService {
    void salvar(Acessorio acessorio);
    void atualizar(Acessorio acessorio);
    void deletar(Integer id);
    Acessorio buscarPorId(Integer id);
    List<Acessorio> buscarTodos();
}
