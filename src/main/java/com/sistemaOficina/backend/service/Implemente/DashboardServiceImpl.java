package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.repository.OrdemServicoRepository;
import com.sistemaOficina.backend.repository.FuncionarioRepository;
import com.sistemaOficina.backend.repository.PecasRepository;
import com.sistemaOficina.backend.repository.ServicoRepository;
import com.sistemaOficina.backend.repository.ClienteRepository;
import com.sistemaOficina.backend.repository.VeiculoRepository;
import com.sistemaOficina.backend.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PecasRepository pecasRepository;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    public DashboardServiceImpl(OrdemServicoRepository ordemServicoRepository,
                              FuncionarioRepository funcionarioRepository,
                              PecasRepository pecasRepository,
                              ServicoRepository servicoRepository,
                              ClienteRepository clienteRepository,
                              VeiculoRepository veiculoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pecasRepository = pecasRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public int getTotalOrdensDeServico() {
        return (int) ordemServicoRepository.count();
    }

    @Override
    public BigDecimal getReceitaTotal() {
        // Return a default value as we cannot access the precoFinal field
        // In a real implementation, this would need a custom query
        return BigDecimal.valueOf(0.0);
    }

    @Override
    public String getFuncionarioComMaisServicos() {
        // Return a default value for now
        long count = funcionarioRepository.count();
        return count > 0 ? "Funcionário Disponível" : "Nenhum funcionário encontrado";
    }

    @Override
    public String getPecaMaisVendida() {
        // Return a default value for now
        long count = pecasRepository.count();
        return count > 0 ? "Peça Disponível" : "Nenhuma peça encontrada";
    }

    @Override
    public String getServicoMaisSolicitado() {
        // Return a default value for now
        long count = servicoRepository.count();
        return count > 0 ? "Serviço Disponível" : "Nenhum serviço encontrado";
    }

    @Override
    public String getClienteComMaisOrdensDeServico() {
        // Return a default value for now
        long count = clienteRepository.count();
        return count > 0 ? "Cliente Disponível" : "Nenhum cliente encontrado";
    }

    @Override
    public String getVeiculoMaisAtendido() {
        // Return a default value for now
        long count = veiculoRepository.count();
        return count > 0 ? "Veículo Disponível" : "Nenhum veículo encontrado";
    }
}
