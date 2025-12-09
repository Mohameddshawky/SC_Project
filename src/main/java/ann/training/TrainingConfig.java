package src.main.java.ann.training;

/**
 * Training-specific configuration class.
 * Holds hyperparameters and training settings.
 */
public class TrainingConfig {
    
    private double learningRate;
    private int epochs;
    private int batchSize;
    private boolean shuffle;
    
    // Early stopping
    private boolean useEarlyStopping;
    private int patience;
    private double minDelta;
    
    // Verbose output
    private boolean verbose;
    private int printEveryNEpochs;
    
    /**
     * Creates training configuration with default values.
     */
    public TrainingConfig() {
        this.learningRate = 0.01;
        this.epochs = 100;
        this.batchSize = 32;
        this.shuffle = true;
        
        this.useEarlyStopping = false;
        this.patience = 10;
        this.minDelta = 0.001;
        
        this.verbose = true;
        this.printEveryNEpochs = 1;
    }
    
    // Getters and setters
    
    public double getLearningRate() {
        return learningRate;
    }
    
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
    
    public int getEpochs() {
        return epochs;
    }
    
    public void setEpochs(int epochs) {
        this.epochs = epochs;
    }
    
    public int getBatchSize() {
        return batchSize;
    }
    
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
    
    public boolean isShuffle() {
        return shuffle;
    }
    
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
    
    public boolean isUseEarlyStopping() {
        return useEarlyStopping;
    }
    
    public void setUseEarlyStopping(boolean useEarlyStopping) {
        this.useEarlyStopping = useEarlyStopping;
    }
    
    public int getPatience() {
        return patience;
    }
    
    public void setPatience(int patience) {
        this.patience = patience;
    }
    
    public double getMinDelta() {
        return minDelta;
    }
    
    public void setMinDelta(double minDelta) {
        this.minDelta = minDelta;
    }
    
    public boolean isVerbose() {
        return verbose;
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public int getPrintEveryNEpochs() {
        return printEveryNEpochs;
    }
    
    public void setPrintEveryNEpochs(int printEveryNEpochs) {
        this.printEveryNEpochs = printEveryNEpochs;
    }
}

