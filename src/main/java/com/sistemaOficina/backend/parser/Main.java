package com.sistemaOficina.backend.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sistemaOficina.backend.parser.estrutura.ConsultaCompleta;

public class Main {
    public static void main(String[] args) {
        try {
            ParserConsultaVendas parser = new ParserConsultaVendas();
            ConsultaCompleta resultado = parser.parse("vendas do \"joao\" - vendas do \"maria\" + vendas de Janeiro");
            
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
            
            String json = gson.toJson(resultado);
            System.out.println(json);
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}