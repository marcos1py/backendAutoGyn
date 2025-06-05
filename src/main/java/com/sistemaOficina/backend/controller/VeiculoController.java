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
import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.service.VeiculoService;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Veiculo veiculo) {
        try {
            veiculoService.salvar(veiculo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar veículo: " + e.getMessage()));
        }
    }

    @PutMapping("/{placa}")
    public ResponseEntity<?> atualizar(@PathVariable String placa, @RequestBody Veiculo veiculo) {
        Veiculo veiculoExistente = veiculoService.buscarPorPlaca(placa);
        if (veiculoExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Veículo não encontrado"));
        }
        
        try {
            veiculoService.atualizar(placa, veiculo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar veículo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> deletar(@PathVariable String placa) {
        Veiculo veiculoExistente = veiculoService.buscarPorPlaca(placa);
        if (veiculoExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Veículo não encontrado"));
        }
        
        try {
            veiculoService.deletar(placa);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar veículo: " + e.getMessage()));
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        Veiculo veiculo = veiculoService.buscarPorPlaca(placa);
        if (veiculo == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Veículo não encontrado"));
        }
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> buscarTodos() {
        List<Veiculo> veiculos = veiculoService.buscarTodos();
        return ResponseEntity.ok(veiculos);
    }
}
