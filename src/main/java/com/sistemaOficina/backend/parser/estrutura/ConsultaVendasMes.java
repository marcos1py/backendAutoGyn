package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaVendasMes {
    private Mes mes;
    
    public ConsultaVendasMes(Mes mes) {
        this.mes = mes;
    }
    
    public Mes obterMes() { return mes; }
    
    @Override
    public String toString() {
        return "vendas de " + mes.obterNome();
    }
}
