package com.sistemaOficina.backend.parser.estrutura;

import java.util.ArrayList;
import java.util.List;

public class ExpressaoVendas {
    private TipoExpressao tipo;
    private ExpressaoVendas expressaoInterna;
    private ConsultaVendasMes vendasMes;
    private ConsultaVendasVendedorMes vendasVendedorMes;
    private ConsultaVendasVendedor vendasVendedor;
    private List<OperacaoEExpressao> operacoesAdicionais;
    
    public ExpressaoVendas(ExpressaoVendas expressaoInterna) {
        this.tipo = TipoExpressao.PARENTESES;
        this.expressaoInterna = expressaoInterna;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoVendas(ConsultaVendasMes vendasMes) {
        this.tipo = TipoExpressao.VENDAS_MES;
        this.vendasMes = vendasMes;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoVendas(ConsultaVendasVendedorMes vendasVendedorMes) {
        this.tipo = TipoExpressao.VENDAS_VENDEDOR_MES;
        this.vendasVendedorMes = vendasVendedorMes;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoVendas(ConsultaVendasVendedor vendasVendedor) {
        this.tipo = TipoExpressao.VENDAS_VENDEDOR;
        this.vendasVendedor = vendasVendedor;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public boolean isExpressaoParenteses() { return tipo == TipoExpressao.PARENTESES; }
    public boolean isVendasPorMes() { return tipo == TipoExpressao.VENDAS_MES; }
    public boolean isVendasPorVendedorEMes() { return tipo == TipoExpressao.VENDAS_VENDEDOR_MES; }
    public boolean isVendasPorVendedor() { return tipo == TipoExpressao.VENDAS_VENDEDOR; }
    
    public ExpressaoVendas getExpressaoInterna() { return expressaoInterna; }
    public ConsultaVendasMes getVendasMes() { return vendasMes; }
    public ConsultaVendasVendedorMes getVendasVendedorMes() { return vendasVendedorMes; }
    public ConsultaVendasVendedor getVendasVendedor() { return vendasVendedor; }
    
    public void adicionarOperacao(OperacaoEExpressao operacao) {
        operacoesAdicionais.add(operacao);
    }
    
    public List<OperacaoEExpressao> getOperacoesAdicionais() { 
        return new ArrayList<>(operacoesAdicionais); 
    }

}