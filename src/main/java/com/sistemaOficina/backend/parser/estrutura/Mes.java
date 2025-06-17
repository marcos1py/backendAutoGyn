package com.sistemaOficina.backend.parser.estrutura;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mes {
    private String nome;
    
    private static final Set<String> MESES_VALIDOS = Set.of(
        "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO",
        "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"
    );
    
    public static boolean isValid(String test) {
        System.out.printf("testing %s in %s\n", test, MESES_VALIDOS.toString());
        return MESES_VALIDOS.contains(test.toUpperCase());
    }

    private static final Map<String, Integer> MESES_MAP = new HashMap<>();
    
    static {
        MESES_MAP.put("JANEIRO", 1);
        MESES_MAP.put("FEVEREIRO", 2);
        MESES_MAP.put("MARÇO", 3);
        MESES_MAP.put("ABRIL", 4);
        MESES_MAP.put("MAIO", 5);
        MESES_MAP.put("JUNHO", 6);
        MESES_MAP.put("JULHO", 7);
        MESES_MAP.put("AGOSTO", 8);
        MESES_MAP.put("SETEMBRO", 9);
        MESES_MAP.put("OUTUBRO", 10);
        MESES_MAP.put("NOVEMBRO", 11);
        MESES_MAP.put("DEZEMBRO", 12);
    }
    
    public Mes(String nome) {
        nome = nome.toUpperCase();
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
        nomeMes = nomeMes.toUpperCase();
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