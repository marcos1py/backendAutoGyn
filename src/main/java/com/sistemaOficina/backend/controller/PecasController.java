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
import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.service.PecasService;

@RestController
@RequestMapping("/api/pecas")
public class PecasController {

    private final PecasService pecasService;

    @Autowired
    public PecasController(PecasService pecasService) {
        this.pecasService = pecasService;
    }

    // Listar todas as peças
    @GetMapping
    public ResponseEntity<List<Pecas>> listarTodas() {
        List<Pecas> pecas = pecasService.buscarTodos();
        return ResponseEntity.ok(pecas);
    }

    // Buscar peça por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Pecas pecas = pecasService.buscarPorId(id);
        if (pecas == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Peça não encontrada"));
        }
        return ResponseEntity.ok(pecas);
    }

    // Criar nova peça
    @PostMapping
    public ResponseEntity<?> criarPeca(@RequestBody Pecas novaPeca) {
        try {
            pecasService.salvar(novaPeca);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar peça: " + e.getMessage()));
        }
    }

    // Atualizar uma peça existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPeca(@PathVariable Integer id, @RequestBody Pecas pecaAtualizada) {
        Pecas pecaExistente = pecasService.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Peça não encontrada"));
        }

        try {
            pecaAtualizada.setId(pecaExistente.getId());
            pecasService.atualizar(pecaAtualizada);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar peça: " + e.getMessage()));
        }
    }

    // Deletar uma peça
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPeca(@PathVariable Integer id) {
        Pecas pecaExistente = pecasService.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Peça não encontrada"));
        }

        try {
            pecasService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar peça: " + e.getMessage()));

        }
    }
}
