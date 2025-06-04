package com.sistemaOficina.backend.service;
import com.sistemaOficina.backend.entidade.*;

import java.util.List;

public interface ItensPecaService {
    void salvar(ItensPeca itensPeca);
    void atualizar(ItensPeca itensPeca);
    void deletar(Integer id);
    ItensPeca buscarPorId(Integer id);
    List<ItensPeca> buscarTodos();
    List<ItensPeca> buscarPorNumeroOs(Integer numeroOs);
}
