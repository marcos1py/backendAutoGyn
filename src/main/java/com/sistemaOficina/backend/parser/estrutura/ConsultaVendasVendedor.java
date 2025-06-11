package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaVendasVendedor {
    private NomeVendedor vendedor;
    
    public ConsultaVendasVendedor(NomeVendedor vendedor) {
        this.vendedor = vendedor;
    }
    
    public NomeVendedor getVendedor() { return vendedor; }
    
    @Override
    public String toString() {
        return "vendas do " + vendedor.getNome();
    }
}