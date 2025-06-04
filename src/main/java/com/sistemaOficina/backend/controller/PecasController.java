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
    public ResponseEntity<Pecas> buscarPorId(@PathVariable Integer id) {
        Pecas pecas = pecasService.buscarPorId(id);
        if (pecas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pecas);
    }

    // Criar nova peça
    @PostMapping
    public ResponseEntity<Void> criarPeca(@RequestBody Pecas novaPeca) {
        pecasService.salvar(novaPeca);
        return ResponseEntity.ok().build();
    }

    // Atualizar uma peça existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPeca(@PathVariable Integer id, @RequestBody Pecas pecaAtualizada) {
        Pecas pecaExistente = pecasService.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pecaAtualizada.setId(pecaExistente.getId());
        pecasService.atualizar(pecaAtualizada);
        return ResponseEntity.ok().build();
    }

    // Deletar uma peça
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Integer id) {
        Pecas pecaExistente = pecasService.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pecasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
