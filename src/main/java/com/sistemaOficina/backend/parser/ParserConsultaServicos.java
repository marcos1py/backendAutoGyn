package com.sistemaOficina.backend.parser;

import java.util.*;

import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosMes;
import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosPrestador;
import com.sistemaOficina.backend.parser.estrutura.ConsultaServicosPrestadorMes;
import com.sistemaOficina.backend.parser.estrutura.ExpressaoServicos;
import com.sistemaOficina.backend.parser.estrutura.Mes;
import com.sistemaOficina.backend.parser.estrutura.NomePrestador;
import com.sistemaOficina.backend.parser.estrutura.OperacaoEExpressao;
import com.sistemaOficina.backend.parser.estrutura.TipoOperacao;

public class ParserConsultaServicos {
    private String entrada;
    private int posicaoAtual;
    private List<Token> tokens;

    private static class Token {
        enum Tipo { 
            SERVICOS, DE, DO, EM, OPERADOR, MES, NOME, ABRE_PAREN, FECHA_PAREN, FIM, ASPAS 
        }

        Tipo tipo;
        String valor;

        Token(Tipo tipo, String valor) {
            this.tipo = tipo;
            this.valor = valor;
        }

        @Override
        public String toString() {
            return tipo + "(" + valor + ")";
        }
    }

    private static final Set<String> MESES_VALIDOS = Set.of(
        "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO",
        "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"
    );

    public ParserConsultaServicos() {
        this.tokens = new ArrayList<>();
        this.posicaoAtual = 0;
    }

    public ExpressaoServicos parse(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) {
            throw new IllegalArgumentException("Consulta não pode ser vazia");
        }

        this.entrada = consulta.trim();
        this.posicaoAtual = 0;
        this.tokens.clear();

        tokenizar();

        this.posicaoAtual = 0;
        return parseConsultaCompleta();
    }

    private void tokenizar() {
        int i = 0;
        while (i < entrada.length()) {
            char c = entrada.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (c == '+' || c == '-') {
                tokens.add(new Token(Token.Tipo.OPERADOR, String.valueOf(c)));
                i++;
                continue;
            }

            if (c == '(') {
                tokens.add(new Token(Token.Tipo.ABRE_PAREN, "("));
                i++;
                continue;
            }

            if (c == ')') {
                tokens.add(new Token(Token.Tipo.FECHA_PAREN, ")"));
                i++;
                continue;
            }

            if (c == '&') {
                tokens.add(new Token(Token.Tipo.FIM, "&"));
                i++;
                continue;
            }

            if (c == '"') {
                StringBuilder nome = new StringBuilder();
                i++; 
                while (i < entrada.length() && entrada.charAt(i) != '"') {
                    nome.append(entrada.charAt(i));
                    i++;
                }
                if (i < entrada.length()) {
                    i++; 
                }
                tokens.add(new Token(Token.Tipo.NOME, nome.toString()));
                continue;
            }

            if (Character.isLetter(c)) {
                StringBuilder palavra = new StringBuilder();
                while (i < entrada.length() && Character.isLetter(entrada.charAt(i))) {
                    palavra.append(entrada.charAt(i));
                    i++;
                }

                String palavraStr = palavra.toString();
                classificarPalavra(palavraStr);
                continue;
            }

            throw new IllegalArgumentException("Caractere não reconhecido: " + c);
        }

        if (tokens.isEmpty() || tokens.get(tokens.size() - 1).tipo != Token.Tipo.FIM) {
            tokens.add(new Token(Token.Tipo.FIM, "&"));
        }
    }

    private void classificarPalavra(String palavra) {
        switch (palavra.toLowerCase()) {
            case "servicos":
            case "serviços":
                tokens.add(new Token(Token.Tipo.SERVICOS, palavra));
                break;
            case "de":
                tokens.add(new Token(Token.Tipo.DE, palavra));
                break;
            case "do":
                tokens.add(new Token(Token.Tipo.DO, palavra));
                break;
            case "em":
                tokens.add(new Token(Token.Tipo.EM, palavra));
                break;
            default:
                if (MESES_VALIDOS.contains(palavra.toUpperCase())) {
                    tokens.add(new Token(Token.Tipo.MES, palavra));
                } else {
                    tokens.add(new Token(Token.Tipo.NOME, palavra));
                }
                break;
        }
    }

    private ExpressaoServicos parseConsultaCompleta() {
        ExpressaoServicos expressao = parseExpressao();

        if (!tokenAtual().tipo.equals(Token.Tipo.FIM)) {
            throw new IllegalArgumentException("Esperado fim da consulta (&), encontrado: " + tokenAtual().valor);
        }

        return expressao;
    }

    private ExpressaoServicos parseExpressao() {
        ExpressaoServicos termo = parseTermo();

        while (posicaoAtual < tokens.size() && 
               tokenAtual().tipo.equals(Token.Tipo.OPERADOR)) {

            TipoOperacao operacao = tokenAtual().valor.equals("+") ? 
                TipoOperacao.SOMA : TipoOperacao.SUBTRACAO;
            avancar(); 

            ExpressaoServicos proximoTermo = parseTermo();
            OperacaoEExpressao operacaoEExpressao = new OperacaoEExpressao(operacao, proximoTermo);
            termo.adicionarOperacao(operacaoEExpressao);
        }

        return termo;
    }

    private ExpressaoServicos parseTermo() {
        if (tokenAtual().tipo.equals(Token.Tipo.ABRE_PAREN)) {
            avancar(); 
            ExpressaoServicos expressaoInterna = parseExpressao();

            if (!tokenAtual().tipo.equals(Token.Tipo.FECHA_PAREN)) {
                throw new IllegalArgumentException("Esperado ')', encontrado: " + tokenAtual().valor);
            }
            avancar(); 

            return new ExpressaoServicos(expressaoInterna); 
        }

        return parseConsultaBase();
    }

    private ExpressaoServicos parseConsultaBase() {
        if (!tokenAtual().tipo.equals(Token.Tipo.SERVICOS)) {
            throw new IllegalArgumentException("Esperado 'serviços', encontrado: " + tokenAtual().valor);
        }
        avancar(); 

        if (tokenAtual().tipo.equals(Token.Tipo.DE)) {
            return parseServicosDe(); 
        } else if (tokenAtual().tipo.equals(Token.Tipo.DO)) {
            return parseServicosDo(); 
        } else {
            throw new IllegalArgumentException("Esperado 'de' ou 'do' após 'serviços', encontrado: " + tokenAtual().valor);
        }
    }

    private ExpressaoServicos parseServicosDe() {
        avancar(); 

        if (!tokenAtual().tipo.equals(Token.Tipo.MES)) {
            throw new IllegalArgumentException("Esperado nome de mês, encontrado: " + tokenAtual().valor);
        }

        Mes mes = new Mes(tokenAtual().valor);
        avancar(); 

        ConsultaServicosMes consulta = new ConsultaServicosMes(mes);
        return new ExpressaoServicos(consulta);
    }

    private ExpressaoServicos parseServicosDo() {
        avancar(); 

        if (!tokenAtual().tipo.equals(Token.Tipo.NOME)) {
            throw new IllegalArgumentException("Esperado nome do prestador, encontrado: " + tokenAtual().valor);
        }

        NomePrestador prestador = new NomePrestador(tokenAtual().valor);
        avancar(); 

        if (posicaoAtual < tokens.size() && 
            tokenAtual().tipo.equals(Token.Tipo.EM)) {

            avancar(); 

            if (!tokenAtual().tipo.equals(Token.Tipo.MES)) {
                throw new IllegalArgumentException("Esperado nome de mês após 'em', encontrado: " + tokenAtual().valor);
            }

            Mes mes = new Mes(tokenAtual().valor);
            avancar(); 

            ConsultaServicosPrestadorMes consulta = new ConsultaServicosPrestadorMes(prestador, mes);
            return new ExpressaoServicos(consulta);
        } else {
            ConsultaServicosPrestador consulta = new ConsultaServicosPrestador(prestador);
            return new ExpressaoServicos(consulta);
        }
    }

    private Token tokenAtual() {
        if (posicaoAtual >= tokens.size()) {
            throw new IllegalArgumentException("Fim inesperado da consulta");
        }
        return tokens.get(posicaoAtual);
    }

    private void avancar() {
        if (posicaoAtual < tokens.size()) {
            posicaoAtual++;
        }
    }
}