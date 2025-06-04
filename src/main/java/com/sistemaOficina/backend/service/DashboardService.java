package com.sistemaOficina.backend.service;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardService {
    int getTotalOrdensDeServico();
    BigDecimal getReceitaTotal();
    String getFuncionarioComMaisServicos();
    String getPecaMaisVendida();
    String getServicoMaisSolicitado();
    String getClienteComMaisOrdensDeServico();
    String getVeiculoMaisAtendido();
}
