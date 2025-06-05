package com.sistemaOficina.backend.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_peca")
public class ItensPeca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "preco_total")
    private double precoTotal;
    private int quantidade;
    
    @ManyToOne
    @JoinColumn(name = "numero_os")
    private OrdemServico numeroOs;
    
    @ManyToOne
    @JoinColumn(name = "peca")
    private Pecas peca;

    public ItensPeca() {
    }

    public ItensPeca(Integer id, double precoTotal, int quantidade, OrdemServico numeroOs, Pecas peca) {
        this.id = id;
        this.precoTotal = precoTotal;
        this.quantidade = quantidade;
        this.numeroOs = numeroOs;
        this.peca = peca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public OrdemServico getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(OrdemServico numeroOs) {
        this.numeroOs = numeroOs;
    }

    public Pecas getPeca() {
        return peca;
    }

    public void setPeca(Pecas peca) {
        this.peca = peca;
    }


}
