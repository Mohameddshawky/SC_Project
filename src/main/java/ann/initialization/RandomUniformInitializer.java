package src.main.java.ann.initialization;

import java.util.Random;

/**
 * Random Uniform weight initialization.
 * Initializes weights randomly from a uniform distribution in [-1, 1].
 * 
 * Simple but may not be optimal for deep networks.
 */
public class RandomUniformInitializer implements WeightInitializer {
    
    private final Random random;
    private final double minValue;
    private final double maxValue;
    
    /**
     * Creates a random uniform initializer with default range [-1, 1].
     */
    public RandomUniformInitializer() {
        this(-1.0, 1.0);
    }
    
    /**
     * Creates a random uniform initializer with custom range.
     * 
     * @param minValue minimum value
     * @param maxValue maximum value
     */
    public RandomUniformInitializer(double minValue, double maxValue) {
        this.random = new Random();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    /**
     * Creates a random uniform initializer with a specific seed.
     * 
     * @param minValue minimum value
     * @param maxValue maximum value
     * @param seed random seed for reproducibility
     */
    public RandomUniformInitializer(double minValue, double maxValue, long seed) {
        this.random = new Random(seed);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    @Override
    public double[][] initializeWeights(int numInputs, int numOutputs) {
        double[][] weights = new double[numOutputs][numInputs];
        
        for (int i = 0; i < numOutputs; i++) {
            for (int j = 0; j < numInputs; j++) {
                weights[i][j] = minValue + (maxValue - minValue) * random.nextDouble();
            }
        }
        
        return weights;
    }
    
    @Override
    public double[] initializeBiases(int numOutputs) {
        double[] biases = new double[numOutputs];
        
        for (int i = 0; i < numOutputs; i++) {
            biases[i] = minValue + (maxValue - minValue) * random.nextDouble();
        }
        
        return biases;
    }
    
    @Override
    public String getName() {
        return "Random Uniform";
    }
    
    @Override
    public String toString() {
        return String.format("RandomUniformInitializer[%.2f, %.2f]", minValue, maxValue);
    }
}

