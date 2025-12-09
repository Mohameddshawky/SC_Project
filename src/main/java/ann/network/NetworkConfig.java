package src.main.java.ann.network;

import src.main.java.ann.loss.LossFunction;
import src.main.java.ann.loss.MSELoss;

/**
 * Configuration class for neural network settings.
 * Contains default values and hyperparameters.
 */
public class NetworkConfig {
    
    // Training hyperparameters
    private double learningRate;
    private int epochs;
    private int batchSize;
    
    // Loss function
    private LossFunction lossFunction;
    
    // Early stopping
    private boolean useEarlyStopping;
    private int earlyStoppingPatience;
    private double earlyStoppingMinDelta;
    
    // Validation split
    private double validationSplit;
    
    // Verbose output
    private boolean verbose;
    
    /**
     * Creates a configuration with default values.
     */
    public NetworkConfig() {
        // Default hyperparameters
        this.learningRate = 0.01;
        this.epochs = 100;
        this.batchSize = 32;
        
        // Default loss function
        this.lossFunction = new MSELoss();
        
        // Default early stopping settings
        this.useEarlyStopping = false;
        this.earlyStoppingPatience = 10;
        this.earlyStoppingMinDelta = 0.001;
        
        // Default validation split
        this.validationSplit = 0.0;
        
        // Default verbosity
        this.verbose = true;
    }
    
    // Getters and setters
    
    public double getLearningRate() {
        return learningRate;
    }
    
    public void setLearningRate(double learningRate) {
        if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be positive");
        }
        this.learningRate = learningRate;
    }
    
    public int getEpochs() {
        return epochs;
    }
    
    public void setEpochs(int epochs) {
        if (epochs <= 0) {
            throw new IllegalArgumentException("Epochs must be positive");
        }
        this.epochs = epochs;
    }
    
    public int getBatchSize() {
        return batchSize;
    }
    
    public void setBatchSize(int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be positive");
        }
        this.batchSize = batchSize;
    }
    
    public LossFunction getLossFunction() {
        return lossFunction;
    }
    
    public void setLossFunction(LossFunction lossFunction) {
        if (lossFunction == null) {
            throw new IllegalArgumentException("Loss function cannot be null");
        }
        this.lossFunction = lossFunction;
    }
    
    public boolean isUseEarlyStopping() {
        return useEarlyStopping;
    }
    
    public void setUseEarlyStopping(boolean useEarlyStopping) {
        this.useEarlyStopping = useEarlyStopping;
    }
    
    public int getEarlyStoppingPatience() {
        return earlyStoppingPatience;
    }
    
    public void setEarlyStoppingPatience(int earlyStoppingPatience) {
        if (earlyStoppingPatience <= 0) {
            throw new IllegalArgumentException("Patience must be positive");
        }
        this.earlyStoppingPatience = earlyStoppingPatience;
    }
    
    public double getEarlyStoppingMinDelta() {
        return earlyStoppingMinDelta;
    }
    
    public void setEarlyStoppingMinDelta(double earlyStoppingMinDelta) {
        if (earlyStoppingMinDelta < 0) {
            throw new IllegalArgumentException("Min delta must be non-negative");
        }
        this.earlyStoppingMinDelta = earlyStoppingMinDelta;
    }
    
    public double getValidationSplit() {
        return validationSplit;
    }
    
    public void setValidationSplit(double validationSplit) {
        if (validationSplit < 0 || validationSplit >= 1) {
            throw new IllegalArgumentException("Validation split must be in [0, 1)");
        }
        this.validationSplit = validationSplit;
    }
    
    public boolean isVerbose() {
        return verbose;
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    @Override
    public String toString() {
        return String.format("NetworkConfig[lr=%.4f, epochs=%d, batchSize=%d, loss=%s]",
                             learningRate, epochs, batchSize, lossFunction.getName());
    }
}

