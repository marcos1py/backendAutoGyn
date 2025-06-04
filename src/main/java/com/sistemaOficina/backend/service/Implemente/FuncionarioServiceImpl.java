package com.sistemaOficina.backend.service.Implemente;

import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.repository.FuncionarioRepository;
import com.sistemaOficina.backend.service.FuncionarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public void salvar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    @Override
    public void deletar(Integer id) {
        funcionarioRepository.deleteById(id);
    }

    @Override
    public Funcionario buscarPorId(Integer id) {
        return funcionarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }
}
