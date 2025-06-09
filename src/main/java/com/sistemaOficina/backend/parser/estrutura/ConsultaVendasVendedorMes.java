package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaVendasVendedorMes {
    private NomeVendedor vendedor;
    private Mes mes;
    
    public ConsultaVendasVendedorMes(NomeVendedor vendedor, Mes mes) {
        this.vendedor = vendedor;
        this.mes = mes;
    }
    
    public NomeVendedor obterVendedor() { return vendedor; }
    public Mes obterMes() { return mes; }
    
    @Override
    public String toString() {
        return "vendas do " + vendedor.obterNome() + " em " + mes.obterNome();
    }
}