package com.sistemaOficina.backend.parser;

import org.springframework.stereotype.Component;

import com.sistemaOficina.backend.parser.estrutura.ExpressaoVendas;

// padrao factory + singleton
@Component
public class ConsultaVendasFactory {
    public ExpressaoVendas instantiate(String consulta) {
        ParserConsultaVendas parser = new ParserConsultaVendas();
        return parser.parse(consulta);
    }
}
