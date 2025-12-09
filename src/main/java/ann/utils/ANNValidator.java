package src.main.java.ann.utils;

/**
 * Validation utilities for neural network inputs and parameters.
 */
public class ANNValidator {
    
    /**
     * Validates that a value is positive.
     * 
     * @param value value to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if not positive
     */
    public static void requirePositive(double value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive, got: " + value);
        }
    }
    
    /**
     * Validates that a value is non-negative.
     * 
     * @param value value to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if negative
     */
    public static void requireNonNegative(double value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must be non-negative, got: " + value);
        }
    }
    
    /**
     * Validates that a value is in a specified range.
     * 
     * @param value value to check
     * @param min minimum value (inclusive)
     * @param max maximum value (inclusive)
     * @param name parameter name for error message
     * @throws IllegalArgumentException if out of range
     */
    public static void requireInRange(double value, double min, double max, String name) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                String.format("%s must be in [%.2f, %.2f], got: %.2f", name, min, max, value));
        }
    }
    
    /**
     * Validates that an array is not null or empty.
     * 
     * @param array array to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if null or empty
     */
    public static void requireNonEmpty(double[] array, String name) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(name + " cannot be null or empty");
        }
    }
    
    /**
     * Validates that a 2D array is not null or empty.
     * 
     * @param array array to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if null or empty
     */
    public static void requireNonEmpty(double[][] array, String name) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(name + " cannot be null or empty");
        }
    }
    
    /**
     * Validates that arrays have matching dimensions.
     * 
     * @param array1 first array
     * @param array2 second array
     * @param name1 name of first array
     * @param name2 name of second array
     * @throws IllegalArgumentException if dimensions don't match
     */
    public static void requireSameLength(double[] array1, double[] array2, 
                                        String name1, String name2) {
        if (array1.length != array2.length) {
            throw new IllegalArgumentException(
                String.format("%s and %s must have same length: %d vs %d",
                              name1, name2, array1.length, array2.length));
        }
    }
    
    /**
     * Validates that a vector contains only finite values (no NaN or Infinity).
     * 
     * @param vector vector to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if contains invalid values
     */
    public static void requireFinite(double[] vector, String name) {
        for (int i = 0; i < vector.length; i++) {
            if (!Double.isFinite(vector[i])) {
                throw new IllegalArgumentException(
                    String.format("%s contains non-finite value at index %d: %.2f",
                                  name, i, vector[i]));
            }
        }
    }
    
    /**
     * Validates that a matrix contains only finite values.
     * 
     * @param matrix matrix to check
     * @param name parameter name for error message
     * @throws IllegalArgumentException if contains invalid values
     */
    public static void requireFinite(double[][] matrix, String name) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (!Double.isFinite(matrix[i][j])) {
                    throw new IllegalArgumentException(
                        String.format("%s contains non-finite value at [%d][%d]: %.2f",
                                      name, i, j, matrix[i][j]));
                }
            }
        }
    }
}

