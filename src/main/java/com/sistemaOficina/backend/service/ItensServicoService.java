package com.sistemaOficina.backend.service;
import com.sistemaOficina.backend.entidade.*;

import java.util.List;

public interface ItensServicoService {
    void salvar(ItensServico itensServico);
    void atualizar(ItensServico itensServico);
    void deletar(Integer id);
    ItensServico buscarPorId(Integer id);
    List<ItensServico> buscarTodos();
    List<ItensServico> buscarPorNumeroOs(Integer numeroOs);
}