package src.main.java.ann.activation;

/**
 * Linear (Identity) activation function.
 * 
 * Formula: f(x) = x
 * Derivative: f'(x) = 1
 * 
 * Range: (-∞, ∞)
 * Used for: Regression problems, output layer for continuous values
 * 
 * Note: Using only linear activations makes the network equivalent to 
 * a single-layer perceptron, regardless of depth.
 */
public class LinearActivation implements ActivationFunction {
    
    @Override
    public double activate(double x) {
        return x;
    }
    
    @Override
    public double derivative(double x) {
        // Derivative of f(x) = x is always 1
        return 1.0;
    }
    
    @Override
    public String getName() {
        return "Linear";
    }
    
    @Override
    public String toString() {
        return "LinearActivation";
    }
}

