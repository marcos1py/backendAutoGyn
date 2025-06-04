package com.sistemaOficina.backend.service.Implemente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Oficina;
import com.sistemaOficina.backend.repository.OficinaRepository;
import com.sistemaOficina.backend.service.OficinaService;

@Service
public class OficinaServiceImpl implements OficinaService {

    private final OficinaRepository oficinaRepository;

    public OficinaServiceImpl(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    @Override
    public void salvar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    @Override
    public void atualizar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    @Override
    public void deletar(Integer id) {
        oficinaRepository.deleteById(id);
    }

    @Override
    public Oficina buscarPorId(Integer id) {
        return oficinaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Oficina> buscarTodos() {
        return oficinaRepository.findAll();
    }
}
