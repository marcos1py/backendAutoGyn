package com.sistemaOficina.backend.parser.estrutura;

public class OperacaoEExpressao {
    private TipoOperacao operacao;
    private ExpressaoServicos expressao;
    
    public OperacaoEExpressao(TipoOperacao operacao, ExpressaoServicos expressao) {
        this.operacao = operacao;
        this.expressao = expressao;
    }
    
    public TipoOperacao getOperacao() { return operacao; }
    public ExpressaoServicos getExpressao() { return expressao; }
    
    public boolean isSoma() { return operacao == TipoOperacao.SOMA; }
    public boolean isSubtracao() { return operacao == TipoOperacao.SUBTRACAO; }
}