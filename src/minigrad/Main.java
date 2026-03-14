package minigrad;

import minigrad.ast.Stmt;
import minigrad.lexer.Lexer;
import minigrad.lexer.Token;
import minigrad.parser.Parser;
import minigrad.runtime.GradualTypeError;
import minigrad.runtime.Interpreter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();

        System.out.println("Enter your MiniGrad program.");
        System.out.println("Type END on a new line when finished.\n");

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("END")) {
                break;
            }
            builder.append(line).append("\n");
        }

        String program = builder.toString();

        System.out.println("\n=== Source Program ===");
        System.out.println(program);

        Lexer lexer = new Lexer(program);
        List<Token> tokens = lexer.lex();

        System.out.println("=== Tokens ===");
        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser = new Parser(tokens);
        List<Stmt> ast = parser.parseProgram();

        System.out.println("=== Output ===");
        Interpreter interpreter = new Interpreter();

        try {
            interpreter.execute(ast);
        } catch (GradualTypeError e) {
            System.out.println("Runtime type error: " + e.getMessage());
        }
    }
}