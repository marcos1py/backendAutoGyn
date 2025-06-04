package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.service.VeiculoService;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    // Método para salvar um veículo (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) 
    public void salvar(@RequestBody Veiculo veiculo) {
        veiculoService.salvar(veiculo);
    }

    // Método para atualizar um veículo (PUT)
    @PutMapping("/{placa}")
    public void atualizar(@PathVariable String placa, @RequestBody Veiculo veiculo) {
        veiculoService.atualizar(placa, veiculo);
    }

    // Método para deletar um veículo (DELETE)
    @DeleteMapping("/{placa}")
    public void deletar(@PathVariable String placa) {
        veiculoService.deletar(placa);
    }

    // Método para buscar um veículo por placa (GET)
    @GetMapping("/{placa}")
    public Veiculo buscarPorPlaca(@PathVariable String placa) {
        Veiculo veiculo = veiculoService.buscarPorPlaca(placa);
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }
        return veiculo; // Retorna o veículo encontrado
    }

    // Método para buscar todos os veículos (GET)
    @GetMapping
    public List<Veiculo> buscarTodos() {
        return veiculoService.buscarTodos(); // Retorna uma lista de todos os veículos
    }
}
