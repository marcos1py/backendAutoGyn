package com.sistemaOficina.backend.parser.estrutura;

import java.util.Set;

public class Mes {
    private String nome;
    private static final Set<String> MESES_VALIDOS = Set.of(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    );
    
    public Mes(String nome) {
        if (!MESES_VALIDOS.contains(nome)) {
            throw new IllegalArgumentException("Mês inválido: " + nome);
        }
        this.nome = nome;
    }
    
    public String getNome() { return nome; }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Mes && ((Mes) obj).nome.equals(this.nome);
    }
    
    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}