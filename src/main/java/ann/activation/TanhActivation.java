package src.main.java.ann.activation;

/**
 * Hyperbolic Tangent (Tanh) activation function.
 * 
 * Formula: tanh(x) = (e^x - e^(-x)) / (e^x + e^(-x))
 * Derivative: tanh'(x) = 1 - tanh²(x)
 * 
 * Range: (-1, 1)
 * Used for: Hidden layers, zero-centered output
 * 
 * Advantages over Sigmoid:
 * - Zero-centered (helps with gradient flow)
 * - Stronger gradients (no vanishing gradient at 0)
 */
public class TanhActivation implements ActivationFunction {
    
    @Override
    public double activate(double x) {
        return Math.tanh(x);
    }
    
    @Override
    public double derivative(double activatedOutput) {
        // Derivative in terms of the output: tanh'(x) = 1 - tanh²(x)
        // More efficient as we already have tanh(x) from forward pass
        return 1.0 - (activatedOutput * activatedOutput);
    }
    
    @Override
    public String getName() {
        return "Tanh";
    }
    
    @Override
    public String toString() {
        return "TanhActivation";
    }
}

