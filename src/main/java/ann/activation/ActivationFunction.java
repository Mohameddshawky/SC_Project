package src.main.java.ann.activation;

/**
 * Interface for activation functions in neural networks.
 * Each activation function must provide both forward (activation) and backward (derivative) operations.
 */
public interface ActivationFunction {
    
    /**
     * Applies the activation function to the input value.
     * 
     * @param x the input value
     * @return the activated output
     */
    double activate(double x);
    
    /**
     * Computes the derivative of the activation function.
     * Used during backpropagation.
     * 
     * @param x the input value (or activated output, depending on the function)
     * @return the derivative value
     */
    double derivative(double x);
    
    /**
     * Gets the name of this activation function.
     * 
     * @return the activation function name
     */
    String getName();
}

