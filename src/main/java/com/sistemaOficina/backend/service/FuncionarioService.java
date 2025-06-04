package com.sistemaOficina.backend.service;
import java.util.List;

import com.sistemaOficina.backend.entidade.Funcionario;


public interface FuncionarioService {
    void salvar(Funcionario funcionario);
    void atualizar(Funcionario funcionario);
    void deletar(Integer id);
    Funcionario buscarPorId(Integer id);
    List<Funcionario> buscarTodos();
}
