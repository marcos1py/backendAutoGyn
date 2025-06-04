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
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Integer numero;

    private LocalDate data;

    @Column(name = "preco_final")
    private double precoFinal;

    private String status;

    @ManyToOne
    @JoinColumn(name = "placa_veiculo", referencedColumnName = "placa")
    private Veiculo placaVeiculo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public OrdemServico() {
    }

    public OrdemServico(Integer numero, LocalDate data, double precoFinal, String status, 
                        Veiculo placaVeiculo, Cliente cliente) {
        this.numero = numero;
        this.data = data;
        this.precoFinal = precoFinal;
        this.status = status;
        this.placaVeiculo = placaVeiculo;
        this.cliente = cliente;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Veiculo getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(Veiculo placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
