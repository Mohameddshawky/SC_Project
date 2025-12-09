package src.main.java.ann.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Utility class for splitting datasets into train/test or train/validation/test sets.
 */
public class DataSplitter {
    
    private Random random;
    
    /**
     * Creates a data splitter with default random seed.
     */
    public DataSplitter() {
        this.random = new Random();
    }
    
    /**
     * Creates a data splitter with a specific seed for reproducibility.
     * 
     * @param seed random seed
     */
    public DataSplitter(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Splits data into train and test sets.
     * 
     * @param dataset the dataset to split
     * @param trainRatio ratio of data to use for training (e.g., 0.8 for 80%)
     * @param shuffle whether to shuffle data before splitting
     * @return array containing [trainDataset, testDataset]
     */
    public Dataset[] trainTestSplit(Dataset dataset, double trainRatio, boolean shuffle) {
        if (trainRatio <= 0 || trainRatio >= 1) {
            throw new IllegalArgumentException("Train ratio must be in (0, 1)");
        }
        
        int totalSize = dataset.getSize();
        int trainSize = (int) (totalSize * trainRatio);
        
        double[][] features = dataset.getFeatures();
        double[][] labels = dataset.getLabels();
        
        // Create indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalSize; i++) {
            indices.add(i);
        }
        
        // Shuffle if requested
        if (shuffle) {
            Collections.shuffle(indices, random);
        }
        
        // Split data
        double[][] trainFeatures = new double[trainSize][];
        double[][] trainLabels = new double[trainSize][];
        double[][] testFeatures = new double[totalSize - trainSize][];
        double[][] testLabels = new double[totalSize - trainSize][];
        
        for (int i = 0; i < trainSize; i++) {
            int idx = indices.get(i);
            trainFeatures[i] = features[idx];
            trainLabels[i] = labels[idx];
        }
        
        for (int i = trainSize; i < totalSize; i++) {
            int idx = indices.get(i);
            testFeatures[i - trainSize] = features[idx];
            testLabels[i - trainSize] = labels[idx];
        }
        
        return new Dataset[] {
            new Dataset(trainFeatures, trainLabels),
            new Dataset(testFeatures, testLabels)
        };
    }
    
    /**
     * Splits data into train, validation, and test sets.
     * 
     * @param dataset the dataset to split
     * @param trainRatio ratio of data for training
     * @param valRatio ratio of data for validation
     * @param shuffle whether to shuffle data before splitting
     * @return array containing [trainDataset, valDataset, testDataset]
     */
    public Dataset[] trainValTestSplit(Dataset dataset, double trainRatio, 
                                       double valRatio, boolean shuffle) {
        if (trainRatio <= 0 || valRatio <= 0 || trainRatio + valRatio >= 1) {
            throw new IllegalArgumentException(
                "Train and validation ratios must be positive and sum to less than 1");
        }
        
        int totalSize = dataset.getSize();
        int trainSize = (int) (totalSize * trainRatio);
        int valSize = (int) (totalSize * valRatio);
        int testSize = totalSize - trainSize - valSize;
        
        double[][] features = dataset.getFeatures();
        double[][] labels = dataset.getLabels();
        
        // Create indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalSize; i++) {
            indices.add(i);
        }
        
        // Shuffle if requested
        if (shuffle) {
            Collections.shuffle(indices, random);
        }
        
        // Split data
        double[][] trainFeatures = new double[trainSize][];
        double[][] trainLabels = new double[trainSize][];
        double[][] valFeatures = new double[valSize][];
        double[][] valLabels = new double[valSize][];
        double[][] testFeatures = new double[testSize][];
        double[][] testLabels = new double[testSize][];
        
        // Training set
        for (int i = 0; i < trainSize; i++) {
            int idx = indices.get(i);
            trainFeatures[i] = features[idx];
            trainLabels[i] = labels[idx];
        }
        
        // Validation set
        for (int i = 0; i < valSize; i++) {
            int idx = indices.get(trainSize + i);
            valFeatures[i] = features[idx];
            valLabels[i] = labels[idx];
        }
        
        // Test set
        for (int i = 0; i < testSize; i++) {
            int idx = indices.get(trainSize + valSize + i);
            testFeatures[i] = features[idx];
            testLabels[i] = labels[idx];
        }
        
        return new Dataset[] {
            new Dataset(trainFeatures, trainLabels),
            new Dataset(valFeatures, valLabels),
            new Dataset(testFeatures, testLabels)
        };
    }
}

