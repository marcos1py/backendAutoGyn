package com.sistemaOficina.backend.service;

import java.util.List;

import com.sistemaOficina.backend.entidade.OrdemServico;

public interface OrdemServicoService {
    void salvar(OrdemServico ordemServico);
    void atualizar(OrdemServico ordemServico);
    int buscarQuantidadePorPecaEOrdemServico(Integer idPeca, Integer idOrdemServico);
    Integer buscarUltimoNumeroOs();
    void deletar(Integer id);
    OrdemServico buscarPorId(Integer id);
    List<OrdemServico> buscarTodos();
}
