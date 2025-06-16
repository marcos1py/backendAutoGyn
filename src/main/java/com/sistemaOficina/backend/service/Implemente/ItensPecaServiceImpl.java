package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.repository.ItensPecaRepository;
import com.sistemaOficina.backend.service.ItensPecaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensPecaServiceImpl implements ItensPecaService {

    private final ItensPecaRepository itensPecaRepository;

    public ItensPecaServiceImpl(ItensPecaRepository itensPecaRepository) {
        this.itensPecaRepository = itensPecaRepository;
    }

    @Override
    public void salvar(ItensPeca itensPeca) {
        itensPecaRepository.save(itensPeca);
    }

    @Override
    public void atualizar(ItensPeca itensPeca) {
        itensPecaRepository.save(itensPeca);
    }

    @Override
    public void deletar(Integer id) {
        itensPecaRepository.deleteById(id);
    }

    @Override
    public ItensPeca buscarPorId(Integer id) {
        return itensPecaRepository.findById(id).orElse(null);
    }

    @Override
    public List<ItensPeca> buscarTodos() {
        return itensPecaRepository.findAll();
    }

    @Override
    public List<ItensPeca> buscarPorNumeroOs(Integer numeroOs) {
        // For now, return all items - this would need a custom query in a real implementation
        return itensPecaRepository.findByNumeroOs_Numero(numeroOs);
    }
}
