package com.sistemaOficina.backend.entidade;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private Integer ddi1;
    private Integer ddd1;
    private Integer numero1;
    private Integer ddi2;
    private Integer ddd2;
    private Integer numero2;
    private String email;
    @Column(unique = true)
    private String cpf; // Para Pessoa Física
    private String cnpj; // Para Pessoa Jurídica
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual; // Para Pessoa Jurídica
    private String contato; // Para Pessoa Jurídica
    @Column(name = "tipo_cliente")
    private String tipoCliente; // Para identificar se é Pessoa Física ou Jurídica

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String logradouro, String numero, String complemento, 
                   Integer ddi1, Integer ddd1, Integer numero1, Integer ddi2, Integer ddd2, 
                   Integer numero2, String email, String cpf, String cnpj, String inscricaoEstadual, 
                   String contato, String tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.ddi1 = ddi1;
        this.ddd1 = ddd1;
        this.numero1 = numero1;
        this.ddi2 = ddi2;
        this.ddd2 = ddd2;
        this.numero2 = numero2;
        this.email = email;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.contato = contato;
        this.tipoCliente = tipoCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getDdi1() {
        return ddi1;
    }

    public void setDdi1(Integer ddi1) {
        this.ddi1 = ddi1;
    }

    public Integer getDdd1() {
        return ddd1;
    }

    public void setDdd1(Integer ddd1) {
        this.ddd1 = ddd1;
    }

    public Integer getNumero1() {
        return numero1;
    }

    public void setNumero1(Integer numero1) {
        this.numero1 = numero1;
    }

    public Integer getDdi2() {
        return ddi2;
    }

    public void setDdi2(Integer ddi2) {
        this.ddi2 = ddi2;
    }

    public Integer getDdd2() {
        return ddd2;
    }

    public void setDdd2(Integer ddd2) {
        this.ddd2 = ddd2;
    }

    public Integer getNumero2() {
        return numero2;
    }

    public void setNumero2(Integer numero2) {
        this.numero2 = numero2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
