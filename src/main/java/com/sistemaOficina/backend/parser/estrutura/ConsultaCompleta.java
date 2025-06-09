package com.sistemaOficina.backend.parser.estrutura;

public class ConsultaCompleta {
    private ExpressaoVendas expressaoPrincipal;
    private String terminador;
    private OperacaoEExpressao continuacao;
    
    public ConsultaCompleta(ExpressaoVendas expressao) {
        this.expressaoPrincipal = expressao;
        this.terminador = "&";
    }
    
    public ConsultaCompleta(ExpressaoVendas expressao, OperacaoEExpressao continuacao) {
        this.expressaoPrincipal = expressao;
        this.continuacao = continuacao;
    }
    
    public ExpressaoVendas obterExpressaoPrincipal() { return expressaoPrincipal; }
    public String obterTerminador() { return terminador; }
    public OperacaoEExpressao obterContinuacao() { return continuacao; }
    public boolean temContinuacao() { return continuacao != null; }
}
