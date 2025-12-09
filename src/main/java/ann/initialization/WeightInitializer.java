package src.main.java.ann.initialization;

/**
 * Interface for weight initialization strategies.
 * Proper initialization is crucial for successful neural network training.
 */
public interface WeightInitializer {
    
    /**
     * Initializes a weight matrix for a layer.
     * 
     * @param numInputs number of input neurons
     * @param numOutputs number of output neurons
     * @return initialized weight matrix [numOutputs][numInputs]
     */
    double[][] initializeWeights(int numInputs, int numOutputs);
    
    /**
     * Initializes bias vector for a layer.
     * 
     * @param numOutputs number of output neurons
     * @return initialized bias vector [numOutputs]
     */
    double[] initializeBiases(int numOutputs);
    
    /**
     * Gets the name of this initialization strategy.
     * 
     * @return the initializer name
     */
    String getName();
}

