package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaServicosMes {
    private Mes mes;
    
    public ConsultaServicosMes(Mes mes) {
        this.mes = mes;
    }
    
    public Mes getMes() { return mes; }
    
    @Override
    public String toString() {
        return "serviços de " + mes.getNome();
    }
}
