package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.repository.VeiculoRepository;
import com.sistemaOficina.backend.service.VeiculoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoServiceImpl(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public void salvar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    @Override
    public void atualizar(String placa, Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    @Override
    public void deletar(String placa) {
        // Assumindo que precisa buscar por placa primeiro
        Veiculo veiculo = buscarPorPlaca(placa);
        if (veiculo != null) {
            veiculoRepository.delete(veiculo);
        }
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa).orElse(null);
    }

    @Override
    public List<Veiculo> buscarTodos() {
        return veiculoRepository.findAll();
    }
}
