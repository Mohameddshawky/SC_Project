package src.main.java.ann.loss;

/**
 * Cross-Entropy loss function.
 * 
 * Formula: L = -Σ(target × log(predicted))
 * 
 * Binary Cross-Entropy (for binary classification):
 *   L = -(target × log(predicted) + (1-target) × log(1-predicted))
 * 
 * Categorical Cross-Entropy (for multi-class classification):
 *   L = -Σ(target_i × log(predicted_i))
 * 
 * Gradient: ∂L/∂predicted = -target / predicted
 * 
 * Used for: Classification problems
 * 
 * Properties:
 * - Works well with sigmoid/softmax output layers
 * - Penalizes confident wrong predictions heavily
 * - Encourages network to be more certain about predictions
 * 
 * Note: Assumes predicted values are probabilities in (0, 1)
 */
public class CrossEntropyLoss implements LossFunction {
    
    private static final double EPSILON = 1e-7;  // Small value to prevent log(0)
    
    @Override
    public double compute(double[] predicted, double[] target) {
        if (predicted.length != target.length) {
            throw new IllegalArgumentException(
                String.format("Array size mismatch: predicted=%d, target=%d", 
                              predicted.length, target.length));
        }
        
        double loss = 0.0;
        
        for (int i = 0; i < predicted.length; i++) {
            // Clip predicted values to avoid log(0) and log(1)
            double clippedPredicted = Math.max(EPSILON, Math.min(1.0 - EPSILON, predicted[i]));
            
            // Cross-entropy: -Σ(target × log(predicted))
            loss -= target[i] * Math.log(clippedPredicted);
        }
        
        return loss / predicted.length;
    }
    
    @Override
    public double[] gradient(double[] predicted, double[] target) {
        if (predicted.length != target.length) {
            throw new IllegalArgumentException(
                String.format("Array size mismatch: predicted=%d, target=%d", 
                              predicted.length, target.length));
        }
        
        double[] grad = new double[predicted.length];
        
        for (int i = 0; i < predicted.length; i++) {
            // Clip predicted values to avoid division by zero
            double clippedPredicted = Math.max(EPSILON, Math.min(1.0 - EPSILON, predicted[i]));
            
            // Gradient: -(target / predicted)
            // Divided by n for averaging
            grad[i] = -(target[i] / clippedPredicted) / predicted.length;
        }
        
        return grad;
    }
    
    @Override
    public String getName() {
        return "Cross-Entropy";
    }
    
    @Override
    public String toString() {
        return "CrossEntropyLoss";
    }
}

