package com.sistemaOficina.backend.controller;

import java.util.List;

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
import com.sistemaOficina.backend.entidade.Acessorio;
import com.sistemaOficina.backend.service.AcessorioService;

@RestController
@RequestMapping("/api/acessorios")
public class AcessorioController {
    private final AcessorioService acessorioService;


    public AcessorioController(AcessorioService acessorioService) {
        this.acessorioService = acessorioService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Acessorio acessorio) {
        try {
            acessorioService.salvar(acessorio);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar acessório: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Acessorio acessorio) {
        Acessorio acessorioExistente = acessorioService.buscarPorId(id);
        if (acessorioExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Acessório não encontrado"));
        }
        
        try {
            acessorio.setId(id);
            acessorioService.atualizar(acessorio);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar acessório: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Acessorio acessorioExistente = acessorioService.buscarPorId(id);
        if (acessorioExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Acessório não encontrado"));
        }
        
        try {
            acessorioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar acessório: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Acessorio acessorio = acessorioService.buscarPorId(id);
        if (acessorio == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Acessório não encontrado"));
        }
        return ResponseEntity.ok(acessorio);
    }

    @GetMapping
    public ResponseEntity<List<Acessorio>> buscarTodos() {
        List<Acessorio> acessorios = acessorioService.buscarTodos();
        return ResponseEntity.ok(acessorios);
    }
}
