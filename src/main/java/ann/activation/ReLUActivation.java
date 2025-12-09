package src.main.java.ann.activation;

/**
 * Rectified Linear Unit (ReLU) activation function.
 * 
 * Formula: f(x) = max(0, x)
 * Derivative: f'(x) = 1 if x > 0, else 0
 * 
 * Range: [0, ∞)
 * Used for: Hidden layers in deep networks, prevents vanishing gradient
 * 
 * Advantages:
 * - Computationally efficient
 * - Helps with vanishing gradient problem
 * - Sparse activation (many neurons output 0)
 */
public class ReLUActivation implements ActivationFunction {
    
    @Override
    public double activate(double x) {
        return Math.max(0.0, x);
    }
    
    @Override
    public double derivative(double x) {
        // Derivative: 1 if x > 0, else 0
        // Note: technically undefined at x=0, we use 0 by convention
        return x > 0.0 ? 1.0 : 0.0;
    }
    
    @Override
    public String getName() {
        return "ReLU";
    }
    
    @Override
    public String toString() {
        return "ReLUActivation";
    }
}

