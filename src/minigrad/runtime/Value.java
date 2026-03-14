package minigrad.runtime;

public class Value {
    private final Type declaredType;
    private final Object runtimeValue;
    private final boolean initialized;

    public Value(Type declaredType, Object runtimeValue, boolean initialized) {
        this.declaredType = declaredType;
        this.runtimeValue = runtimeValue;
        this.initialized = initialized;
    }

    public Type getDeclaredType() {
        return declaredType;
    }

    public Object getRuntimeValue() {
        return runtimeValue;
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String toString() {
        if (!initialized) {
            return declaredType + "(uninitialized)";
        }
        return declaredType + "(" + runtimeValue + ")";
    }
}