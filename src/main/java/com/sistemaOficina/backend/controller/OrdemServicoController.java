package com.sistemaOficina.backend.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sistemaOficina.backend.dto.OrdemServicoRequest;
import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.service.ClienteService;
import com.sistemaOficina.backend.service.FuncionarioService;
import com.sistemaOficina.backend.service.ItensPecaService;
import com.sistemaOficina.backend.service.ItensServicoService;
import com.sistemaOficina.backend.service.OrdemServicoService;
import com.sistemaOficina.backend.service.PecasService;
import com.sistemaOficina.backend.service.ServicoService;
import com.sistemaOficina.backend.service.VeiculoService;

@RestController
@RequestMapping("/api/ordemServico")
public class OrdemServicoController {
    private final OrdemServicoService ordemServicoService;
    private final ItensPecaService itensPecaService;
    private final ClienteService clienteService;
    private final ItensServicoService itensServicoService;
    private final PecasService pecasService;
    private final ServicoService servicoService;
    private final FuncionarioService funcionarioService;
    private final VeiculoService veiculoService;

    @Autowired
    public OrdemServicoController(OrdemServicoService ordemServicoService,
                                  ItensPecaService itensPecaService,
                                  ClienteService clienteService,
                                  ItensServicoService itensServicoService,
                                  PecasService pecasService,
                                  ServicoService servicoService,
                                  FuncionarioService funcionarioService,
                                  VeiculoService veiculoService) {
        this.ordemServicoService = ordemServicoService;
        this.itensPecaService = itensPecaService;
        this.clienteService = clienteService;
        this.itensServicoService = itensServicoService;
        this.pecasService = pecasService;
        this.servicoService = servicoService;
        this.funcionarioService = funcionarioService;
        this.veiculoService = veiculoService;
    }

    @PostMapping
@ResponseStatus(HttpStatus.CREATED)
@Transactional
public void salvar(@RequestBody OrdemServicoRequest request) {
    double precoTotal = 0;

    // Gerar número da Ordem de Serviço
    Integer ultimoNumero = ordemServicoService.buscarUltimoNumeroOs();
    Integer numero = (ultimoNumero != null) ? ultimoNumero + 1 : 1;

    // Validar e buscar Veículo e Cliente
    Veiculo veiculo = veiculoService.buscarPorPlaca(request.getPlacaVeiculo());
    if (veiculo == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
    }

    Cliente cliente = clienteService.buscarPorId(request.getIdCliente());
    if (cliente == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }

    // Criar e salvar Ordem de Serviço
    OrdemServico ordemServico = new OrdemServico(
            numero,
            LocalDate.now(),
            0,
            request.getStatus(),
            veiculo,
            cliente
    );
    ordemServicoService.salvar(ordemServico);

    if (ordemServico.getNumero() == null) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao salvar a ordem de serviço");
    }

    // Processar Itens de Peça
    precoTotal += processarItensPeca(request.getItensPeca(), ordemServico);

    // Processar Itens de Serviço
    precoTotal += processarItensServico(request.getItensServico(), ordemServico);

    // Atualizar preço final na Ordem de Serviço
    ordemServico.setPrecoFinal(precoTotal);
    ordemServicoService.atualizar(ordemServico);
}

private double processarItensPeca(List<ItensPeca> itensPecaList, OrdemServico ordemServico) {
    double precoTotalPecas = 0;

    for (ItensPeca itensPecaRequest : itensPecaList) {
        Pecas peca = pecasService.buscarPorId(itensPecaRequest.getPeca().getId());
        int quantidadeUsada = itensPecaRequest.getQuantidade();

        if (quantidadeUsada > peca.getQuantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de peças insuficiente");
        }

        peca.setQuantidade(peca.getQuantidade() - quantidadeUsada);
        pecasService.atualizar(peca);

        double precoPecaTotal = quantidadeUsada * peca.getPrecoUnitario();
        precoTotalPecas += precoPecaTotal;

        ItensPeca itensPeca = new ItensPeca(
                0,
                precoPecaTotal,
                quantidadeUsada,
                ordemServico,
                peca
        );
        itensPecaService.salvar(itensPeca);
    }

    return precoTotalPecas;
}

private double processarItensServico(List<ItensServico> itensServicoList, OrdemServico ordemServico) {
    double precoTotalServicos = 0;

    for (ItensServico itensServicoRequest : itensServicoList) {
        Servico servico = servicoService.buscarPorId(itensServicoRequest.getIdServico().getId());
        Funcionario funcionario = funcionarioService.buscarPorId(itensServicoRequest.getIdFuncionario().getId());

        double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
        precoTotalServicos += precoServicoTotal;

        ItensServico itensServico = new ItensServico(
                0,
                itensServicoRequest.getHorarioInicio(),
                itensServicoRequest.getHorarioFim(),
                itensServicoRequest.getQuantidade(),
                precoServicoTotal,
                funcionario,
                servico,
                ordemServico
        );
        itensServicoService.salvar(itensServico);
    }

    return precoTotalServicos;
}

    
    @PutMapping("/{numero}")
    @Transactional
    public void salvarAlteracao(@PathVariable Integer numero, @RequestBody OrdemServicoRequest request) {
        double precoTotal = 0;
    
        // Verificar se a Ordem de Serviço existe
        OrdemServico ordemServico = ordemServicoService.buscarPorId(numero);
        if (ordemServico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada");
        }
    
        // Atualizar os detalhes da Ordem de Serviço
        Veiculo veiculo = veiculoService.buscarPorPlaca(request.getPlacaVeiculo());
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }

        Cliente cliente = clienteService.buscarPorId(request.getIdCliente());
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(request.getStatus());
        ordemServico.setPlacaVeiculo(veiculo);
    
        // Atualizar Itens de Peças
        for (ItensPeca itensPecaRequest : request.getItensPeca()) {
            Pecas peca = pecasService.buscarPorId(itensPecaRequest.getPeca().getId());
            if (peca == null) {
                System.out.println("DEBUG: Peça não encontrada - ID: " + itensPecaRequest.getPeca().getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Peça não encontrada");
            }
            
            
            // Recuperar a quantidade previamente usada na OS (se existir)
            int quantidadeAnterior = ordemServicoService.buscarQuantidadePorPecaEOrdemServico(
                itensPecaRequest.getPeca().getId(), ordemServico.getNumero());
            
            // Calcular a diferença real
            int diferenca = itensPecaRequest.getQuantidade() - quantidadeAnterior;
            
            // Verificar se há estoque suficiente para a alteração
            if (diferenca > 0 && peca.getQuantidade() < diferenca) {
                System.out.println("DEBUG: Estoque insuficiente - Quantidade em Estoque: " + peca.getQuantidade() + ", Diferença Necessária: " + diferenca);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para a peça");
            }
            
            // Atualizar o estoque com base na diferença
            peca.setQuantidade(peca.getQuantidade() - diferenca);
            pecasService.atualizar(peca);
            
            double precoPecaTotal = itensPecaRequest.getQuantidade() * peca.getPrecoUnitario();
            precoTotal += precoPecaTotal;
    
            // Atualizar ou criar Item de Peça
            ItensPeca itensPeca = itensPecaService.buscarPorId(itensPecaRequest.getId());
            System.out.println("DEBUG: Ordem de Serviço - Número: " + ordemServico.getNumero());

            if (itensPeca == null) {
                System.err.println("entrrou no if");
                itensPeca = new ItensPeca(
                        0,
                        precoPecaTotal,
                        itensPecaRequest.getQuantidade(),
                        ordemServico,
                        peca
                );
                
                itensPecaService.salvar(itensPeca);
            } else {
                itensPeca.setQuantidade(itensPecaRequest.getQuantidade());
                itensPeca.setPrecoTotal(precoPecaTotal);
                itensPecaService.atualizar(itensPeca);
            }
        }
    
        // Atualizar Itens de Serviços
        for (ItensServico itensServicoRequest : request.getItensServico()) {
            Servico servico = servicoService.buscarPorId(itensServicoRequest.getIdServico().getId());
            Funcionario funcionario = funcionarioService.buscarPorId(itensServicoRequest.getIdFuncionario().getId());
            if (servico == null || funcionario == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço ou Funcionário não encontrado");
            }
    
            double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
            precoTotal += precoServicoTotal;
    
            // Atualizar ou criar Item de Serviço
            ItensServico itensServico = itensServicoService.buscarPorId(itensServicoRequest.getId());
            if (itensServico == null) {
                itensServico = new ItensServico(
                        0,
                        itensServicoRequest.getHorarioInicio(),
                        itensServicoRequest.getHorarioFim(),
                        itensServicoRequest.getQuantidade(),
                        precoServicoTotal,
                        funcionario,
                        servico,
                        ordemServico
                );
                itensServicoService.salvar(itensServico);
            } else {
                itensServico.setHorarioInicio(itensServicoRequest.getHorarioInicio());
                itensServico.setHorarioFim(itensServicoRequest.getHorarioFim());
                itensServico.setQuantidade(itensServicoRequest.getQuantidade());
                itensServico.setPrecoTotal(precoServicoTotal);
                itensServicoService.atualizar(itensServico);
            }
        }
    
        // Atualizar o preço total da Ordem de Serviço
        ordemServico.setPrecoFinal(precoTotal);
        ordemServicoService.atualizar(ordemServico);
    }
    

    @GetMapping("/{id}")
    public OrdemServico buscarPorId(@PathVariable Integer id) {
        OrdemServico ordemServico = ordemServicoService.buscarPorId(id);
        if (ordemServico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada");
        }
        return ordemServico;
    }

    @GetMapping("/{numeroOs}/itensPeca")
    public List<ItensPeca> buscarItensPecaPorNumeroOs(@PathVariable Integer numeroOs) {
        List<ItensPeca> itensPeca = itensPecaService.buscarPorNumeroOs(numeroOs);
        if (itensPeca.isEmpty()) {
            return new ArrayList<>();
        }
        return itensPeca;
    }

    @GetMapping("/{numeroOs}/itensServico")
    public List<ItensServico> buscarItensServicoPorNumeroOs(@PathVariable Integer numeroOs) {
        List<ItensServico> itensServico = itensServicoService.buscarPorNumeroOs(numeroOs);
        if (itensServico.isEmpty()) {
            return new ArrayList<>();
        }
        return itensServico;
    }

    @GetMapping
    public List<OrdemServico> buscarTodos() {
        return ordemServicoService.buscarTodos();
    }
}