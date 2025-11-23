package src.main.java.fl.operators;

/**
 * Probabilistic Sum S-norm operator: a + b - a*b
 * Also known as algebraic sum.
 */
public class ProbabilisticSumSNorm implements SNorm {
    
    @Override
    public double apply(double a, double b) {
        return a + b - (a * b);
    }
    
    @Override
    public String getName() {
        return "Probabilistic Sum";
    }
    
    @Override
    public String toString() {
        return "ProbabilisticSumSNorm";
    }
}

