package com.sistemaOficina.backend.parser.estrutura;

public class OperacaoEExpressao {
    private TipoOperacao operacao;
    private ExpressaoVendas expressao;
    
    public OperacaoEExpressao(TipoOperacao operacao, ExpressaoVendas expressao) {
        this.operacao = operacao;
        this.expressao = expressao;
    }
    
    public TipoOperacao obterOperacao() { return operacao; }
    public ExpressaoVendas obterExpressao() { return expressao; }
    
    public boolean ehSoma() { return operacao == TipoOperacao.SOMA; }
    public boolean ehSubtracao() { return operacao == TipoOperacao.SUBTRACAO; }
}