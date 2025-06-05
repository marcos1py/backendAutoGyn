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
import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // Método para salvar um funcionário (POST)
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Funcionario funcionario) {
        try {
            funcionarioService.salvar(funcionario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao criar funcionário: " + e.getMessage()));
        }
    }

    // Método para atualizar um funcionário (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioService.buscarPorId(id);
        if (funcionarioExistente == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Funcionário não encontrado"));
        }
        
        try {
            funcionario.setId(funcionarioExistente.getId());
            funcionarioService.atualizar(funcionario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao atualizar funcionário: " + e.getMessage()));
        }
    }

    // Método para deletar um funcionário (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Funcionário não encontrado"));
        }
        
        try {
            funcionarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Erro ao deletar funcionário: " + e.getMessage()));
        }
    }

    // Método para buscar um funcionário por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Funcionário não encontrado"));
        }
        return ResponseEntity.ok(funcionario);
    }

    // Método para buscar todos os funcionários (GET)
    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarTodos() {
        List<Funcionario> funcionarios = funcionarioService.buscarTodos();
        return ResponseEntity.ok(funcionarios);
    }
}
