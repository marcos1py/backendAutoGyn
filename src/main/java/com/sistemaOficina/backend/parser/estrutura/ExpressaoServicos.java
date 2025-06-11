package com.sistemaOficina.backend.parser.estrutura;

import java.util.ArrayList;
import java.util.List;

public class ExpressaoServicos {
    private TipoExpressao tipo;
    private ExpressaoServicos expressaoInterna;
    private ConsultaServicosMes servicosMes;
    private ConsultaServicosPrestadorMes servicosPrestadorMes;
    private ConsultaServicosPrestador servicosPrestador;
    private List<OperacaoEExpressao> operacoesAdicionais;
    
    public ExpressaoServicos(ExpressaoServicos expressaoInterna) {
        this.tipo = TipoExpressao.PARENTESES;
        this.expressaoInterna = expressaoInterna;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoServicos(ConsultaServicosMes servicosMes) {
        this.tipo = TipoExpressao.SERVICOS_MES;
        this.servicosMes = servicosMes;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoServicos(ConsultaServicosPrestadorMes servicosPrestadorMes) {
        this.tipo = TipoExpressao.SERVICOS_PRESTADOR_MES;
        this.servicosPrestadorMes = servicosPrestadorMes;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public ExpressaoServicos(ConsultaServicosPrestador servicosPrestador) {
        this.tipo = TipoExpressao.SERVICOS_PRESTADOR;
        this.servicosPrestador = servicosPrestador;
        this.operacoesAdicionais = new ArrayList<>();
    }
    
    public boolean isExpressaoParenteses() { return tipo == TipoExpressao.PARENTESES; }
    public boolean isServicosPorMes() { return tipo == TipoExpressao.SERVICOS_MES; }
    public boolean isServicosPorPrestadorEMes() { return tipo == TipoExpressao.SERVICOS_PRESTADOR_MES; }
    public boolean isServicosPorPrestador() { return tipo == TipoExpressao.SERVICOS_PRESTADOR; }
    
    public ExpressaoServicos getExpressaoInterna() { return expressaoInterna; }
    public ConsultaServicosMes getServicosMes() { return servicosMes; }
    public ConsultaServicosPrestadorMes getServicosPrestadorMes() { return servicosPrestadorMes; }
    public ConsultaServicosPrestador getServicosPrestador() { return servicosPrestador; }
    
    public void adicionarOperacao(OperacaoEExpressao operacao) {
        operacoesAdicionais.add(operacao);
    }
    
    public List<OperacaoEExpressao> getOperacoesAdicionais() { 
        return new ArrayList<>(operacoesAdicionais); 
    }
}