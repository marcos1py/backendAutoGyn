package com.sistemaOficina.backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Cadastrar um novo cliente
    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
        if (cliente.getTipoCliente() == null || cliente.getTipoCliente().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de cliente é obrigatório.");
        }
        if ("PessoaFisica".equals(cliente.getTipoCliente()) && (cliente.getCpf() == null || cliente.getCpf().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF é obrigatório para Pessoa Física.");
        }
        if ("PessoaJuridica".equals(cliente.getTipoCliente()) && (cliente.getCnpj() == null || cliente.getCnpj().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ é obrigatório para Pessoa Jurídica.");
        }

        clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso.");
    }

    // Buscar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = clienteService.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cliente);
    }

    // Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteService.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        cliente.setId(id);
        clienteService.salvar(cliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso.");
    }

    // Deletar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        Cliente clienteExistente = clienteService.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        clienteService.deletar(id);
        return ResponseEntity.ok("Cliente deletado com sucesso.");
    }
}
