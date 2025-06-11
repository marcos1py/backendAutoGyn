package com.sistemaOficina.backend.parser.estrutura;

public class NomeVendedor {
    private String nome;
    
    public NomeVendedor(String nome) {
        this.nome = nome.trim();
    }
    
    public String getNome() { return nome; }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NomeVendedor && ((NomeVendedor) obj).nome.equals(this.nome);
    }
    
    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
