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
        return itensServicoRepository.findByNumeroOsNumero(numeroOs);
    }

    @Override
    public List<ItensServico> buscarPorNomeFuncionario(String nomeLike) {
        if (nomeLike == null || nomeLike.trim().isEmpty()) {
            return List.of();
        }
        return itensServicoRepository.findByFuncionarioNome(nomeLike.trim());
    }

    @Override
    public List<ItensServico> buscarPorMes(int mes) {
        return this.itensServicoRepository.findByMesAnoAtual(mes);
    }

    @Override
    public List<ItensServico> buscarPorMesNomeFuncionario(int mes, String nomeLike) {
        return this.itensServicoRepository.findByMesAnoAtualAndFuncionarioNome(mes, nomeLike);
    }
}
