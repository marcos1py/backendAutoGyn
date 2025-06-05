package com.sistemaOficina.backend.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaOficina.backend.dto.ErrorResponse;
import com.sistemaOficina.backend.service.DashboardService;

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
    public ResponseEntity<?> getDashboardData() {
        try {
            Map<String, Object> dashboardData = new HashMap<>();

            dashboardData.put("totalOrdensDeServico", dashboardService.getTotalOrdensDeServico());
            dashboardData.put("receitaTotal", dashboardService.getReceitaTotal());
            dashboardData.put("funcionarioComMaisServicos", dashboardService.getFuncionarioComMaisServicos());
            dashboardData.put("pecaMaisVendida", dashboardService.getPecaMaisVendida());
            dashboardData.put("servicoMaisSolicitado", dashboardService.getServicoMaisSolicitado());
            dashboardData.put("clienteComMaisOrdensDeServico", dashboardService.getClienteComMaisOrdensDeServico());
            dashboardData.put("veiculoMaisAtendido", dashboardService.getVeiculoMaisAtendido());

            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Erro ao buscar dados do dashboard: " + e.getMessage()));
        }
    }
}
