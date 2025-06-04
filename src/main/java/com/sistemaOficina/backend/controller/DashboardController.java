package com.sistemaOficina.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaOficina.backend.service.DashboardService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Endpoint para obter os dados do dashboard
     */
    @GetMapping
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();

        dashboardData.put("totalOrdensDeServico", dashboardService.getTotalOrdensDeServico());
        dashboardData.put("receitaTotal", dashboardService.getReceitaTotal());
        dashboardData.put("funcionarioComMaisServicos", dashboardService.getFuncionarioComMaisServicos());
        dashboardData.put("pecaMaisVendida", dashboardService.getPecaMaisVendida());
        dashboardData.put("servicoMaisSolicitado", dashboardService.getServicoMaisSolicitado());
        dashboardData.put("clienteComMaisOrdensDeServico", dashboardService.getClienteComMaisOrdensDeServico());
        dashboardData.put("veiculoMaisAtendido", dashboardService.getVeiculoMaisAtendido());

        return dashboardData;
    }
}
