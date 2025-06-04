package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.service.ServicoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public List<Servico> listarTodos() {
        return servicoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Integer id) {
        return servicoService.buscarPorId(id);
    }

    @PostMapping
    public void salvar(@RequestBody Servico servico) {
        servicoService.salvar(servico);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Servico servico) {
        servico.setId(id);
        servicoService.atualizar(servico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        servicoService.deletar(id);
    }
}
