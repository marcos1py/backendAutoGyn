package com.sistemaOficina.backend.parser;

import java.util.*;
import com.sistemaOficina.backend.parser.estrutura.*;

public class ParserConsultaServicos {
    
    // Tipos de tokens que nosso parser reconhece
    private enum TipoToken {
        SERVICOS, DE, DO, EM, OPERADOR, MES, NOME, ABRE_PAREN, FECHA_PAREN, FIM
    }
    
    // Classe interna para representar cada token encontrado
    private static class Token {
        final TipoToken tipo;
        // Só usado para nomes, meses e operadores
        String valor;  
        
        Token(TipoToken tipo, String valor) {
            this.tipo = tipo;
            this.valor = valor;
        }
        
        // metodos de verificação para deixar o código mais legível
        boolean isFim() { return tipo == TipoToken.FIM; }
        boolean isServicos() { return tipo == TipoToken.SERVICOS; }
        boolean isDe() { return tipo == TipoToken.DE; }
        boolean isDo() { return tipo == TipoToken.DO; }
        boolean isEm() { return tipo == TipoToken.EM; }
        boolean isOperador() { return tipo == TipoToken.OPERADOR; }
        boolean isMes() { return tipo == TipoToken.MES; }
        boolean isNome() { return tipo == TipoToken.NOME; }
        boolean isAbreParenteses() { return tipo == TipoToken.ABRE_PAREN; }
        boolean isFechaParenteses() { return tipo == TipoToken.FECHA_PAREN; }

        @Override
        public String toString() {
            return "Token [tipo=" + tipo + ", valor=" + valor + "]";
        }

    }

    private final List<Token> tokens = new ArrayList<>();
    private int posicaoAtual = 0;

    public ExpressaoServicos parse(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) {
            throw new IllegalArgumentException("Consulta não pode ser vazia!");
        }
        
        tokenizar(consulta);
        return parseConsultaCompleta();
    }
   
    private void tokenizar(String consulta) {
        int i = 0;
        final int tamanho = consulta.length();
        
        while (i < tamanho) {
            char c = consulta.charAt(i);
            
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            
            if (c == '(') {
                tokens.add(new Token(TipoToken.ABRE_PAREN, null));
                i++;
                continue;
            }
            if (c == ')') {
                tokens.add(new Token(TipoToken.FECHA_PAREN, null));
                i++;
                continue;
            }
            if (c == '+' || c == '-') {
                tokens.add(new Token(TipoToken.OPERADOR, String.valueOf(c)));
                i++;
                continue;
            }
            if (c == '&') {
                tokens.add(new Token(TipoToken.FIM, "&"));
                i++;
                continue;
            }
            if (c == '"') {
                i = processarTextoEntreAspas(i, consulta);
                continue;
            }
            
            if (Character.isLetter(c)) {
                i = processarPalavra(i, consulta);
                continue;
            }
            
            throw new IllegalArgumentException(
                "Caractere inválido na posição " + i + ": '" + c + "'"
            );
        }
        
        // Garante que sempre termine com FIM
        if (tokens.isEmpty() || !tokens.get(tokens.size()-1).isFim()) {
            tokens.add(new Token(TipoToken.FIM, "&"));
        }
    }
    
    private int processarTextoEntreAspas(int inicio, String consulta) {
        StringBuilder sb = new StringBuilder();
        int i = inicio + 1;
        
        while (i < consulta.length()) {
            char c = consulta.charAt(i);
            if (c == '"') {
                tokens.add(new Token(TipoToken.NOME, sb.toString()));
                return i + 1;
            }
            sb.append(c);
            i++;
        }
        
        // Se chegou aqui, aspa não foi fechada
        throw new IllegalArgumentException("Texto entre aspas não foi fechado!");
    }
    
    private int processarPalavra(int inicio, String consulta) {
        StringBuilder sb = new StringBuilder();
        int i = inicio;
        
        while (i < consulta.length()) {
            char c = consulta.charAt(i);
            if (!Character.isLetter(c)) break;
            sb.append(c);
            i++;
        }
        
        String palavra = sb.toString();
        tokens.add(classificarPalavra(palavra));
        return i;
    }
    
    private Token classificarPalavra(String palavra) {
        String lower = palavra.toLowerCase();
        
        switch (lower) {
            case "servicos":
            case "serviços":
                return new Token(TipoToken.SERVICOS, null);
            case "de":
                return new Token(TipoToken.DE, null);
            case "do":
            case "da":
                return new Token(TipoToken.DO, null);
            case "em":
                return new Token(TipoToken.EM, null);
            default:
                if (Mes.isValid(palavra.toUpperCase())) {
                    return new Token(TipoToken.MES, palavra.toUpperCase());
                }
                return new Token(TipoToken.NOME, palavra);
        }
    }

    private ExpressaoServicos parseConsultaCompleta() {
        ExpressaoServicos expressao = parseExpressao();
        
        if (!tokenAtual().isFim()) {
            throw new IllegalArgumentException(
                "Expressao deveria acabar com fim &, mas acabou com: " + 
                tokenAtual().valor
            );
        }
        
        return expressao;
    }
    
    private ExpressaoServicos parseExpressao() {
        ExpressaoServicos termo = parseTermo();
        
        while (temOperador()) {
            TipoOperacao operacao = tokenAtual().valor.equals("+") 
                ? TipoOperacao.SOMA 
                : TipoOperacao.SUBTRACAO;
            
            consumirToken();
            
            ExpressaoServicos proximoTermo = parseTermo();
            
            OperacaoEExpressao operacaoCombinada = new OperacaoEExpressao(operacao, proximoTermo);
            termo.adicionarOperacao(operacaoCombinada);
        }
        
        return termo;
    }
    
    private ExpressaoServicos parseTermo() {
        if (tokenAtual().isAbreParenteses()) {
            consumirToken();
            ExpressaoServicos interno = parseExpressao();
            
            if (!tokenAtual().isFechaParenteses()) {
                throw new IllegalArgumentException(
                    "nao achou ) achou: " + 
                    tokenAtual().valor
                );
            }
            
            consumirToken();
            return new ExpressaoServicos(interno);
        }
        
        return parseConsultaBasica();
    }
    
    private ExpressaoServicos parseConsultaBasica() {
        if (!tokenAtual().isServicos()) {
            throw new IllegalArgumentException(
                "deve começar com serviços 'serviços'! começou com: " + 
                tokenAtual().valor
            );
        }
        
        consumirToken();
        
        if (tokenAtual().isDe()) {
            return parseServicosDeMes();
        } else if (tokenAtual().isDo()) {
            return parseServicosDoPrestador();
        } else {
            throw new IllegalArgumentException(
                "Esperado 'de' ou 'do' após 'serviços', mas encontrou: " + 
                tokenAtual().valor
            );
        }
    }
    
    private ExpressaoServicos parseServicosDeMes() {
        consumirToken();
        if (!tokenAtual().isMes()) {
            throw new IllegalArgumentException(
                "Esperado nome do mês após 'de', mas encontrou: " + 
                tokenAtual().valor
            );
        }
        
        Mes mes = new Mes(tokenAtual().valor);
        consumirToken();
        
        return new ExpressaoServicos(new ConsultaServicosMes(mes));
    }
    
    private ExpressaoServicos parseServicosDoPrestador() {
        consumirToken();
        
        if (!tokenAtual().isNome()) {
            throw new IllegalArgumentException(
                "Esperado nome do prestador após 'do', mas encontrou: " + 
                tokenAtual().valor
            );
        }
        
        NomePrestador prestador = new NomePrestador(tokenAtual().valor);
        consumirToken();
        
        if (temProximoToken() && tokenAtual().isEm()) {
            consumirToken();
            
            if (!tokenAtual().isMes()) {
                throw new IllegalArgumentException(
                    "Esperado nome do mês após 'em', mas encontrou: " + 
                    tokenAtual().valor
                );
            }
            
            Mes mes = new Mes(tokenAtual().valor);
            consumirToken();
            return new ExpressaoServicos(new ConsultaServicosPrestadorMes(prestador, mes));
        }
        
        return new ExpressaoServicos(new ConsultaServicosPrestador(prestador));
    }
    
    // -- Utilitários --
    
    private Token tokenAtual() {
        if (posicaoAtual >= tokens.size()) {
            throw new IllegalArgumentException("erro posicao atual maior que numero de tokens");
        }
        return tokens.get(posicaoAtual);
    }
    
    private void consumirToken() {
        posicaoAtual++;
    }
    
    private boolean temProximoToken() {
        return posicaoAtual < tokens.size();
    }
    
    private boolean temOperador() {
        return temProximoToken() && tokenAtual().isOperador();
    }
}