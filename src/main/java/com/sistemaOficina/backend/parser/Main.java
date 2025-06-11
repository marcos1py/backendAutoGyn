package com.sistemaOficina.backend.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sistemaOficina.backend.parser.estrutura.ExpressaoServicos;

public class Main {
    public static void main(String[] args) {
        try {
            ParserConsultaServicos parser = new ParserConsultaServicos();
            ExpressaoServicos resultado = parser.parse("(serviços do \"joao\" - serviços do \"maria\" + serviços de Janeiro - (serviços do \"maria\" + serviços de Janeiro))+ serviços do \"maria\"");
            
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