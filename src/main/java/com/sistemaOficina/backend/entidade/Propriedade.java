package com.sistemaOficina.backend.entidade;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "propriedade")
public class Propriedade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim")
    private LocalDate dataFim;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;
    
    @ManyToOne
    @JoinColumn(name = "placa_veiculo")
    private Veiculo placaVeiculo;

    public Propriedade() {
    }

    public Propriedade(Integer id, LocalDate dataInicio, LocalDate dataFim, Cliente idCliente, Veiculo placaVeiculo) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idCliente = idCliente;
        this.placaVeiculo = placaVeiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Veiculo getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(Veiculo placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }
}
