package src.main.java.ann.activation;

/**
 * Sigmoid activation function.
 * 
 * Formula: σ(x) = 1 / (1 + e^(-x))
 * Derivative: σ'(x) = σ(x) × (1 - σ(x))
 * 
 * Range: (0, 1)
 * Used for: Binary classification, output layer for probability
 */
public class SigmoidActivation implements ActivationFunction {
    
    @Override
    public double activate(double x) {
        // Prevent overflow for very large negative values
        if (x < -700) {
            return 0.0;
        } else if (x > 700) {
            return 1.0;
        }
        return 1.0 / (1.0 + Math.exp(-x));
    }
    
    @Override
    public double derivative(double activatedOutput) {
        // Derivative in terms of the output: σ'(x) = σ(x) × (1 - σ(x))
        // This is more efficient as we already have σ(x) from forward pass
        return activatedOutput * (1.0 - activatedOutput);
    }
    
    @Override
    public String getName() {
        return "Sigmoid";
    }
    
    @Override
    public String toString() {
        return "SigmoidActivation";
    }
}

