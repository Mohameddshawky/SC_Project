package src.main.java.ann.layer;

import src.main.java.ann.activation.ActivationFunction;
import src.main.java.ann.initialization.WeightInitializer;

/**
 * Fully connected (Dense) layer implementation.
 * 
 * Each output neuron is connected to all input neurons.
 * 
 * Forward pass:
 *   z = W × input + b
 *   output = activation(z)
 * 
 * Backward pass:
 *   Computes gradients for weights, biases, and input
 *   Updates weights and biases using gradient descent
 */
public class DenseLayer extends Layer {
    
    private double[][] weights;  // [outputSize][inputSize]
    private double[] biases;     // [outputSize]
    private ActivationFunction activation;
    
    // Gradients for weight updates
    private double[][] weightGradients;
    private double[] biasGradients;
    
    /**
     * Creates a dense layer with specified parameters.
     * 
     * @param inputSize number of input neurons
     * @param outputSize number of output neurons
     * @param activation activation function
     * @param initializer weight initialization strategy
     */
    public DenseLayer(int inputSize, int outputSize, 
                      ActivationFunction activation, 
                      WeightInitializer initializer) {
        super(inputSize, outputSize);
        
        this.activation = activation;
        this.weights = initializer.initializeWeights(inputSize, outputSize);
        this.biases = initializer.initializeBiases(outputSize);
        
        this.weightGradients = new double[outputSize][inputSize];
        this.biasGradients = new double[outputSize];
    }
    
    /**
     * Creates a dense layer with pre-initialized weights and biases.
     * Useful for loading saved models.
     * 
     * @param inputSize number of input neurons
     * @param outputSize number of output neurons
     * @param activation activation function
     * @param weights weight matrix [outputSize][inputSize]
     * @param biases bias vector [outputSize]
     */
    public DenseLayer(int inputSize, int outputSize, 
                      ActivationFunction activation,
                      double[][] weights, double[] biases) {
        super(inputSize, outputSize);
        
        this.activation = activation;
        this.weights = weights;
        this.biases = biases;
        
        this.weightGradients = new double[outputSize][inputSize];
        this.biasGradients = new double[outputSize];
    }
    
    @Override
    public double[] forward(double[] input) {
        if (input.length != inputSize) {
            throw new IllegalArgumentException(
                String.format("Input size mismatch: expected %d, got %d", 
                              inputSize, input.length));
        }
        
        // Store input for backpropagation
        this.lastInput = input.clone();
        
        // Initialize output arrays
        this.lastWeightedSum = new double[outputSize];
        this.lastOutput = new double[outputSize];
        
        // Compute weighted sum: z = W × input + b
        for (int i = 0; i < outputSize; i++) {
            double sum = biases[i];
            for (int j = 0; j < inputSize; j++) {
                sum += weights[i][j] * input[j];
            }
            lastWeightedSum[i] = sum;
            
            // Apply activation function
            lastOutput[i] = activation.activate(sum);
        }
        
        return lastOutput;
    }
    
    @Override
    public double[] backward(double[] outputGradient, double learningRate) {
        if (outputGradient.length != outputSize) {
            throw new IllegalArgumentException(
                String.format("Gradient size mismatch: expected %d, got %d", 
                              outputSize, outputGradient.length));
        }
        
        double[] inputGradient = new double[inputSize];
        
        // Compute gradients for this layer
        for (int i = 0; i < outputSize; i++) {
            // Gradient of activation function
            double activationGrad = activation.derivative(lastOutput[i]);
            
            // Delta for this neuron: gradient × activation derivative
            double delta = outputGradient[i] * activationGrad;
            
            // Gradient for bias: just the delta
            biasGradients[i] = delta;
            
            // Gradient for weights and input
            for (int j = 0; j < inputSize; j++) {
                // Weight gradient: delta × input
                weightGradients[i][j] = delta * lastInput[j];
                
                // Input gradient (for previous layer): delta × weight
                inputGradient[j] += delta * weights[i][j];
            }
        }
        
        // Update weights and biases using gradient descent
        updateParameters(learningRate);
        
        return inputGradient;
    }
    
    /**
     * Updates weights and biases using computed gradients.
     * 
     * @param learningRate learning rate for gradient descent
     */
    private void updateParameters(double learningRate) {
        // Update weights: W = W - η × ∇W
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] -= learningRate * weightGradients[i][j];
            }
            
            // Update biases: b = b - η × ∇b
            biases[i] -= learningRate * biasGradients[i];
        }
    }
    
    @Override
    public double[][] getWeights() {
        return weights;
    }
    
    @Override
    public double[] getBiases() {
        return biases;
    }
    
    /**
     * Gets the activation function used by this layer.
     * 
     * @return activation function
     */
    public ActivationFunction getActivation() {
        return activation;
    }
    
    /**
     * Gets the weight gradients (for debugging/monitoring).
     * 
     * @return weight gradient matrix
     */
    public double[][] getWeightGradients() {
        return weightGradients;
    }
    
    /**
     * Gets the bias gradients (for debugging/monitoring).
     * 
     * @return bias gradient vector
     */
    public double[] getBiasGradients() {
        return biasGradients;
    }
    
    @Override
    public String toString() {
        return String.format("DenseLayer[in=%d, out=%d, activation=%s]", 
                             inputSize, outputSize, activation.getName());
    }
}

