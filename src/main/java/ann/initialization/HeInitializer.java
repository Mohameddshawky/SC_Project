package src.main.java.ann.initialization;

import java.util.Random;

/**
 * He (Kaiming) weight initialization.
 * 
 * Designed specifically for ReLU activation functions.
 * Addresses the dying ReLU problem by maintaining variance.
 * 
 * Formula: weights ~ N(0, √(2/n_in))
 * 
 * Where n_in is the number of input units.
 * 
 * Reference: He et al., 2015
 */
public class HeInitializer implements WeightInitializer {
    
    private final Random random;
    
    /**
     * Creates He initializer.
     */
    public HeInitializer() {
        this.random = new Random();
    }
    
    /**
     * Creates He initializer with a specific seed.
     * 
     * @param seed random seed for reproducibility
     */
    public HeInitializer(long seed) {
        this.random = new Random(seed);
    }
    
    @Override
    public double[][] initializeWeights(int numInputs, int numOutputs) {
        double[][] weights = new double[numOutputs][numInputs];
        
        // He initialization: stddev = sqrt(2 / n_in)
        double stddev = Math.sqrt(2.0 / numInputs);
        
        for (int i = 0; i < numOutputs; i++) {
            for (int j = 0; j < numInputs; j++) {
                weights[i][j] = random.nextGaussian() * stddev;
            }
        }
        
        return weights;
    }
    
    @Override
    public double[] initializeBiases(int numOutputs) {
        // Biases typically initialized to zero in He initialization
        double[] biases = new double[numOutputs];
        // Arrays in Java are initialized to 0 by default
        return biases;
    }
    
    @Override
    public String getName() {
        return "He";
    }
    
    @Override
    public String toString() {
        return "HeInitializer";
    }
}

