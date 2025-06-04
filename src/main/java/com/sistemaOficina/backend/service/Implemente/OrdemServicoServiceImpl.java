package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.repository.OrdemServicoRepository;
import com.sistemaOficina.backend.service.OrdemServicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemServicoServiceImpl implements OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoServiceImpl(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @Override
    public void salvar(OrdemServico ordemServico) {
        ordemServicoRepository.save(ordemServico);
    }

    @Override
    public void atualizar(OrdemServico ordemServico) {
        ordemServicoRepository.save(ordemServico);
    }

    @Override
    public int buscarQuantidadePorPecaEOrdemServico(Integer idPeca, Integer idOrdemServico) {
        // Esta implementação seria mais complexa - precisaria de query customizada
        // Por enquanto retornando 0 como placeholder
        return 0;
    }

    @Override
    public Integer buscarUltimoNumeroOs() {
        Integer maxId = ordemServicoRepository.findMaxId();
        return maxId != null ? maxId : 0;
    }

    @Override
    public void deletar(Integer id) {
        ordemServicoRepository.deleteById(id);
    }

    @Override
    public OrdemServico buscarPorId(Integer id) {
        return ordemServicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrdemServico> buscarTodos() {
        return ordemServicoRepository.findAll();
    }
}
