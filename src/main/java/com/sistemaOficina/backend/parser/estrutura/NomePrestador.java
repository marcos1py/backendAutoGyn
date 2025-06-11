package com.sistemaOficina.backend.parser.estrutura;

public class NomePrestador {
    private String nome;
    
    public NomePrestador(String nome) {
        this.nome = nome.trim();
    }
    
    public String getNome() { return nome; }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NomePrestador && ((NomePrestador) obj).nome.equals(this.nome);
    }
    
    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}