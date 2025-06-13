package com.sistemaOficina.backend.parser;

import org.springframework.stereotype.Component;

import com.sistemaOficina.backend.parser.estrutura.ExpressaoServicos;

// padrao factory + singleton
@Component
public class ConsultaServicosFactory {
    public ExpressaoServicos instantiate(String consulta) {
        ParserConsultaServicos parser = new ParserConsultaServicos();
        return parser.parse(consulta);
    }
}