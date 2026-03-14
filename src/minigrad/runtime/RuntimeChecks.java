package minigrad.runtime;

public final class RuntimeChecks {
    private RuntimeChecks() {
    }

    public static int castInt(Value value) {
        if ((value.getDeclaredType() == Type.INT || value.getDeclaredType() == Type.DYNAMIC)
                && value.getRuntimeValue() instanceof Integer i) {
            return i;
        }

        throw new GradualTypeError("Expected Int, got " + runtimeTypeName(value.getRuntimeValue()));
    }

    public static String castString(Value value) {
        if ((value.getDeclaredType() == Type.STRING || value.getDeclaredType() == Type.DYNAMIC)
                && value.getRuntimeValue() instanceof String s) {
            return s;
        }

        throw new GradualTypeError("Expected String, got " + runtimeTypeName(value.getRuntimeValue()));
    }

    public static String runtimeTypeName(Object value) {
        if (value instanceof Integer) {
            return "Int";
        }
        if (value instanceof String) {
            return "String";
        }
        if (value == null) {
            return "null";
        }
        return value.getClass().getSimpleName();
    }
}