package com.sistemaOficina.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaOficina.backend.dto.ConsultaServico;
import com.sistemaOficina.backend.dto.ErrorResponse;
import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.service.ConsultaServicoService;

@RestController
@RequestMapping("/api/consulta-servico")
public class ConsultaServicoController {

    @Autowired
    private ConsultaServicoService consultaServicoService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ConsultaServico consulta) {
        System.out.println(consulta);
        System.out.println(consulta.getExpressao());
        return ResponseEntity.ok(consultaServicoService.execute(consulta.getExpressao()).size());
    }
}
