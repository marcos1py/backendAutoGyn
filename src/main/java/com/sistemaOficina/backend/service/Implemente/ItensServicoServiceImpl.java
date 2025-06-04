package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.repository.ItensServicoRepository;
import com.sistemaOficina.backend.service.ItensServicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensServicoServiceImpl implements ItensServicoService {

    private final ItensServicoRepository itensServicoRepository;

    public ItensServicoServiceImpl(ItensServicoRepository itensServicoRepository) {
        this.itensServicoRepository = itensServicoRepository;
    }

    @Override
    public void salvar(ItensServico itensServico) {
        itensServicoRepository.save(itensServico);
    }

    @Override
    public void atualizar(ItensServico itensServico) {
        itensServicoRepository.save(itensServico);
    }

    @Override
    public void deletar(Integer id) {
        itensServicoRepository.deleteById(id);
    }

    @Override
    public ItensServico buscarPorId(Integer id) {
        return itensServicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<ItensServico> buscarTodos() {
        return itensServicoRepository.findAll();
    }

    @Override
    public List<ItensServico> buscarPorNumeroOs(Integer numeroOs) {
        // For now, return all items - this would need a custom query in a real implementation
        return itensServicoRepository.findAll();
    }
}
