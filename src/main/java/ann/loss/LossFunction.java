package src.main.java.ann.loss;

/**
 * Interface for loss functions in neural networks.
 * Loss functions measure the difference between predicted and target values.
 */
public interface LossFunction {
    
    /**
     * Computes the loss for a single prediction.
     * 
     * @param predicted predicted output vector
     * @param target target (ground truth) vector
     * @return loss value
     */
    double compute(double[] predicted, double[] target);
    
    /**
     * Computes the gradient of the loss with respect to the predicted output.
     * Used during backpropagation.
     * 
     * @param predicted predicted output vector
     * @param target target (ground truth) vector
     * @return gradient vector (same size as predicted)
     */
    double[] gradient(double[] predicted, double[] target);
    
    /**
     * Gets the name of this loss function.
     * 
     * @return the loss function name
     */
    String getName();
}

