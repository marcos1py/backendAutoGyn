package com.sistemaOficina.backend.entidade;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_servico")
public class ItensServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;
    @Column(name = "horario_fim")
    private LocalTime horarioFim;
    private int quantidade;
    @Column(name = "preco_total")
    private double precoTotal;
    
    @ManyToOne
    @JoinColumn(name = "funcionario")
    private Funcionario funcionario;
    
    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico idServico;
    
    @ManyToOne
    @JoinColumn(name = "numero_os")
    private OrdemServico numeroOs;

    public ItensServico() {
    }

    public ItensServico(Integer id, LocalTime horarioInicio, LocalTime horarioFim, int quantidade, 
                        double precoTotal, Funcionario funcionario, Servico idServico, 
                        OrdemServico numeroOs) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.funcionario = funcionario;
        this.idServico = idServico;
        this.numeroOs = numeroOs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
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

  

    public Servico getIdServico() {
        return idServico;
    }

    public void setIdServico(Servico idServico) {
        this.idServico = idServico;
    }

    public OrdemServico getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(OrdemServico numeroOs) {
        this.numeroOs = numeroOs;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
