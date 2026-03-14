package minigrad.runtime;

import minigrad.ast.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private final Map<String, Value> environment = new HashMap<>();

    public void execute(List<Stmt> program) {
        for (Stmt stmt : program) {
            executeStmt(stmt);
        }
    }

    private void executeStmt(Stmt stmt) {
        if (stmt instanceof LetStmt letStmt) {
            Type declaredType = parseType(letStmt.getDeclaredType());

            if (letStmt.hasInitializer()) {
                Value value = eval(letStmt.getExpr());
                Value assigned = assign(declaredType, value);
                environment.put(letStmt.getName(), assigned);
            } else {
                environment.put(letStmt.getName(), new Value(declaredType, null, false));
            }
            return;
        }

        if (stmt instanceof AssignStmt assignStmt) {
            Value oldValue = environment.get(assignStmt.getName());
            if (oldValue == null) {
                throw new GradualTypeError("Undefined variable: " + assignStmt.getName());
            }

            Value newValue = eval(assignStmt.getExpr());
            Value assigned = assign(oldValue.getDeclaredType(), newValue);
            environment.put(assignStmt.getName(), assigned);
            return;
        }

        if (stmt instanceof PrintStmt printStmt) {
            Value value = eval(printStmt.getExpr());
            System.out.println(value.getRuntimeValue());
            return;
        }

        throw new RuntimeException("Unknown statement: " + stmt.getClass().getSimpleName());
    }

    private Value eval(Expr expr) {
        if (expr instanceof IntExpr intExpr) {
            return new Value(Type.INT, intExpr.getValue(), true);
        }

        if (expr instanceof StringExpr stringExpr) {
            return new Value(Type.STRING, stringExpr.getValue(), true);
        }

        if (expr instanceof VarExpr varExpr) {
            Value value = environment.get(varExpr.getName());
            if (value == null) {
                throw new GradualTypeError("Undefined variable: " + varExpr.getName());
            }
            if (!value.isInitialized()) {
                throw new GradualTypeError("Uninitialized variable: " + varExpr.getName());
            }
            return value;
        }

        if (expr instanceof AddExpr addExpr) {
            Value left = eval(addExpr.getLeft());
            Value right = eval(addExpr.getRight());

            int l = RuntimeChecks.castInt(left);
            int r = RuntimeChecks.castInt(right);

            return new Value(Type.INT, l + r, true);
        }

        throw new RuntimeException("Unknown expression: " + expr.getClass().getSimpleName());
    }

    private Value assign(Type declaredType, Value value) {
        if (declaredType == Type.DYNAMIC) {
            return new Value(Type.DYNAMIC, value.getRuntimeValue(), true);
        }

        if (declaredType == Type.INT) {
            int v = RuntimeChecks.castInt(value);
            return new Value(Type.INT, v, true);
        }

        if (declaredType == Type.STRING) {
            String v = RuntimeChecks.castString(value);
            return new Value(Type.STRING, v, true);
        }

        throw new RuntimeException("Unknown type: " + declaredType);
    }

    private Type parseType(String typeName) {
        return switch (typeName) {
            case "Int" -> Type.INT;
            case "String" -> Type.STRING;
            case "Dynamic" -> Type.DYNAMIC;
            default -> throw new RuntimeException("Unknown type name: " + typeName);
        };
    }
}