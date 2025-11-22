package src.main.java.fl.operators;

/**
 * Maximum S-norm operator: max(a, b)
 * This is the most commonly used S-norm in fuzzy logic.
 */
public class MaximumSNorm implements SNorm {
    
    @Override
    public double apply(double a, double b) {
        return Math.max(a, b);
    }
    
    @Override
    public String getName() {
        return "Maximum";
    }
    
    @Override
    public String toString() {
        return "MaximumSNorm";
    }
}

