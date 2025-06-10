package com.sistemaOficina.backend.dto;

import java.util.List;

public class OrdemServicoRequest {
    private String status;
    private String placaVeiculo;
    private Long idCliente;
    private List<ItensPecaDTO> itensPeca;
    private List<ItensServicoDTO> itensServico;
public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItensPecaDTO> getItensPeca() {
        return itensPeca;
    }

    public void setItensPeca(List<ItensPecaDTO> itensPeca) {
        this.itensPeca = itensPeca;
    }

    public List<ItensServicoDTO> getItensServico() {
        return itensServico;
    }

    public void setItensServico(List<ItensServicoDTO> itensServico) {
        this.itensServico = itensServico;
    }
    }