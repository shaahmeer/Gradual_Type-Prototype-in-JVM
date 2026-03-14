package minigrad.ast;

public class AddExpr implements Expr {
    private final Expr left;
    private final Expr right;

    public AddExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }
}