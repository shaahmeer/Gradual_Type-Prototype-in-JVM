package minigrad.ast;

public class StringExpr implements Expr {
    private final String value;

    public StringExpr(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}