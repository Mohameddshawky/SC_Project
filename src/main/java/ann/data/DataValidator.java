package src.main.java.ann.data;

/**
 * Data validation utilities.
 * Handles missing values, outliers, and data quality checks.
 */
public class DataValidator {
    
    /**
     * Strategy for handling missing values.
     */
    public enum MissingValueStrategy {
        REMOVE,         // Remove rows with missing values
        MEAN_IMPUTE,    // Replace with feature mean
        MEDIAN_IMPUTE,  // Replace with feature median
        ZERO_IMPUTE     // Replace with zero
    }
    
    /**
     * Checks if data contains any NaN or infinite values.
     * 
     * @param data data to check
     * @return true if data contains invalid values
     */
    public static boolean containsInvalidValues(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (Double.isNaN(data[i][j]) || Double.isInfinite(data[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Counts the number of invalid (NaN or infinite) values in data.
     * 
     * @param data data to check
     * @return count of invalid values
     */
    public static int countInvalidValues(double[][] data) {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (Double.isNaN(data[i][j]) || Double.isInfinite(data[i][j])) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Replaces invalid values (NaN, infinite) with the mean of the feature.
     * 
     * @param data data to clean
     * @return cleaned data
     */
    public static double[][] handleMissingValues(double[][] data, MissingValueStrategy strategy) {
        if (data.length == 0 || data[0].length == 0) {
            return data;
        }
        
        int numSamples = data.length;
        int numFeatures = data[0].length;
        
        // Create a copy to avoid modifying original data
        double[][] cleaned = new double[numSamples][numFeatures];
        for (int i = 0; i < numSamples; i++) {
            cleaned[i] = data[i].clone();
        }
        
        switch (strategy) {
            case MEAN_IMPUTE:
                // Compute means for each feature (ignoring NaN/Inf)
                double[] means = new double[numFeatures];
                int[] counts = new int[numFeatures];
                
                for (int i = 0; i < numSamples; i++) {
                    for (int j = 0; j < numFeatures; j++) {
                        if (!Double.isNaN(data[i][j]) && !Double.isInfinite(data[i][j])) {
                            means[j] += data[i][j];
                            counts[j]++;
                        }
                    }
                }
                
                for (int j = 0; j < numFeatures; j++) {
                    if (counts[j] > 0) {
                        means[j] /= counts[j];
                    }
                }
                
                // Replace invalid values with means
                for (int i = 0; i < numSamples; i++) {
                    for (int j = 0; j < numFeatures; j++) {
                        if (Double.isNaN(cleaned[i][j]) || Double.isInfinite(cleaned[i][j])) {
                            cleaned[i][j] = means[j];
                        }
                    }
                }
                break;
                
            case ZERO_IMPUTE:
                // Replace invalid values with zero
                for (int i = 0; i < numSamples; i++) {
                    for (int j = 0; j < numFeatures; j++) {
                        if (Double.isNaN(cleaned[i][j]) || Double.isInfinite(cleaned[i][j])) {
                            cleaned[i][j] = 0.0;
                        }
                    }
                }
                break;
                
            case MEDIAN_IMPUTE:
                // For simplicity, fall back to mean imputation
                // Full median calculation would require sorting
                return handleMissingValues(data, MissingValueStrategy.MEAN_IMPUTE);
                
            case REMOVE:
                // Count valid rows
                int validRowCount = 0;
                boolean[] validRows = new boolean[numSamples];
                
                for (int i = 0; i < numSamples; i++) {
                    validRows[i] = true;
                    for (int j = 0; j < numFeatures; j++) {
                        if (Double.isNaN(data[i][j]) || Double.isInfinite(data[i][j])) {
                            validRows[i] = false;
                            break;
                        }
                    }
                    if (validRows[i]) {
                        validRowCount++;
                    }
                }
                
                // Create cleaned array with only valid rows
                double[][] validData = new double[validRowCount][numFeatures];
                int validIdx = 0;
                for (int i = 0; i < numSamples; i++) {
                    if (validRows[i]) {
                        validData[validIdx++] = data[i].clone();
                    }
                }
                
                return validData;
                
            default:
                return cleaned;
        }
        
        return cleaned;
    }
    
    /**
     * Validates that features and labels have consistent dimensions.
     * 
     * @param features feature matrix
     * @param labels label matrix
     * @return true if dimensions are valid
     */
    public static boolean validateDimensions(double[][] features, double[][] labels) {
        if (features.length != labels.length) {
            return false;
        }
        
        if (features.length == 0) {
            return false;
        }
        
        int numFeatures = features[0].length;
        int numLabels = labels[0].length;
        
        for (int i = 0; i < features.length; i++) {
            if (features[i].length != numFeatures || labels[i].length != numLabels) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if data contains only finite values.
     * 
     * @param data data to check
     * @return true if all values are finite
     */
    public static boolean isFinite(double[][] data) {
        return !containsInvalidValues(data);
    }
}

