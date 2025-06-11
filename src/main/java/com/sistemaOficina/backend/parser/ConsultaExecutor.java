package com.sistemaOficina.backend.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sistemaOficina.backend.parser.estrutura.ConsultaVendasMes;
import com.sistemaOficina.backend.parser.estrutura.ConsultaVendasVendedor;
import com.sistemaOficina.backend.parser.estrutura.ConsultaVendasVendedorMes;
import com.sistemaOficina.backend.parser.estrutura.Expressao;
import com.sistemaOficina.backend.parser.estrutura.ExpressaoVendas;
import com.sistemaOficina.backend.parser.estrutura.OperacaoEExpressao;


// padrao template method
@Component
public class ConsultaExecutor {
    public static interface TemplateExecucaoConsulta<T> {
        List<T> execute(ConsultaVendasMes exp);
        List<T> execute(ConsultaVendasVendedor exp);
        List<T> execute(ConsultaVendasVendedorMes exp);
        List<T> add(List<T> exp0, List<T> exp1);
        List<T> remove(List<T> exp0, List<T> exp1);
    }

    public <T> List<T> execute(ExpressaoVendas expressao, TemplateExecucaoConsulta<T> template) {
        if (expressao == null) {
            throw new IllegalArgumentException("Expressão não pode ser nula");
        }
        
        if (template == null) {
            throw new IllegalArgumentException("Template de execução não pode ser nulo");
        }
        
        List<T> resultado;
        if (!expressao.isExpressaoParenteses()) {
            resultado = search(expressao, template);
        } else {
            resultado = execute(expressao.getExpressaoInterna(), template);
        }
        
        if (resultado == null) {
            resultado = new ArrayList<>();
        }
        
        List<OperacaoEExpressao> operacoes = expressao.getOperacoesAdicionais();
        if (operacoes == null) {
            return resultado;
        }

        for (OperacaoEExpressao operacao : operacoes) {
            if (operacao == null) {
                continue;
            }
            
            List<T> proximoResultado = execute(operacao.getExpressao(), template);
            
            if (proximoResultado != null) {
                if (operacao.isSoma()) {
                    resultado = template.add(resultado, proximoResultado);
                } else if (operacao.isSubtracao()) {
                    resultado = template.remove(resultado, proximoResultado);
                }
            }
        }
        
        return resultado;
    }

    private <T> List<T> search(ExpressaoVendas consulta, TemplateExecucaoConsulta template) {
        if (consulta.isVendasPorMes()) {
            return template.execute(consulta.getVendasMes());
        }
        if (consulta.isVendasPorVendedor()) {
            return template.execute(consulta.getVendasVendedor());
        }
        if (consulta.isVendasPorVendedorEMes()) {
            return template.execute(consulta.getVendasVendedorMes());
        }
        return null;
    }
    
}
