package minigrad.ast;

public class LetStmt implements Stmt {
    private final String name;
    private final String declaredType;
    private final Expr expr; // may be null

    public LetStmt(String name, String declaredType, Expr expr) {
        this.name = name;
        this.declaredType = declaredType;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public String getDeclaredType() {
        return declaredType;
    }

    public Expr getExpr() {
        return expr;
    }

    public boolean hasInitializer() {
        return expr != null;
    }
}