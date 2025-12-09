package src.main.java.ann.loss;

/**
 * Mean Squared Error (MSE) loss function.
 * 
 * Formula: L = (1/n) × Σ(target - predicted)²
 * 
 * For backpropagation, we use (1/2) × Σ(target - predicted)² to simplify the gradient.
 * Gradient: ∂L/∂predicted = -(target - predicted) = (predicted - target)
 * 
 * Used for: Regression problems
 * 
 * Properties:
 * - Sensitive to outliers (quadratic penalty)
 * - Always non-negative
 * - Differentiable everywhere
 */
public class MSELoss implements LossFunction {
    
    @Override
    public double compute(double[] predicted, double[] target) {
        if (predicted.length != target.length) {
            throw new IllegalArgumentException(
                String.format("Array size mismatch: predicted=%d, target=%d", 
                              predicted.length, target.length));
        }
        
        double sum = 0.0;
        for (int i = 0; i < predicted.length; i++) {
            double error = target[i] - predicted[i];
            sum += error * error;
        }
        
        // Return (1/2) × MSE for easier gradient computation
        return 0.5 * sum / predicted.length;
    }
    
    @Override
    public double[] gradient(double[] predicted, double[] target) {
        if (predicted.length != target.length) {
            throw new IllegalArgumentException(
                String.format("Array size mismatch: predicted=%d, target=%d", 
                              predicted.length, target.length));
        }
        
        double[] grad = new double[predicted.length];
        
        // Gradient: (predicted - target) / n
        for (int i = 0; i < predicted.length; i++) {
            grad[i] = (predicted[i] - target[i]) / predicted.length;
        }
        
        return grad;
    }
    
    @Override
    public String getName() {
        return "Mean Squared Error";
    }
    
    @Override
    public String toString() {
        return "MSELoss";
    }
}

