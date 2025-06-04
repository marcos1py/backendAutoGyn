package com.sistemaOficina.backend.service.Implemente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Modelo;
import com.sistemaOficina.backend.repository.ModeloRepository;
import com.sistemaOficina.backend.service.ModeloService;

@Service
public class ModeloServiceImpl implements ModeloService {

    private final ModeloRepository modeloRepository;

    public ModeloServiceImpl(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    @Override
    public void salvar(Modelo modelo) {
        modeloRepository.save(modelo);
    }

    @Override
    public void atualizar(Modelo modelo) {
        modeloRepository.save(modelo);
    }

    @Override
    public void deletar(Integer id) {
        modeloRepository.deleteById(id);
    }

    @Override
    public Modelo buscarPorId(Integer id) {
        return modeloRepository.findById(id).orElse(null);
    }

    @Override
    public List<Modelo> buscarTodos() {
        return modeloRepository.findAll();
    }
}
