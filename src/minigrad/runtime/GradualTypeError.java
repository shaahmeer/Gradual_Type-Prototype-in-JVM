package minigrad.runtime;

public class GradualTypeError extends RuntimeException {
    public GradualTypeError(String message) {
        super(message);
    }
}