package minigrad.ast;

public class IntExpr implements Expr {
    private final int value;

    public IntExpr(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}