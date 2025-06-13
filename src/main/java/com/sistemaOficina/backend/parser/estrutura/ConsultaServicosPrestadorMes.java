package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaServicosPrestadorMes {
    private NomePrestador prestador;
    private Mes mes;
    
    public ConsultaServicosPrestadorMes(NomePrestador prestador, Mes mes) {
        this.prestador = prestador;
        this.mes = mes;
    }
    
    public NomePrestador getPrestador() { return prestador; }
    public Mes getMes() { return mes; }
    
    @Override
    public String toString() {
        return "serviços do " + prestador.getNome() + " em " + mes.getNome();
    }
}