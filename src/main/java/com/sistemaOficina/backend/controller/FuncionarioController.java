package com.sistemaOficina.backend.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody Funcionario funcionario) {
    	funcionario.toString();
        funcionarioService.salvar(funcionario);
    }

    // Método para atualizar um funcionário (PUT)
    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioService.buscarPorId(id);
        if (funcionarioExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        funcionario.setId(funcionarioExistente.getId());
        funcionarioService.atualizar(funcionario);
    }

    // Método para deletar um funcionário (DELETE)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        funcionarioService.deletar(id);
    }

    // Método para buscar um funcionário por ID (GET)
    @GetMapping("/{id}")
    public Funcionario buscarPorId(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        return funcionario;
    }

    // Método para buscar todos os funcionários (GET)
    @GetMapping
    public List<Funcionario> buscarTodos() {
        return funcionarioService.buscarTodos();
    }
}
