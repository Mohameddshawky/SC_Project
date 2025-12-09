package src.main.java.ann.layer;

/**
 * Abstract base class for neural network layers.
 * Defines the interface that all layer types must implement.
 */
public abstract class Layer {
    
    protected int inputSize;
    protected int outputSize;
    
    // Store intermediate values for backpropagation
    protected double[] lastInput;
    protected double[] lastWeightedSum;  // z = W*x + b (before activation)
    protected double[] lastOutput;       // a = activation(z)
    
    /**
     * Creates a layer with specified input and output sizes.
     * 
     * @param inputSize number of input neurons
     * @param outputSize number of output neurons
     */
    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
    }
    
    /**
     * Forward pass: compute layer output given input.
     * 
     * @param input input vector [inputSize]
     * @return output vector [outputSize]
     */
    public abstract double[] forward(double[] input);
    
    /**
     * Backward pass: compute gradients and propagate error.
     * 
     * @param outputGradient gradient flowing from next layer [outputSize]
     * @return gradient to propagate to previous layer [inputSize]
     */
    public abstract double[] backward(double[] outputGradient, double learningRate);
    
    /**
     * Gets the number of input neurons.
     * 
     * @return input size
     */
    public int getInputSize() {
        return inputSize;
    }
    
    /**
     * Gets the number of output neurons.
     * 
     * @return output size
     */
    public int getOutputSize() {
        return outputSize;
    }
    
    /**
     * Gets the last output computed during forward pass.
     * 
     * @return last output vector
     */
    public double[] getLastOutput() {
        return lastOutput;
    }
    
    /**
     * Gets the last weighted sum (pre-activation) computed during forward pass.
     * 
     * @return last weighted sum vector
     */
    public double[] getLastWeightedSum() {
        return lastWeightedSum;
    }
    
    /**
     * Gets the weights of this layer.
     * 
     * @return weight matrix or null if layer has no weights
     */
    public abstract double[][] getWeights();
    
    /**
     * Gets the biases of this layer.
     * 
     * @return bias vector or null if layer has no biases
     */
    public abstract double[] getBiases();
}

