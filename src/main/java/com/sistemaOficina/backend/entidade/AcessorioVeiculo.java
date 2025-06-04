package com.sistemaOficina.backend.entidade;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "acessorio_veiculo")
@IdClass(AcessorioVeiculoId.class)
public class AcessorioVeiculo implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "id_acessorio")
    private Acessorio idAcessorio;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "placa_veiculo")
    private Veiculo placaVeiculo;

    public AcessorioVeiculo() {
    }

    public AcessorioVeiculo(Acessorio idAcessorio, Veiculo placaVeiculo) {
        this.idAcessorio = idAcessorio;
        this.placaVeiculo = placaVeiculo;
    }

    public Acessorio getIdAcessorio() {
        return idAcessorio;
    }

    public void setIdAcessorio(Acessorio idAcessorio) {
        this.idAcessorio = idAcessorio;
    }

    public Veiculo getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(Veiculo placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }
}
