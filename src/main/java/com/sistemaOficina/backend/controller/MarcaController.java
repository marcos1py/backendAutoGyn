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
import com.sistemaOficina.backend.entidade.Marca;
import com.sistemaOficina.backend.service.MarcaService;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<Marca>> listarTodas() {
        List<Marca> marcas = marcaService.buscarTodos();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Marca marca = marcaService.buscarPorId(id);
        if (marca == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Marca não encontrada"));
        }
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Marca marca) {
        try {
            marcaService.salvar(marca);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar marca: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Marca marca) {
        Marca marcaExistente = marcaService.buscarPorId(id);
        if (marcaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Marca não encontrada"));
        }
        
        try {
            marca.setId(id);
            marcaService.atualizar(marca);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar marca: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Marca marcaExistente = marcaService.buscarPorId(id);
        if (marcaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Marca não encontrada"));
        }
        
        try {
            marcaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar marca: " + e.getMessage()));
        }
    }
}
