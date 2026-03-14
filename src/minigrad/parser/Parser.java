package minigrad.parser;

import minigrad.ast.*;
import minigrad.lexer.Token;
import minigrad.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parseProgram() {
        List<Stmt> statements = new ArrayList<>();

        while (!check(TokenType.EOF)) {
            statements.add(parseStmt());
        }

        return statements;
    }

    private Stmt parseStmt() {
        if (match(TokenType.LET)) {
            return parseLet();
        }

        if (match(TokenType.PRINT)) {
            return parsePrint();
        }

        if (check(TokenType.IDENTIFIER) && checkNext(TokenType.EQUALS)) {
            return parseAssign();
        }

        throw error("Expected statement");
    }

    private Stmt parseLet() {
        Token name = consume(TokenType.IDENTIFIER, "Expected variable name");
        consume(TokenType.COLON, "Expected ':'");
        Token type = consume(TokenType.TYPE, "Expected type");

        // let x: Int;
        if (match(TokenType.SEMICOLON)) {
            return new LetStmt(name.getText(), type.getText(), null);
        }

        // let x: Int = expr;
        consume(TokenType.EQUALS, "Expected '='");
        Expr expr = parseExpr();
        consume(TokenType.SEMICOLON, "Expected ';'");

        return new LetStmt(name.getText(), type.getText(), expr);
    }

    private Stmt parseAssign() {
        Token name = consume(TokenType.IDENTIFIER, "Expected variable name");
        consume(TokenType.EQUALS, "Expected '='");
        Expr expr = parseExpr();
        consume(TokenType.SEMICOLON, "Expected ';'");

        return new AssignStmt(name.getText(), expr);
    }

    private Stmt parsePrint() {
        consume(TokenType.LPAREN, "Expected '('");
        Expr expr = parseExpr();
        consume(TokenType.RPAREN, "Expected ')'");
        consume(TokenType.SEMICOLON, "Expected ';'");

        return new PrintStmt(expr);
    }

    private Expr parseExpr() {
        Expr expr = parsePrimary();

        while (match(TokenType.PLUS)) {
            Expr right = parsePrimary();
            expr = new AddExpr(expr, right);
        }

        return expr;
    }

    private Expr parsePrimary() {
        if (match(TokenType.INT_LITERAL)) {
            return new IntExpr(Integer.parseInt(previous().getText()));
        }

        if (match(TokenType.STRING_LITERAL)) {
            return new StringExpr(previous().getText());
        }

        if (match(TokenType.IDENTIFIER)) {
            return new VarExpr(previous().getText());
        }

        throw error("Expected expression");
    }

    private boolean match(TokenType type) {
        if (check(type)) {
            pos++;
            return true;
        }
        return false;
    }

    private boolean check(TokenType type) {
        return peek().getType() == type;
    }

    private boolean checkNext(TokenType type) {
        if (pos + 1 >= tokens.size()) {
            return false;
        }
        return tokens.get(pos + 1).getType() == type;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return tokens.get(pos++);
        }
        throw error(message + ", got " + peek());
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }

    private RuntimeException error(String message) {
        return new RuntimeException("Parse error at token " + peek() + ": " + message);
    }
}