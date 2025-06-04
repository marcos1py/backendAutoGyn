package com.sistemaOficina.backend.service.Implemente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Marca;
import com.sistemaOficina.backend.repository.MarcaRepository;
import com.sistemaOficina.backend.service.MarcaService;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public void salvar(Marca marca) {
        marcaRepository.save(marca);
    }

    @Override
    public void atualizar(Marca marca) {
        marcaRepository.save(marca);
    }

    @Override
    public void deletar(Integer id) {
        marcaRepository.deleteById(id);
    }

    @Override
    public Marca buscarPorId(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Marca> buscarTodos() {
        return marcaRepository.findAll();
    }
}
