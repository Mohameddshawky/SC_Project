package src.main.java.ann.initialization;

import java.util.Random;

/**
 * Xavier (Glorot) weight initialization.
 * 
 * Designed for sigmoid and tanh activation functions.
 * Helps maintain variance of activations across layers.
 * 
 * Formula: weights ~ Uniform(-√(6/(n_in + n_out)), √(6/(n_in + n_out)))
 * Alternative (Gaussian): weights ~ N(0, √(2/(n_in + n_out)))
 * 
 * Reference: Glorot & Bengio, 2010
 */
public class XavierInitializer implements WeightInitializer {
    
    private final Random random;
    private final boolean useGaussian;
    
    /**
     * Creates Xavier initializer with uniform distribution (default).
     */
    public XavierInitializer() {
        this(false);
    }
    
    /**
     * Creates Xavier initializer.
     * 
     * @param useGaussian if true, use Gaussian distribution; otherwise uniform
     */
    public XavierInitializer(boolean useGaussian) {
        this.random = new Random();
        this.useGaussian = useGaussian;
    }
    
    /**
     * Creates Xavier initializer with a specific seed.
     * 
     * @param useGaussian if true, use Gaussian distribution; otherwise uniform
     * @param seed random seed for reproducibility
     */
    public XavierInitializer(boolean useGaussian, long seed) {
        this.random = new Random(seed);
        this.useGaussian = useGaussian;
    }
    
    @Override
    public double[][] initializeWeights(int numInputs, int numOutputs) {
        double[][] weights = new double[numOutputs][numInputs];
        
        if (useGaussian) {
            // Gaussian variant: stddev = sqrt(2 / (n_in + n_out))
            double stddev = Math.sqrt(2.0 / (numInputs + numOutputs));
            
            for (int i = 0; i < numOutputs; i++) {
                for (int j = 0; j < numInputs; j++) {
                    weights[i][j] = random.nextGaussian() * stddev;
                }
            }
        } else {
            // Uniform variant: range = sqrt(6 / (n_in + n_out))
            double limit = Math.sqrt(6.0 / (numInputs + numOutputs));
            
            for (int i = 0; i < numOutputs; i++) {
                for (int j = 0; j < numInputs; j++) {
                    weights[i][j] = -limit + 2.0 * limit * random.nextDouble();
                }
            }
        }
        
        return weights;
    }
    
    @Override
    public double[] initializeBiases(int numOutputs) {
        // Biases typically initialized to zero in Xavier
        double[] biases = new double[numOutputs];
        // Arrays in Java are initialized to 0 by default
        return biases;
    }
    
    @Override
    public String getName() {
        return "Xavier" + (useGaussian ? " (Gaussian)" : " (Uniform)");
    }
    
    @Override
    public String toString() {
        return "XavierInitializer[" + (useGaussian ? "Gaussian" : "Uniform") + "]";
    }
}

