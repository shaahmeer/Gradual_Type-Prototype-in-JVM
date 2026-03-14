package minigrad.ast;

public class VarExpr implements Expr {
    private final String name;

    public VarExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}