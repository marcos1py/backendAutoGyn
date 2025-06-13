package com.sistemaOficina.backend.parser.estrutura;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mes {
    private String nome;
    
    private static final Set<String> MESES_VALIDOS = Set.of(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    );
    
    private static final Map<String, Integer> MESES_MAP = new HashMap<>();
    
    static {
        MESES_MAP.put("Janeiro", 1);
        MESES_MAP.put("Fevereiro", 2);
        MESES_MAP.put("Março", 3);
        MESES_MAP.put("Abril", 4);
        MESES_MAP.put("Maio", 5);
        MESES_MAP.put("Junho", 6);
        MESES_MAP.put("Julho", 7);
        MESES_MAP.put("Agosto", 8);
        MESES_MAP.put("Setembro", 9);
        MESES_MAP.put("Outubro", 10);
        MESES_MAP.put("Novembro", 11);
        MESES_MAP.put("Dezembro", 12);
    }
    
    public Mes(String nome) {
        if (!MESES_VALIDOS.contains(nome)) {
            throw new IllegalArgumentException("Mês inválido: " + nome);
        }
        this.nome = nome;
    }
    
    public String getNome() { 
        return nome; 
    }
    
    public int getNumero() {
        return MESES_MAP.get(nome);
    }
    
    public static int converterParaNumero(String nomeMes) {
        if (!MESES_VALIDOS.contains(nomeMes)) {
            throw new IllegalArgumentException("Mês inválido: " + nomeMes);
        }
        return MESES_MAP.get(nomeMes);
    }
    
    public static String converterParaNome(int numeroMes) {
        if (numeroMes < 1 || numeroMes > 12) {
            throw new IllegalArgumentException("Número do mês deve estar entre 1 e 12");
        }
        return MESES_MAP.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(numeroMes))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Mes && ((Mes) obj).nome.equals(this.nome);
    }
    
    @Override
    public int hashCode() {
        return nome.hashCode();
    }
    
    @Override
    public String toString() {
        return nome + " (" + getNumero() + ")";
    }
}