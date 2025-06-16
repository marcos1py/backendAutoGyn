package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.dto.ErrorResponse;
import com.sistemaOficina.backend.dto.ItensPecaDTO;
import com.sistemaOficina.backend.dto.ItensServicoDTO;
import com.sistemaOficina.backend.dto.OrdemServicoRequest;
import com.sistemaOficina.backend.entidade.*;
import com.sistemaOficina.backend.event.OrdemServicoCreatedEvent;
import com.sistemaOficina.backend.service.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
    private final PdfService pdfService;
    private final ApplicationEventPublisher eventPublisher;

    public OrdemServicoController(OrdemServicoService ordemServicoService,
                                  ItensPecaService itensPecaService,
                                  ClienteService clienteService,
                                  ItensServicoService itensServicoService,
                                  PecasService pecasService,
                                  ServicoService servicoService,
                                  FuncionarioService funcionarioService,
                                  VeiculoService veiculoService,
                                  PdfService pdfService,
                                  ApplicationEventPublisher eventPublisher) {
        this.ordemServicoService = ordemServicoService;
        this.itensPecaService = itensPecaService;
        this.clienteService = clienteService;
        this.itensServicoService = itensServicoService;
        this.pecasService = pecasService;
        this.servicoService = servicoService;
        this.funcionarioService = funcionarioService;
        this.veiculoService = veiculoService;
        this.pdfService = pdfService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/{numero}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Integer numero) {
        OrdemServico os = ordemServicoService.buscarPorId(numero);
        Cliente cliente = clienteService.buscarPorId(os.getCliente().getId());
        Veiculo veiculo = veiculoService.buscarPorPlaca(os.getPlacaVeiculo().getPlaca());
        List<ItensPeca> pecas = itensPecaService.buscarPorNumeroOs(numero);
        List<ItensServico> servicos = itensServicoService.buscarPorNumeroOs(numero);
        //System.print com for das pecas e serviços
        for (ItensPeca peca : pecas) {
            System.out.println("Peça: " + peca.getPeca().getNome() + ", Quantidade: " + peca.getQuantidade());
        }

        for (ItensServico servico : servicos) {
            System.out.println("Serviço: " + servico.getIdServico().getNome() + ", Quantidade: " + servico.getQuantidade());
        }
        try {
            byte[] pdf = pdfService.gerarPdfOrdemServico(os, cliente, veiculo, pecas, servicos);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"OS_" + numero + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> salvar(@RequestBody OrdemServicoRequest request) {
        try {
            // 1) Validar veículo e cliente
            Veiculo veiculo = veiculoService.buscarPorPlaca(request.getPlacaVeiculo());
            if (veiculo == null) {
                return ResponseEntity.status(404).body(new ErrorResponse("Veículo não encontrado"));
            }
            Cliente cliente = clienteService.buscarPorId(request.getIdCliente().intValue());
            if (cliente == null) {
                return ResponseEntity.status(404).body(new ErrorResponse("Cliente não encontrado"));
            }

            // 2) Criar e salvar Ordem de Serviço para gerar o número
            OrdemServico os = new OrdemServico();
            os.setData(LocalDate.now());
            os.setPrecoFinal(0.0);
            os.setStatus(request.getStatus());
            os.setPlacaVeiculo(veiculo);
            os.setCliente(cliente);
            ordemServicoService.salvar(os);
            Integer numero = os.getNumero();

            // números e datas já definidos acima

            // 3) Calcular preço total com segurança
            double precoTotal = 0.0;

            // Itens de peça
            for (ItensPecaDTO item : request.getItensPeca()) {
                Pecas peca = pecasService.buscarPorId(item.getPecaId().intValue());
                if (peca == null) {
                    return ResponseEntity.status(404).body(new ErrorResponse("Peça não encontrada"));
                }

                if (peca.getQuantidade() < item.getQuantidade()) {
                    return ResponseEntity.status(400).body(new ErrorResponse("Estoque insuficiente para a peça"));
                }

                peca.setQuantidade(peca.getQuantidade() - item.getQuantidade());
                pecasService.atualizar(peca);

                double preco = item.getQuantidade() * peca.getPrecoUnitario();
                precoTotal += preco;

                ItensPeca ip = new ItensPeca(0, preco, item.getQuantidade(), os, peca);
                itensPecaService.salvar(ip);
            }

            // Itens de serviço
            for (ItensServicoDTO item : request.getItensServico()) {
                Servico servico = servicoService.buscarPorId(item.getServicoId().intValue());
                Funcionario funcionario = funcionarioService.buscarPorId(item.getFuncionarioId().intValue());

                if (servico == null || funcionario == null) {
                    return ResponseEntity.status(404).body(new ErrorResponse("Serviço ou Funcionário não encontrado"));
                }

                double preco = item.getQuantidade() * servico.getPrecoUnitario();
                precoTotal += preco;

                LocalDateTime inicio = null;
                LocalDateTime fim = null;

                try {
                    if (item.getHorarioInicio() != null && !item.getHorarioInicio().isBlank()) {
                        inicio = LocalDateTime.parse(item.getHorarioInicio());
                    }
                    if (item.getHorarioFim() != null && !item.getHorarioFim().isBlank()) {
                        fim = LocalDateTime.parse(item.getHorarioFim());
                    }
                } catch (DateTimeParseException e) {
                    return ResponseEntity.status(400)
                            .body(new ErrorResponse("Formato de data/hora inválido em horário de serviço."));
                }

                ItensServico is = new ItensServico(0, inicio, fim, item.getQuantidade(), preco, funcionario, servico, os);
                itensServicoService.salvar(is);
            }

            // 5) Atualizar o total e persistir
            os.setPrecoFinal(precoTotal);
            ordemServicoService.atualizar(os);

            // 6) Notificar
            eventPublisher.publishEvent(new OrdemServicoCreatedEvent(this, os, request.getItensServico()));

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // Marca a transação explicitamente como rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(400)
                    .body(new ErrorResponse("Erro ao criar ordem de serviço: " + e.getMessage()));
        }
    }

    @PutMapping("/{numero}")
    @Transactional
    public ResponseEntity<?> salvarAlteracao(@PathVariable Integer numero, @RequestBody OrdemServicoRequest request) {
        try {
            double precoTotal = 0;

            OrdemServico ordemServico = ordemServicoService.buscarPorId(numero);
            if (ordemServico == null) {
                return ResponseEntity.status(404).body(new ErrorResponse("Ordem de serviço não encontrada"));
            }

            Veiculo veiculo = veiculoService.buscarPorPlaca(request.getPlacaVeiculo());
            if (veiculo == null) {
                return ResponseEntity.status(404).body(new ErrorResponse("Veículo não encontrado"));
            }

            Cliente cliente = clienteService.buscarPorId(request.getIdCliente().intValue());
            if (cliente == null) {
                return ResponseEntity.status(404).body(new ErrorResponse("Cliente não encontrado"));
            }

            ordemServico.setCliente(cliente);
            ordemServico.setStatus(request.getStatus());
            ordemServico.setPlacaVeiculo(veiculo);

            // Atualizar Itens de Peças
            List<ItensPeca> existingPecas = itensPecaService.buscarPorNumeroOs(numero);
            for (ItensPecaDTO itensPecaRequest : request.getItensPeca()) {
                Integer pecaId = itensPecaRequest.getPecaId().intValue();
                Pecas peca = pecasService.buscarPorId(pecaId);
                if (peca == null) {
                    return ResponseEntity.status(404).body(new ErrorResponse("Peça não encontrada"));
                }

                ItensPeca existingItem = existingPecas.stream()
                        .filter(ip -> ip.getPeca().getId().equals(pecaId))
                        .findFirst().orElse(null);
                int quantidadeAnterior = existingItem != null ? existingItem.getQuantidade() : 0;
                int diferenca = itensPecaRequest.getQuantidade() - quantidadeAnterior;

                if (diferenca > 0 && peca.getQuantidade() < diferenca) {
                    return ResponseEntity.status(400).body(new ErrorResponse("Estoque insuficiente para a peça"));
                }

                if (diferenca > 0) {
                    peca.setQuantidade(peca.getQuantidade() - diferenca);
                } else if (diferenca < 0) {
                    peca.setQuantidade(peca.getQuantidade() + Math.abs(diferenca));
                }
                if (diferenca != 0) {
                    pecasService.atualizar(peca);
                }

                double precoPecaTotal = itensPecaRequest.getQuantidade() * peca.getPrecoUnitario();
                precoTotal += precoPecaTotal;

                ItensPeca itensPeca = existingItem;
                if (itensPeca == null) {
                    itensPeca = new ItensPeca(0, precoPecaTotal, itensPecaRequest.getQuantidade(), ordemServico, peca);
                    itensPecaService.salvar(itensPeca);
                } else {
                    itensPeca.setQuantidade(itensPecaRequest.getQuantidade());
                    itensPeca.setPrecoTotal(precoPecaTotal);
                    itensPecaService.atualizar(itensPeca);
                }
            }

            // Atualizar Itens de Serviços
            List<ItensServico> existingServicos = itensServicoService.buscarPorNumeroOs(numero);
            for (ItensServicoDTO itensServicoRequest : request.getItensServico()) {
                LocalDateTime inicio = null;
                LocalDateTime fim = null;

                try {
                    if (itensServicoRequest.getHorarioInicio() != null && !itensServicoRequest.getHorarioInicio().isBlank()) {
                        inicio = LocalDateTime.parse(itensServicoRequest.getHorarioInicio());
                    }
                    if (itensServicoRequest.getHorarioFim() != null && !itensServicoRequest.getHorarioFim().isBlank()) {
                        fim = LocalDateTime.parse(itensServicoRequest.getHorarioFim());
                    }
                } catch (DateTimeParseException e) {
                    return ResponseEntity.status(400).body(new ErrorResponse("Formato inválido de horário."));
                }

                Integer servicoId = itensServicoRequest.getServicoId().intValue();
                Integer funcId = itensServicoRequest.getFuncionarioId().intValue();
                Servico servico = servicoService.buscarPorId(servicoId);
                Funcionario funcionario = funcionarioService.buscarPorId(funcId);

                if (servico == null || funcionario == null) {
                    return ResponseEntity.status(404).body(new ErrorResponse("Serviço ou Funcionário não encontrado"));
                }

                double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
                precoTotal += precoServicoTotal;

                ItensServico itensServico = existingServicos.stream()
                        .filter(is -> is.getIdServico().getId().equals(servicoId) && is.getFuncionario().getId().equals(funcId))
                        .findFirst().orElse(null);

                if (itensServico == null) {
                    itensServico = new ItensServico(0, inicio, fim, itensServicoRequest.getQuantidade(), precoServicoTotal,
                            funcionario, servico, ordemServico);
                    itensServicoService.salvar(itensServico);
                } else {
                    itensServico.setHorarioInicio(inicio);
                    itensServico.setHorarioFim(fim);
                    itensServico.setQuantidade(itensServicoRequest.getQuantidade());
                    itensServico.setPrecoTotal(precoServicoTotal);
                    itensServicoService.atualizar(itensServico);
                }
            }

            ordemServico.setPrecoFinal(precoTotal);
            ordemServicoService.atualizar(ordemServico);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ErrorResponse("Erro ao atualizar ordem de serviço: " + e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        OrdemServico os = ordemServicoService.buscarPorId(id);
        if (os == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Ordem de serviço não encontrada"));
        }
        return ResponseEntity.ok(os);
    }

    @GetMapping("/{numeroOs}/itensPeca")
    public ResponseEntity<List<ItensPeca>> buscarItensPecaPorNumeroOs(@PathVariable Integer numeroOs) {
        List<ItensPeca> lista = itensPecaService.buscarPorNumeroOs(numeroOs);
        return ResponseEntity.ok(lista.isEmpty() ? new ArrayList<>() : lista);
    }

    @GetMapping("/{numeroOs}/itensServico")
    public ResponseEntity<List<ItensServico>> buscarItensServicoPorNumeroOs(@PathVariable Integer numeroOs) {
        List<ItensServico> lista = itensServicoService.buscarPorNumeroOs(numeroOs);
        return ResponseEntity.ok(lista.isEmpty() ? new ArrayList<>() : lista);
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> buscarTodos() {
        return ResponseEntity.ok(ordemServicoService.buscarTodos());
    }

    private double processarItensPeca(List<ItensPecaDTO> itens, OrdemServico os) {
        double soma = 0;
        for (ItensPecaDTO dto : itens) {
            Pecas p = pecasService.buscarPorId(dto.getPecaId().intValue());
            int qtd = dto.getQuantidade();
            if (p == null) throw new RuntimeException("Peça não encontrada: " + dto.getPecaId());
            if (qtd > p.getQuantidade()) throw new RuntimeException("Estoque insuficiente para peça " + dto.getPecaId());

            double total = qtd * p.getPrecoUnitario();
            soma += total;

            p.setQuantidade(p.getQuantidade() - qtd);
            pecasService.atualizar(p);

            itensPecaService.salvar(new ItensPeca(null, total, qtd, os, p));
        }
        return soma;
    }

    private double processarItensServico(List<ItensServicoDTO> itens, OrdemServico os) {
        double soma = 0;
        for (ItensServicoDTO dto : itens) {
            LocalDateTime inicio = LocalDateTime.parse(dto.getHorarioInicio());
            LocalDateTime fim    = LocalDateTime.parse(dto.getHorarioFim());
            Funcionario f    = funcionarioService.buscarPorId(dto.getFuncionarioId().intValue());
            Servico s        = servicoService.buscarPorId(dto.getServicoId().intValue());
            int qtd          = dto.getQuantidade();

            if (f == null) throw new RuntimeException("Funcionário não encontrado: " + dto.getFuncionarioId());
            if (s == null) throw new RuntimeException("Serviço não encontrado: " + dto.getServicoId());

            double total = qtd * s.getPrecoUnitario();
            soma += total;

            itensServicoService.salvar(new ItensServico(null, inicio, fim, qtd, total, f, s, os));
        }
        return soma;
    }
}
