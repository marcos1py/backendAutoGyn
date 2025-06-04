package com.sistemaOficina.backend.service.Implemente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.repository.PecasRepository;
import com.sistemaOficina.backend.service.PecasService;

@Service
public class PecasServiceImpl implements PecasService {

    private final PecasRepository pecasRepository;

    public PecasServiceImpl(PecasRepository pecasRepository) {
        this.pecasRepository = pecasRepository;
    }

    @Override
    public void salvar(Pecas pecas) {
        pecasRepository.save(pecas);
    }

    @Override
    public void atualizar(Pecas pecas) {
        pecasRepository.save(pecas);
    }

    @Override
    public void deletar(Integer id) {
        pecasRepository.deleteById(id);
    }

    @Override
    public Pecas buscarPorId(Integer id) {
        return pecasRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pecas> buscarTodos() {
        return pecasRepository.findAll();
    }
}
