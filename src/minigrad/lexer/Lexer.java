package minigrad.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> lex() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }

            if (Character.isLetter(c) || c == '_') {
                tokens.add(readWord());
                continue;
            }

            if (Character.isDigit(c)) {
                tokens.add(readInt());
                continue;
            }

            if (c == '"') {
                tokens.add(readString());
                continue;
            }

            switch (c) {
                case ':' -> tokens.add(new Token(TokenType.COLON, ":"));
                case '=' -> tokens.add(new Token(TokenType.EQUALS, "="));
                case ';' -> tokens.add(new Token(TokenType.SEMICOLON, ";"));
                case '+' -> tokens.add(new Token(TokenType.PLUS, "+"));
                case '(' -> tokens.add(new Token(TokenType.LPAREN, "("));
                case ')' -> tokens.add(new Token(TokenType.RPAREN, ")"));
                default -> throw new RuntimeException("Unexpected character: " + c);
            }

            pos++;
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token readWord() {
        int start = pos;

        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isLetterOrDigit(c) || c == '_') {
                pos++;
            } else {
                break;
            }
        }

        String word = input.substring(start, pos);

        return switch (word) {
            case "let" -> new Token(TokenType.LET, word);
            case "print" -> new Token(TokenType.PRINT, word);
            case "Int", "String", "Dynamic" -> new Token(TokenType.TYPE, word);
            default -> new Token(TokenType.IDENTIFIER, word);
        };
    }

    private Token readInt() {
        int start = pos;

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }

        return new Token(TokenType.INT_LITERAL, input.substring(start, pos));
    }

    private Token readString() {
        pos++; // skip opening quote
        int start = pos;

        while (pos < input.length() && input.charAt(pos) != '"') {
            pos++;
        }

        if (pos >= input.length()) {
            throw new RuntimeException("Unterminated string literal");
        }

        String content = input.substring(start, pos);
        pos++; // skip closing quote

        return new Token(TokenType.STRING_LITERAL, content);
    }
}