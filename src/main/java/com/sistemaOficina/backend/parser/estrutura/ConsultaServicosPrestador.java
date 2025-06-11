package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaServicosPrestador {
    private NomePrestador prestador;
    
    public ConsultaServicosPrestador(NomePrestador prestador) {
        this.prestador = prestador;
    }
    
    public NomePrestador getPrestador() { return prestador; }
    
    @Override
    public String toString() {
        return "serviços do " + prestador.getNome();
    }
}
