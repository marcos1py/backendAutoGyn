package com.sistemaOficina.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.sistemaOficina.backend.entidade.Oficina;
import com.sistemaOficina.backend.service.OficinaService;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaController {
    
    private final OficinaService oficinaService;

    public OficinaController(OficinaService oficinaService) {
        this.oficinaService = oficinaService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Oficina oficina) {
        try {
            oficinaService.salvar(oficina);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar oficina: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Oficina oficina) {
        Oficina oficinaExistente = oficinaService.buscarPorId(id);
        if (oficinaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Oficina não encontrada"));
        }
        
        try {
            oficina.setId(id);
            oficinaService.atualizar(oficina);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar oficina: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Oficina oficinaExistente = oficinaService.buscarPorId(id);
        if (oficinaExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Oficina não encontrada"));
        }
        
        try {
            oficinaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar oficina: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Oficina oficina = oficinaService.buscarPorId(id);
        if (oficina == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Oficina não encontrada"));
        }
        return ResponseEntity.ok(oficina);
    }

    @GetMapping
    public ResponseEntity<List<Oficina>> buscarTodos() {
        List<Oficina> oficinas = oficinaService.buscarTodos();
        return ResponseEntity.ok(oficinas);
    }
}
