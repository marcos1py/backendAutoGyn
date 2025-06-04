package com.sistemaOficina.backend.service.Implemente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Acessorio;
import com.sistemaOficina.backend.repository.AcessorioRepository;
import com.sistemaOficina.backend.service.AcessorioService;

@Service
public class AcessorioServiceImpl implements AcessorioService {

    private final AcessorioRepository acessorioRepository;

    public AcessorioServiceImpl(AcessorioRepository acessorioRepository) {
        this.acessorioRepository = acessorioRepository;
    }

    @Override
    public void salvar(Acessorio acessorio) {
        acessorioRepository.save(acessorio);
    }

    @Override
    public void atualizar(Acessorio acessorio) {
        acessorioRepository.save(acessorio);
    }

    @Override
    public void deletar(Integer id) {
        acessorioRepository.deleteById(id);
    }

    @Override
    public Acessorio buscarPorId(Integer id) {
        return acessorioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Acessorio> buscarTodos() {
        return acessorioRepository.findAll();
    }
}
