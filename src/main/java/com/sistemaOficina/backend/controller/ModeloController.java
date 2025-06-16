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
import com.sistemaOficina.backend.entidade.Modelo;
import com.sistemaOficina.backend.service.ModeloService;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Modelo modelo) {
        try {
            modeloService.salvar(modelo);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar modelo: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Modelo modelo) {
        Modelo modeloExistente = modeloService.buscarPorId(id);
        if (modeloExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Modelo não encontrado"));
        }
        
        try {
            modelo.setId(id);
            modeloService.atualizar(modelo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar modelo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Modelo modeloExistente = modeloService.buscarPorId(id);
        if (modeloExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Modelo não encontrado"));
        }
        
        try {
            modeloService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar modelo: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Modelo modelo = modeloService.buscarPorId(id);
        if (modelo == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Modelo não encontrado"));
        }
        return ResponseEntity.ok(modelo);
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> buscarTodos() {
        List<Modelo> modelos = modeloService.buscarTodos();
        return ResponseEntity.ok(modelos);
    }
    
    @GetMapping("/marcas/{id}")
    public ResponseEntity<List<Modelo>> findByMarcaId(@PathVariable Integer id) {
        List<Modelo> modelos = modeloService.findByMarcaId(id);
        return ResponseEntity.ok(modelos);
    }
}
