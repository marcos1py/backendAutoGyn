package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.repository.ClienteRepository;
import com.sistemaOficina.backend.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deletar(Integer id) {
        clienteRepository.deleteById(id);
    }
}
