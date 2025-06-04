package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entidade.Modelo;
import com.sistemaOficina.backend.service.ModeloService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Modelo modelo) {
        modeloService.salvar(modelo);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Modelo modelo) {
        modelo.setId(id);
        modeloService.atualizar(modelo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        modeloService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Integer id) {
        Modelo modelo = modeloService.buscarPorId(id);
        return modelo != null ? ResponseEntity.ok(modelo) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> buscarTodos() {
        return ResponseEntity.ok(modeloService.buscarTodos());
    }
}
