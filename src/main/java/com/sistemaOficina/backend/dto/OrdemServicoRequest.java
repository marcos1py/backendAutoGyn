package com.sistemaOficina.backend.dto;

import java.util.List;

import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;

public class OrdemServicoRequest {
    private String placaVeiculo;
    private Integer idCliente;
    private String status;
    private List<ItensPeca> itensPeca;
    private List<ItensServico> itensServico; 

    // Default constructor
    public OrdemServicoRequest() {
    }

    // Parameterized constructor
    public OrdemServicoRequest(String placaVeiculo, Integer idCliente, String status, 
                               List<ItensPeca> itensPeca, List<ItensServico> itensServico) {
        this.placaVeiculo = placaVeiculo;
        this.idCliente = idCliente;
        this.status = status;
        this.itensPeca = itensPeca;
        this.itensServico = itensServico;
    }

    // Getters and setters
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItensPeca> getItensPeca() {
        return itensPeca;
    }

    public void setItensPeca(List<ItensPeca> itensPeca) {
        this.itensPeca = itensPeca;
    }

    public List<ItensServico> getItensServico() {
        return itensServico;
    }

    public void setItensServico(List<ItensServico> itensServico) {
        this.itensServico = itensServico;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    
}