package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entidade.Marca;
import com.sistemaOficina.backend.service.MarcaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public List<Marca> listarTodas() {
        return marcaService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Marca buscarPorId(@PathVariable Integer id) {
        return marcaService.buscarPorId(id);
    }

    @PostMapping
    public void salvar(@RequestBody Marca marca) {
        marcaService.salvar(marca);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Marca marca) {
        marca.setId(id);
        marcaService.atualizar(marca);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        marcaService.deletar(id);
    }
}
