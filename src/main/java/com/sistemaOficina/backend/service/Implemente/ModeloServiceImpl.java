package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.Modelo;
import com.sistemaOficina.backend.repository.ModeloRepository;
import com.sistemaOficina.backend.service.ModeloService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Modelo> buscarPorIdMarca(Integer id) {
        return modeloRepository.findByMarcaId(id);
    }


    @Override
    public List<Modelo> buscarTodos() {
        return modeloRepository.findAll();
    }
}
