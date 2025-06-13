package com.sistemaOficina.backend.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ItensServicoResponse {
    
    private Integer id;
    private long horarioInicio;
    private long horarioFim;
    private int quantidade;
    private double precoTotal;
    
    private Integer funcionarioId;
    private String funcionarioNome;
    private String funcionarioCargo;
    
    private Integer servicoId;
    private String servicoNome;
    private double servicoPrecoUnitario;
    
    private Integer numeroOs;
    private String statusOs;
    private String placaVeiculo;
    private String clienteNome;
    
    public ItensServicoResponse() {}
    
    public ItensServicoResponse(com.sistemaOficina.backend.entidade.ItensServico entity) {
        this.id = entity.getId();
        this.horarioInicio = entity.getHorarioInicio() != null ? 
            entity.getHorarioInicio().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : 0;
        this.horarioFim = entity.getHorarioFim() != null ? 
            entity.getHorarioFim().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : 0;
        this.quantidade = entity.getQuantidade();
        this.precoTotal = entity.getPrecoTotal();
        
        if (entity.getFuncionario() != null) {
            this.funcionarioId = entity.getFuncionario().getId();
            this.funcionarioNome = entity.getFuncionario().getNome();
            this.funcionarioCargo = entity.getFuncionario().getCargo();
        }
        
        if (entity.getIdServico() != null) {
            this.servicoId = entity.getIdServico().getId();
            this.servicoNome = entity.getIdServico().getNome();
            this.servicoPrecoUnitario = entity.getIdServico().getPrecoUnitario();
        }
        
        if (entity.getNumeroOs() != null) {
            this.numeroOs = entity.getNumeroOs().getNumero();
            this.statusOs = entity.getNumeroOs().getStatus();
            this.placaVeiculo = entity.getNumeroOs().getPlacaVeiculo().getPlaca();
            
            if (entity.getNumeroOs().getCliente() != null) {
                this.clienteNome = entity.getNumeroOs().getCliente().getNome();
            }
        }
    }
    
    public ItensServicoResponse(Integer id, long horarioInicio, long horarioFim, 
                                 int quantidade, double precoTotal, Integer funcionarioId, 
                                 String funcionarioNome, String funcionarioCargo, Integer servicoId, 
                                 String servicoNome, double servicoPrecoUnitario, Integer numeroOs, 
                                 String statusOs, String placaVeiculo, String clienteNome) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.funcionarioId = funcionarioId;
        this.funcionarioNome = funcionarioNome;
        this.funcionarioCargo = funcionarioCargo;
        this.servicoId = servicoId;
        this.servicoNome = servicoNome;
        this.servicoPrecoUnitario = servicoPrecoUnitario;
        this.numeroOs = numeroOs;
        this.statusOs = statusOs;
        this.placaVeiculo = placaVeiculo;
        this.clienteNome = clienteNome;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public long getHorarioInicio() {
        return horarioInicio;
    }
    
    public void setHorarioInicio(long horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    
    public long getHorarioFim() {
        return horarioFim;
    }
    
    public void setHorarioFim(long horarioFim) {
        this.horarioFim = horarioFim;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getPrecoTotal() {
        return precoTotal;
    }
    
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    public Integer getFuncionarioId() {
        return funcionarioId;
    }
    
    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
    
    public String getFuncionarioNome() {
        return funcionarioNome;
    }
    
    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }
    
    public String getFuncionarioCargo() {
        return funcionarioCargo;
    }
    
    public void setFuncionarioCargo(String funcionarioCargo) {
        this.funcionarioCargo = funcionarioCargo;
    }
    
    public Integer getServicoId() {
        return servicoId;
    }
    
    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }
    
    public String getServicoNome() {
        return servicoNome;
    }
    
    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }
    
    public double getServicoPrecoUnitario() {
        return servicoPrecoUnitario;
    }
    
    public void setServicoPrecoUnitario(double servicoPrecoUnitario) {
        this.servicoPrecoUnitario = servicoPrecoUnitario;
    }
    
    public Integer getNumeroOs() {
        return numeroOs;
    }
    
    public void setNumeroOs(Integer numeroOs) {
        this.numeroOs = numeroOs;
    }
    
    public String getStatusOs() {
        return statusOs;
    }
    
    public void setStatusOs(String statusOs) {
        this.statusOs = statusOs;
    }
    
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }
    
    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }
    
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    
    public static ItensServicoResponse fromEntity(com.sistemaOficina.backend.entidade.ItensServico entity) {
        return new ItensServicoResponse(entity);
    }
    
    @Override
    public String toString() {
        return "ItensServicoResponse{" +
                "id=" + id +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                ", quantidade=" + quantidade +
                ", precoTotal=" + precoTotal +
                ", funcionarioNome='" + funcionarioNome + '\'' +
                ", servicoNome='" + servicoNome + '\'' +
                ", numeroOs=" + numeroOs +
                ", placaVeiculo='" + placaVeiculo + '\'' +
                '}';
    }
}