package com.sistemaOficina.backend.entidade;

import java.io.Serializable;
import java.util.Objects;

public class AcessorioVeiculoId implements Serializable {
    
    private Integer idAcessorio;
    private String placaVeiculo;

    public AcessorioVeiculoId() {
    }

    public AcessorioVeiculoId(Integer idAcessorio, String placaVeiculo) {
        this.idAcessorio = idAcessorio;
        this.placaVeiculo = placaVeiculo;
    }

    public Integer getIdAcessorio() {
        return idAcessorio;
    }

    public void setIdAcessorio(Integer idAcessorio) {
        this.idAcessorio = idAcessorio;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcessorioVeiculoId that = (AcessorioVeiculoId) o;
        return Objects.equals(idAcessorio, that.idAcessorio) && 
               Objects.equals(placaVeiculo, that.placaVeiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAcessorio, placaVeiculo);
    }
}
