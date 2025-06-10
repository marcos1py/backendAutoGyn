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
    
        // Métodos existentes permanecem inalterados
        public ExpressaoVendas obterExpressaoPrincipal() { return expressaoPrincipal; }
        public String obterTerminador() { return terminador; }
        public OperacaoEExpressao obterContinuacao() { return continuacao; }
        public boolean temContinuacao() { return continuacao != null; }
        
        // NOVOS métodos getter compatíveis com Jackson
        public ExpressaoVendas getExpressaoPrincipal() { return expressaoPrincipal; }
        public String getTerminador() { return terminador; }
        public OperacaoEExpressao getContinuacao() { return continuacao; }
        public boolean isTemContinuacao() { return continuacao != null; }
}
