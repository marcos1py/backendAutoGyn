package com.sistemaOficina.backend.parser;

import org.springframework.stereotype.Component;

import com.sistemaOficina.backend.parser.estrutura.ConsultaCompleta;

@Component
public class ConsultaVendasFactory {
    public ConsultaCompleta instantiate(String consulta) {
        ParserConsultaVendas parser = new ParserConsultaVendas();
        return parser.parse(consulta);
    }
}
