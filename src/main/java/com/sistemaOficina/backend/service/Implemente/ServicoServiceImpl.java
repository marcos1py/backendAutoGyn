package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.repository.ServicoRepository;
import com.sistemaOficina.backend.service.ServicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoServiceImpl(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Override
    public void salvar(Servico servico) {
        servicoRepository.save(servico);
    }

    @Override
    public void atualizar(Servico servico) {
        servicoRepository.save(servico);
    }

    @Override
    public void deletar(Integer id) {
        servicoRepository.deleteById(id);
    }

    @Override
    public Servico buscarPorId(Integer id) {
        return servicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Servico> buscarTodos() {
        return servicoRepository.findAll();
    }
}
