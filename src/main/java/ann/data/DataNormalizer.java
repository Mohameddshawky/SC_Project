package src.main.java.ann.data;

/**
 * Data normalization utilities.
 * Provides various normalization strategies for neural network inputs.
 */
public class DataNormalizer {
    
    /**
     * Normalization strategy enum.
     */
    public enum Strategy {
        MIN_MAX,    // Scale to [0, 1]
        Z_SCORE,    // Standardize to mean=0, std=1
        MEAN_NORM   // Normalize to [-1, 1] using mean and range
    }
    
    private Strategy strategy;
    private double[] mins;
    private double[] maxs;
    private double[] means;
    private double[] stds;
    
    /**
     * Creates a normalizer with the specified strategy.
     * 
     * @param strategy normalization strategy
     */
    public DataNormalizer(Strategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Fits the normalizer to the training data.
     * Computes statistics needed for normalization.
     * 
     * @param data training data [numSamples][numFeatures]
     */
    public void fit(double[][] data) {
        if (data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Data cannot be empty");
        }
        
        int numSamples = data.length;
        int numFeatures = data[0].length;
        
        mins = new double[numFeatures];
        maxs = new double[numFeatures];
        means = new double[numFeatures];
        stds = new double[numFeatures];
        
        // Initialize mins and maxs
        for (int j = 0; j < numFeatures; j++) {
            mins[j] = Double.MAX_VALUE;
            maxs[j] = -Double.MAX_VALUE;
        }
        
        // Compute mins, maxs, and means
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numFeatures; j++) {
                double value = data[i][j];
                mins[j] = Math.min(mins[j], value);
                maxs[j] = Math.max(maxs[j], value);
                means[j] += value;
            }
        }
        
        // Finalize means
        for (int j = 0; j < numFeatures; j++) {
            means[j] /= numSamples;
        }
        
        // Compute standard deviations
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numFeatures; j++) {
                double diff = data[i][j] - means[j];
                stds[j] += diff * diff;
            }
        }
        
        for (int j = 0; j < numFeatures; j++) {
            stds[j] = Math.sqrt(stds[j] / numSamples);
            // Prevent division by zero
            if (stds[j] == 0) {
                stds[j] = 1.0;
            }
        }
    }
    
    /**
     * Transforms data using the fitted statistics.
     * 
     * @param data data to transform [numSamples][numFeatures]
     * @return normalized data
     */
    public double[][] transform(double[][] data) {
        if (mins == null || maxs == null || means == null || stds == null) {
            throw new IllegalStateException("Normalizer must be fitted before transformation");
        }
        
        int numSamples = data.length;
        int numFeatures = data[0].length;
        
        if (numFeatures != mins.length) {
            throw new IllegalArgumentException(
                String.format("Feature count mismatch: expected %d, got %d",
                              mins.length, numFeatures));
        }
        
        double[][] normalized = new double[numSamples][numFeatures];
        
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numFeatures; j++) {
                normalized[i][j] = normalizeValue(data[i][j], j);
            }
        }
        
        return normalized;
    }
    
    /**
     * Fits and transforms data in one step.
     * 
     * @param data data to fit and transform
     * @return normalized data
     */
    public double[][] fitTransform(double[][] data) {
        fit(data);
        return transform(data);
    }
    
    /**
     * Normalizes a single value for a specific feature.
     * 
     * @param value the value to normalize
     * @param featureIndex the feature index
     * @return normalized value
     */
    private double normalizeValue(double value, int featureIndex) {
        switch (strategy) {
            case MIN_MAX:
                // Scale to [0, 1]: (x - min) / (max - min)
                double range = maxs[featureIndex] - mins[featureIndex];
                if (range == 0) {
                    return 0.5; // Default to middle if no variation
                }
                return (value - mins[featureIndex]) / range;
                
            case Z_SCORE:
                // Standardize: (x - mean) / std
                return (value - means[featureIndex]) / stds[featureIndex];
                
            case MEAN_NORM:
                // Normalize to [-1, 1]: (x - mean) / range
                double meanRange = maxs[featureIndex] - mins[featureIndex];
                if (meanRange == 0) {
                    return 0.0;
                }
                return (value - means[featureIndex]) / meanRange;
                
            default:
                return value;
        }
    }
    
    /**
     * Gets the normalization strategy.
     * 
     * @return strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }
    
    /**
     * Gets the computed minimum values per feature.
     * 
     * @return minimum values
     */
    public double[] getMins() {
        return mins;
    }
    
    /**
     * Gets the computed maximum values per feature.
     * 
     * @return maximum values
     */
    public double[] getMaxs() {
        return maxs;
    }
    
    /**
     * Gets the computed mean values per feature.
     * 
     * @return mean values
     */
    public double[] getMeans() {
        return means;
    }
    
    /**
     * Gets the computed standard deviation values per feature.
     * 
     * @return standard deviation values
     */
    public double[] getStds() {
        return stds;
    }
}

