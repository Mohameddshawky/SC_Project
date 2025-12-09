package src.main.java.ann.utils;

/**
 * Mathematical utility functions for neural networks.
 */
public class ANNMath {
    
    /**
     * Computes the dot product of two vectors.
     * 
     * @param a first vector
     * @param b second vector
     * @return dot product
     */
    public static double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have same length");
        }
        
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        
        return sum;
    }
    
    /**
     * Computes the L2 norm (Euclidean norm) of a vector.
     * 
     * @param vector input vector
     * @return L2 norm
     */
    public static double l2Norm(double[] vector) {
        double sum = 0.0;
        for (double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }
    
    /**
     * Normalizes a vector to unit length.
     * 
     * @param vector vector to normalize
     * @return normalized vector
     */
    public static double[] normalize(double[] vector) {
        double norm = l2Norm(vector);
        
        if (norm == 0) {
            return vector.clone();
        }
        
        double[] normalized = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = vector[i] / norm;
        }
        
        return normalized;
    }
    
    /**
     * Clips values in a vector to a specified range.
     * 
     * @param vector input vector
     * @param min minimum value
     * @param max maximum value
     * @return clipped vector
     */
    public static double[] clip(double[] vector, double min, double max) {
        double[] clipped = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++) {
            clipped[i] = Math.max(min, Math.min(max, vector[i]));
        }
        
        return clipped;
    }
    
    /**
     * Adds two vectors element-wise.
     * 
     * @param a first vector
     * @param b second vector
     * @return sum vector
     */
    public static double[] add(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have same length");
        }
        
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
        
        return result;
    }
    
    /**
     * Subtracts two vectors element-wise.
     * 
     * @param a first vector
     * @param b second vector
     * @return difference vector
     */
    public static double[] subtract(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have same length");
        }
        
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        
        return result;
    }
    
    /**
     * Multiplies a vector by a scalar.
     * 
     * @param vector input vector
     * @param scalar scalar value
     * @return scaled vector
     */
    public static double[] scale(double[] vector, double scalar) {
        double[] result = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * scalar;
        }
        
        return result;
    }
    
    /**
     * Applies softmax function to a vector.
     * Used for multi-class classification output.
     * 
     * @param vector input vector
     * @return softmax output
     */
    public static double[] softmax(double[] vector) {
        // Subtract max for numerical stability
        double max = vector[0];
        for (int i = 1; i < vector.length; i++) {
            max = Math.max(max, vector[i]);
        }
        
        double[] exp = new double[vector.length];
        double sum = 0.0;
        
        for (int i = 0; i < vector.length; i++) {
            exp[i] = Math.exp(vector[i] - max);
            sum += exp[i];
        }
        
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = exp[i] / sum;
        }
        
        return result;
    }
    
    /**
     * Computes the mean of a vector.
     * 
     * @param vector input vector
     * @return mean value
     */
    public static double mean(double[] vector) {
        double sum = 0.0;
        for (double v : vector) {
            sum += v;
        }
        return sum / vector.length;
    }
    
    /**
     * Computes the standard deviation of a vector.
     * 
     * @param vector input vector
     * @return standard deviation
     */
    public static double std(double[] vector) {
        double mean = mean(vector);
        double variance = 0.0;
        
        for (double v : vector) {
            double diff = v - mean;
            variance += diff * diff;
        }
        
        return Math.sqrt(variance / vector.length);
    }
}

