package src.main.java.ann.data;

/**
 * Dataset class holding features and labels.
 * Provides convenient access to training data.
 */
public class Dataset {
    
    private double[][] features;
    private double[][] labels;
    
    /**
     * Creates a dataset with features and labels.
     * 
     * @param features input feature matrix [numSamples][numFeatures]
     * @param labels target label matrix [numSamples][numOutputs]
     */
    public Dataset(double[][] features, double[][] labels) {
        if (features.length != labels.length) {
            throw new IllegalArgumentException(
                String.format("Feature and label counts must match: features=%d, labels=%d",
                              features.length, labels.length));
        }
        
        this.features = features;
        this.labels = labels;
    }
    
    /**
     * Gets the features matrix.
     * 
     * @return features
     */
    public double[][] getFeatures() {
        return features;
    }
    
    /**
     * Gets the labels matrix.
     * 
     * @return labels
     */
    public double[][] getLabels() {
        return labels;
    }
    
    /**
     * Gets the number of samples in the dataset.
     * 
     * @return number of samples
     */
    public int getSize() {
        return features.length;
    }
    
    /**
     * Gets the number of features per sample.
     * 
     * @return number of features
     */
    public int getNumFeatures() {
        return features.length > 0 ? features[0].length : 0;
    }
    
    /**
     * Gets the number of output labels per sample.
     * 
     * @return number of labels
     */
    public int getNumLabels() {
        return labels.length > 0 ? labels[0].length : 0;
    }
    
    /**
     * Gets a specific sample (feature and label).
     * 
     * @param index sample index
     * @return array containing [feature, label]
     */
    public double[][] getSample(int index) {
        return new double[][] { features[index], labels[index] };
    }
    
    /**
     * Gets a subset of the dataset.
     * 
     * @param startIdx start index (inclusive)
     * @param endIdx end index (exclusive)
     * @return new dataset containing the subset
     */
    public Dataset getSubset(int startIdx, int endIdx) {
        int subsetSize = endIdx - startIdx;
        double[][] subsetFeatures = new double[subsetSize][];
        double[][] subsetLabels = new double[subsetSize][];
        
        for (int i = 0; i < subsetSize; i++) {
            subsetFeatures[i] = features[startIdx + i];
            subsetLabels[i] = labels[startIdx + i];
        }
        
        return new Dataset(subsetFeatures, subsetLabels);
    }
    
    @Override
    public String toString() {
        return String.format("Dataset[samples=%d, features=%d, labels=%d]",
                             getSize(), getNumFeatures(), getNumLabels());
    }
}

