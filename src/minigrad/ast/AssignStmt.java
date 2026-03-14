package minigrad.ast;

public class AssignStmt implements Stmt {
    private final String name;
    private final Expr expr;

    public AssignStmt(String name, Expr expr) {
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public Expr getExpr() {
        return expr;
    }
}