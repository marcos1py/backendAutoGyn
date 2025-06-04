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
    public ResponseEntity<String> salvar(@RequestBody Acessorio acessorio) {
        acessorioService.salvar(acessorio);
        return ResponseEntity.ok("Acessório salvo com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody Acessorio acessorio) {
        acessorio.setId(id);
        acessorioService.atualizar(acessorio);
        return ResponseEntity.ok("Acessório atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        acessorioService.deletar(id);
        return ResponseEntity.ok("Acessório deletado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acessorio> buscarPorId(@PathVariable Integer id) {
        Acessorio acessorio = acessorioService.buscarPorId(id);
        if (acessorio != null) {
            return ResponseEntity.ok(acessorio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Acessorio>> buscarTodos() {
        List<Acessorio> acessorios = acessorioService.buscarTodos();
        return ResponseEntity.ok(acessorios);
    }
}
