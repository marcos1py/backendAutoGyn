package com.sistemaOficina.backend.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "veiculo")
public class Veiculo {
    
    @Id
    private String placa;
    private int quilometragem;
    private String chassi;
    private String patrimonio;
    @Column(name = "ano_modelo")
    private int anoModelo;
    @Column(name = "ano_fabricacao")
    private int anoFabricacao;
    
    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    public Veiculo() {
    }

    public Veiculo(String placa, int quilometragem, String chassi, String patrimonio, 
                   int anoModelo, int anoFabricacao, Modelo modelo) {
        this.placa = placa;
        this.quilometragem = quilometragem;
        this.chassi = chassi;
        this.patrimonio = patrimonio;
        this.anoModelo = anoModelo;
        this.anoFabricacao = anoFabricacao;
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}
