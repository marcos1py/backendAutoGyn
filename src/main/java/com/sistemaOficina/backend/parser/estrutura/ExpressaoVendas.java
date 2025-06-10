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
    
    public boolean ehExpressaoParenteses() { return tipo == TipoExpressao.PARENTESES; }
    public boolean ehVendasPorMes() { return tipo == TipoExpressao.VENDAS_MES; }
    public boolean ehVendasPorVendedorEMes() { return tipo == TipoExpressao.VENDAS_VENDEDOR_MES; }
    public boolean ehVendasPorVendedor() { return tipo == TipoExpressao.VENDAS_VENDEDOR; }
    
    public ExpressaoVendas obterExpressaoInterna() { return expressaoInterna; }
    public ConsultaVendasMes obterVendasMes() { return vendasMes; }
    public ConsultaVendasVendedorMes obterVendasVendedorMes() { return vendasVendedorMes; }
    public ConsultaVendasVendedor obterVendasVendedor() { return vendasVendedor; }
    
    public void adicionarOperacao(OperacaoEExpressao operacao) {
        operacoesAdicionais.add(operacao);
    }
    
    public List<OperacaoEExpressao> obterOperacoesAdicionais() { 
        return new ArrayList<>(operacoesAdicionais); 
    }

}