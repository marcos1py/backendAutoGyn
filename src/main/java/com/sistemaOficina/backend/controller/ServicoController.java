package com.sistemaOficina.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaOficina.backend.dto.ErrorResponse;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        List<Servico> servicos = servicoService.buscarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Servico servico = servicoService.buscarPorId(id);
        if (servico == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Serviço não encontrado"));
        }
        return ResponseEntity.ok(servico);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Servico servico) {
        try {
            servicoService.salvar(servico);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar serviço: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Servico servico) {
        Servico servicoExistente = servicoService.buscarPorId(id);
        if (servicoExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Serviço não encontrado"));
        }
        
        try {
            servico.setId(id);
            servicoService.atualizar(servico);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar serviço: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Servico servicoExistente = servicoService.buscarPorId(id);
        if (servicoExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Serviço não encontrado"));
        }
        
        try {
            servicoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar serviço: " + e.getMessage()));
        }
    }
}
