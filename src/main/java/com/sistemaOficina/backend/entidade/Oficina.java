package com.sistemaOficina.backend.entidade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "oficina")
public class Oficina {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String logradouro;
    private String complemento;
    private String numero;
    private String ddi1;
    private String ddd1;
    private String numero1;
    private String ddi2;
    private String ddd2;
    private String numero2;

    public Oficina() {
    }

    public Oficina(Integer id, String nome, String email, String logradouro, String complemento, 
                   String numero, String ddi1, String ddd1, String numero1, String ddi2, String ddd2, String numero2) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numero = numero;
        this.ddi1 = ddi1;
        this.ddd1 = ddd1;
        this.numero1 = numero1;
        this.ddi2 = ddi2;
        this.ddd2 = ddd2;
        this.numero2 = numero2;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDdi1() {
        return ddi1;
    }

    public void setDdi1(String ddi1) {
        this.ddi1 = ddi1;
    }

    public String getDdd1() {
        return ddd1;
    }

    public void setDdd1(String ddd1) {
        this.ddd1 = ddd1;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getDdi2() {
        return ddi2;
    }

    public void setDdi2(String ddi2) {
        this.ddi2 = ddi2;
    }

    public String getDdd2() {
        return ddd2;
    }

    public void setDdd2(String ddd2) {
        this.ddd2 = ddd2;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }
}
