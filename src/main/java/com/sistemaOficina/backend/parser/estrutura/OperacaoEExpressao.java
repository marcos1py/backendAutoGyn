package com.sistemaOficina.backend.parser.estrutura;

public class OperacaoEExpressao {
    private TipoOperacao operacao;
    private ExpressaoVendas expressao;
    
    public OperacaoEExpressao(TipoOperacao operacao, ExpressaoVendas expressao) {
        this.operacao = operacao;
        this.expressao = expressao;
    }
    
    public TipoOperacao getOperacao() { return operacao; }
    public ExpressaoVendas getExpressao() { return expressao; }
    
    public boolean isSoma() { return operacao == TipoOperacao.SOMA; }
    public boolean isSubtracao() { return operacao == TipoOperacao.SUBTRACAO; }
}