package com.sistemaOficina.backend.entidade;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String numero1;
    private String numero2;
    private String email;
    private String endereco;
    @Column(unique = true)
    private String cpf; // Para Pessoa Física
    private String cnpj; // Para Pessoa Jurídica
    private String cep;
    @Column(name = "tipo_cliente")
    private String tipoCliente; // Para identificar se é Pessoa Física ou Jurídica

    public Cliente() {
    }

    public Cliente(Integer id, String nome,String numero1,
    		String numero2, String email, String cpf, String cnpj, 
    		String contato, String tipoCliente, String cep, String endereco) {
        this.id = id;
        this.nome = nome;
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.email = email;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.tipoCliente = tipoCliente;
        this.cep = cep;
        this.endereco= endereco; 
        
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


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

    
	

}
