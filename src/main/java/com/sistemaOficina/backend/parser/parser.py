import re
from typing import List, Optional, Union
from dataclasses import dataclass

@dataclass
class Node:
    """Nó da árvore sintática"""
    type: str
    value: Optional[str] = None
    children: List['Node'] = None
    
    def __post_init__(self):
        if self.children is None:
            self.children = []
    
    def add_child(self, child: 'Node'):
        self.children.append(child)
    
    def __str__(self):
        return self.pretty_print()
    
    def pretty_print(self, indent=0):
        """Impressão formatada da árvore"""
        spaces = "  " * indent
        if self.value:
            result = f"{spaces}{self.type}: {self.value}\n"
        else:
            result = f"{spaces}{self.type}\n"
        
        for child in self.children:
            result += child.pretty_print(indent + 1)
        
        return result

class Token:
    """Representa um token"""
    def __init__(self, type: str, value: str, pos: int):
        self.type = type
        self.value = value
        self.pos = pos
    
    def __repr__(self):
        return f"Token({self.type}, {self.value})"

class Lexer:
    """Analisador léxico"""
    def __init__(self, text: str):
        self.text = text
        self.pos = 0
        self.tokens = []
        self.meses = {
            'janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho',
            'julho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro'
        }
    
    def tokenize(self) -> List[Token]:
        """Converte o texto em tokens"""
        self.tokens = []
        self.pos = 0
        
        while self.pos < len(self.text):
            self._skip_whitespace()
            
            if self.pos >= len(self.text):
                break
            
            # Parênteses
            if self.text[self.pos] == '(':
                self.tokens.append(Token('LPAREN', '(', self.pos))
                self.pos += 1
            elif self.text[self.pos] == ')':
                self.tokens.append(Token('RPAREN', ')', self.pos))
                self.pos += 1
            
            # Operadores
            elif self.text[self.pos] == '+':
                self.tokens.append(Token('PLUS', '+', self.pos))
                self.pos += 1
            elif self.text[self.pos] == '-':
                self.tokens.append(Token('MINUS', '-', self.pos))
                self.pos += 1
            
            # Strings entre aspas
            elif self.text[self.pos] == '"':
                self._read_quoted_string()
            
            # Palavras-chave e identificadores
            else:
                self._read_word()
        
        self.tokens.append(Token('EOF', '', self.pos))
        return self.tokens
    
    def _skip_whitespace(self):
        while self.pos < len(self.text) and self.text[self.pos].isspace():
            self.pos += 1
    
    def _read_quoted_string(self):
        start_pos = self.pos
        self.pos += 1  # pula a primeira aspas
        value = ''
        
        while self.pos < len(self.text) and self.text[self.pos] != '"':
            value += self.text[self.pos]
            self.pos += 1
        
        if self.pos < len(self.text):
            self.pos += 1  # pula a aspas de fechamento
        
        self.tokens.append(Token('QUOTED_STRING', value, start_pos))
    
    def _read_word(self):
        start_pos = self.pos
        word = ''
        
        # Lê apenas caracteres alfanuméricos e apóstrofes (sem espaços)
        while (self.pos < len(self.text) and 
               (self.text[self.pos].isalnum() or self.text[self.pos] == "'")):
            word += self.text[self.pos]
            self.pos += 1
        
        word = word.strip()
        
        # Identifica o tipo do token
        if word.lower() in self.meses:
            self.tokens.append(Token('MONTH', word, start_pos))
        elif word == 'vendas':
            self.tokens.append(Token('VENDAS', word, start_pos))
        elif word == 'de':
            self.tokens.append(Token('DE', word, start_pos))
        elif word == 'do':
            self.tokens.append(Token('DO', word, start_pos))
        elif word == 'em':
            self.tokens.append(Token('EM', word, start_pos))
        else:
            self.tokens.append(Token('IDENTIFIER', word, start_pos))

class Parser:
    """Parser recursivo descendente"""
    def __init__(self, tokens: List[Token]):
        self.tokens = tokens
        self.pos = 0
        self.current_token = self.tokens[0] if tokens else None
    
    def parse(self) -> Node:
        """Ponto de entrada do parser"""
        return self.parse_S()
    
    def advance(self):
        """Avança para o próximo token"""
        if self.pos < len(self.tokens) - 1:
            self.pos += 1
            self.current_token = self.tokens[self.pos]
    
    def expect(self, token_type: str) -> Token:
        """Verifica se o token atual é do tipo esperado"""
        if self.current_token.type != token_type:
            raise SyntaxError(f"Esperado {token_type}, encontrado {self.current_token.type} na posição {self.current_token.pos}")
        token = self.current_token
        self.advance()
        return token
    
    def parse_S(self) -> Node:
        """S -> E | E O E"""
        node = Node('S')
        
        # Primeiro E
        e1 = self.parse_E()
        node.add_child(e1)
        
        # Verifica se há operador (E O E)
        if self.current_token.type in ['PLUS', 'MINUS']:
            op = self.parse_O()
            node.add_child(op)
            
            e2 = self.parse_E()
            node.add_child(e2)
        
        return node
    
    def parse_E(self) -> Node:
        """E -> (E) | B1 | B2 | B3 | E O E'"""
        node = Node('E')
        
        if self.current_token.type == 'LPAREN':
            # (E)
            self.expect('LPAREN')
            lparen = Node('LPAREN', '(')
            node.add_child(lparen)
            
            e = self.parse_E()
            node.add_child(e)
            
            self.expect('RPAREN')
            rparen = Node('RPAREN', ')')
            node.add_child(rparen)
            
            # Verifica continuação E O E'
            if self.current_token.type in ['PLUS', 'MINUS']:
                op = self.parse_O()
                node.add_child(op)
                
                e_prime = self.parse_E_prime()
                node.add_child(e_prime)
        
        elif self.current_token.type == 'VENDAS':
            # Tenta B1, B2 ou B3
            b = self.parse_B()
            node.add_child(b)
            
            # Verifica continuação E O E'
            if self.current_token.type in ['PLUS', 'MINUS']:
                op = self.parse_O()
                node.add_child(op)
                
                e_prime = self.parse_E_prime()
                node.add_child(e_prime)
        
        else:
            raise SyntaxError(f"Token inesperado: {self.current_token.type}")
        
        return node
    
    def parse_E_prime(self) -> Node:
        """E' -> (E) | B1 | B2 | B3"""
        node = Node("E'")
        
        if self.current_token.type == 'LPAREN':
            # (E)
            self.expect('LPAREN')
            lparen = Node('LPAREN', '(')
            node.add_child(lparen)
            
            e = self.parse_E()
            node.add_child(e)
            
            self.expect('RPAREN')
            rparen = Node('RPAREN', ')')
            node.add_child(rparen)
        
        elif self.current_token.type == 'VENDAS':
            b = self.parse_B()
            node.add_child(b)
        
        else:
            raise SyntaxError(f"Token inesperado em E': {self.current_token.type}")
        
        return node
    
    def parse_B(self) -> Node:
        """Determina se é B1, B2 ou B3"""
        if self.current_token.type != 'VENDAS':
            raise SyntaxError("Esperado 'vendas'")
        
        # Salva posição para backtracking
        saved_pos = self.pos
        
        try:
            # Tenta B2: vendas do N em M
            return self.parse_B2()
        except:
            # Restaura posição
            self.pos = saved_pos
            self.current_token = self.tokens[self.pos]
            
            try:
                # Tenta B3: vendas do N
                return self.parse_B3()
            except:
                # Restaura posição
                self.pos = saved_pos
                self.current_token = self.tokens[self.pos]
                
                # Tenta B1: vendas de M
                return self.parse_B1()
    
    def parse_B1(self) -> Node:
        """B1 -> vendas de M"""
        node = Node('B1')
        
        self.expect('VENDAS')
        vendas = Node('VENDAS', 'vendas')
        node.add_child(vendas)
        
        self.expect('DE')
        de = Node('DE', 'de')
        node.add_child(de)
        
        m = self.parse_M()
        node.add_child(m)
        
        return node
    
    def parse_B2(self) -> Node:
        """B2 -> vendas do N em M"""
        node = Node('B2')
        
        self.expect('VENDAS')
        vendas = Node('VENDAS', 'vendas')
        node.add_child(vendas)
        
        self.expect('DO')
        do = Node('DO', 'do')
        node.add_child(do)
        
        n = self.parse_N()
        node.add_child(n)
        
        self.expect('EM')
        em = Node('EM', 'em')
        node.add_child(em)
        
        m = self.parse_M()
        node.add_child(m)
        
        return node
    
    def parse_B3(self) -> Node:
        """B3 -> vendas do N"""
        node = Node('B3')
        
        self.expect('VENDAS')
        vendas = Node('VENDAS', 'vendas')
        node.add_child(vendas)
        
        self.expect('DO')
        do = Node('DO', 'do')
        node.add_child(do)
        
        n = self.parse_N()
        node.add_child(n)
        
        return node
    
    def parse_O(self) -> Node:
        """O -> + | -"""
        node = Node('O')
        
        if self.current_token.type == 'PLUS':
            self.expect('PLUS')
            plus = Node('PLUS', '+')
            node.add_child(plus)
        elif self.current_token.type == 'MINUS':
            self.expect('MINUS')
            minus = Node('MINUS', '-')
            node.add_child(minus)
        else:
            raise SyntaxError(f"Esperado + ou -, encontrado {self.current_token.type}")
        
        return node
    
    def parse_N(self) -> Node:
        """N -> "N'"""
        node = Node('N')
        
        if self.current_token.type == 'QUOTED_STRING':
            quoted = self.current_token.value
            self.advance()
            n_prime = Node("N'", quoted)
            node.add_child(n_prime)
        else:
            raise SyntaxError(f"Esperado string entre aspas, encontrado {self.current_token.type}")
        
        return node
    
    def parse_M(self) -> Node:
        """M -> Janeiro | Fevereiro | ... | Dezembro"""
        node = Node('M')
        
        if self.current_token.type == 'MONTH':
            month = Node('MONTH', self.current_token.value)
            node.add_child(month)
            self.advance()
        else:
            raise SyntaxError(f"Esperado nome do mês, encontrado {self.current_token.type}")
        
        return node

def main():
    """Função principal para testes"""
    exemplos = [
        'vendas de Janeiro',
        'vendas do "João" em Março',
        'vendas do "Maria"',
        'vendas de Abril + vendas do "Pedro"',
        '(vendas de Maio) - vendas do "Ana" em Junho',
        'vendas de Janeiro + vendas do "Carlos" - vendas de Dezembro'
    ]
    
    for exemplo in exemplos:
        print(f"\n{'='*50}")
        print(f"Analisando: {exemplo}")
        print('='*50)
        
        try:
            # Tokenização
            lexer = Lexer(exemplo)
            tokens = lexer.tokenize()
            
            print("Tokens:")
            for token in tokens[:-1]:  # Excluir EOF
                print(f"  {token}")
            
            # Parsing
            parser = Parser(tokens)
            arvore = parser.parse()
            
            print("\nÁrvore Sintática:")
            print(arvore)
            
        except Exception as e:
            print(f"Erro: {e}")

if __name__ == "__main__":
    main()