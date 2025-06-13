package com.sistemaOficina.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.dto.ItensServicoResponse;
import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.parser.ConsultaExecutor;
import com.sistemaOficina.backend.parser.ConsultaExecutor.TemplateExecucaoConsulta;
import com.sistemaOficina.backend.parser.ConsultaServicosFactory;
import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosMes;
import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosPrestador;
import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosPrestadorMes;
import com.sistemaOficina.backend.parser.estrutura.ExpressaoServicos;
import com.sistemaOficina.backend.service.Implemente.ItensServicoServiceImpl;

@Service
public class ConsultaServicoService implements TemplateExecucaoConsulta {

    @Autowired
    private ItensServicoServiceImpl itensServicoService;

    @Autowired
    private ConsultaExecutor consultaExecutor;
    
    @Autowired
    private ConsultaServicosFactory consultaServicosFactory;

    public List<ItensServicoResponse> execute(String expressaoStr) {
        ExpressaoServicos expressao = this.consultaServicosFactory.instantiate(expressaoStr);
        
        List<ItensServico> lista = this.consultaExecutor.execute(expressao, this);
        return lista.stream()
            .map(ItensServicoResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<ItensServico> execute(ConsultaServicosMes exp) {
        return this.itensServicoService.buscarPorMes(exp.getMes().getNumero());
    }

    @Override
    public List<ItensServico> execute(ConsultaServicosPrestador exp) {
        return this.itensServicoService.buscarPorNomeFuncionario(exp.getPrestador().getNome());
    }

    @Override
    public List<ItensServico> execute(ConsultaServicosPrestadorMes exp) {
        return this.itensServicoService.buscarPorMesNomeFuncionario(exp.getMes().getNumero(), exp.getPrestador().getNome());
    }

    @Override
    public List<ItensServico> add(List exp0, List exp1) {
        List<ItensServico> added = new ArrayList<>();
        Set<Integer> addedIds = new HashSet<>();

        for (Object itemObj : exp0) {
            ItensServico item = (ItensServico) itemObj;
            if (!addedIds.contains(item.getId())) {
                added.add(item);
                addedIds.add(item.getId());
            }
        }

        for (Object itemObj : exp1) {
            ItensServico item = (ItensServico) itemObj;
            if (!addedIds.contains(item.getId())) {
                added.add(item);
                addedIds.add(item.getId());
            }
        }

        return added;
    }

    @Override
    public List<ItensServico> remove(List exp0, List exp1) {
        Set<Integer> toRemove = new HashSet<>();

        for (Object itemObj : exp1) {
            ItensServico item = (ItensServico) itemObj;
            toRemove.add(item.getId());
        }

        List<ItensServico> remaining = new ArrayList<>();

        for (Object itemObj : exp0) {
            ItensServico item = (ItensServico) itemObj;
            if (!toRemove.contains(item.getId())) {
                remaining.add(item);
            }
        }

        return remaining;
    }
}
