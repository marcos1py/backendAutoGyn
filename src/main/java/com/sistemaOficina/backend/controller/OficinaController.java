package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Oficina;
import com.sistemaOficina.backend.service.OficinaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaController {
    
    private final OficinaService oficinaService;

    public OficinaController(OficinaService oficinaService) {
        this.oficinaService = oficinaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody Oficina oficina) {
        oficinaService.salvar(oficina);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Oficina oficina) {
        oficina.setId(id);
        oficinaService.atualizar(oficina);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        oficinaService.deletar(id);
    }

    @GetMapping("/{id}")
    public Oficina buscarPorId(@PathVariable Integer id) {
        Oficina oficina = oficinaService.buscarPorId(id);
        if (oficina == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oficina não encontrada");
        }
        return oficina;
    }

    @GetMapping
    public List<Oficina> buscarTodos() {
        return oficinaService.buscarTodos();
    }
}
