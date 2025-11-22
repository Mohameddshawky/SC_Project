package src.main.java.fl.operators;

/**
 * Minimum T-norm operator: min(a, b)
 * This is the most commonly used T-norm in fuzzy logic.
 */
public class MinimumTNorm implements TNorm {
    
    @Override
    public double apply(double a, double b) {
        return Math.min(a, b);
    }
    
    @Override
    public String getName() {
        return "Minimum";
    }
    
    @Override
    public String toString() {
        return "MinimumTNorm";
    }
}

