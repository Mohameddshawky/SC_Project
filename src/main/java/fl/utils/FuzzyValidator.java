package src.main.java.fl.utils;

import src.main.java.fl.variable.LinguisticVariable;

/**
 * Utility class for validating fuzzy logic inputs and parameters.
 */
public class FuzzyValidator {
    
    /**
     * Validates that a value is within the range [0, 1].
     * 
     * @param value the value to validate
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if value is not in [0, 1]
     */
    public static void validateMembershipDegree(double value, String paramName) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(
                String.format("%s must be in [0, 1], got: %.4f", paramName, value)
            );
        }
    }
    
    /**
     * Validates that a value is within a domain.
     * 
     * @param value the value to validate
     * @param minValue the minimum domain value
     * @param maxValue the maximum domain value
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if value is outside the domain
     */
    public static void validateDomain(double value, double minValue, double maxValue, String paramName) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException(
                String.format("%s=%.2f is outside domain [%.2f, %.2f]", 
                    paramName, value, minValue, maxValue)
            );
        }
    }
    
    /**
     * Clamps a value to the domain [minValue, maxValue].
     * 
     * @param value the value to clamp
     * @param minValue the minimum domain value
     * @param maxValue the maximum domain value
     * @return the clamped value
     */
    public static double clampToDomain(double value, double minValue, double maxValue) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        }
        return value;
    }
    
    /**
     * Validates that a string is not null or empty.
     * 
     * @param value the string to validate
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if string is null or empty
     */
    public static void validateNotEmpty(String value, String paramName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(paramName + " cannot be null or empty");
        }
    }
    
    /**
     * Validates that an object is not null.
     * 
     * @param value the object to validate
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if object is null
     */
    public static void validateNotNull(Object value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException(paramName + " cannot be null");
        }
    }
    
    /**
     * Validates that a value is positive.
     * 
     * @param value the value to validate
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if value is not positive
     */
    public static void validatePositive(double value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                String.format("%s must be positive, got: %.4f", paramName, value)
            );
        }
    }
    
    /**
     * Validates that a value is non-negative.
     * 
     * @param value the value to validate
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if value is negative
     */
    public static void validateNonNegative(double value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(
                String.format("%s must be non-negative, got: %.4f", paramName, value)
            );
        }
    }
    
    /**
     * Validates that minValue < maxValue.
     * 
     * @param minValue the minimum value
     * @param maxValue the maximum value
     * @param paramName the parameter name for error messages
     * @throws IllegalArgumentException if minValue >= maxValue
     */
    public static void validateMinMax(double minValue, double maxValue, String paramName) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException(
                String.format("%s: minValue=%.2f must be less than maxValue=%.2f", 
                    paramName, minValue, maxValue)
            );
        }
    }
    
    /**
     * Validates and clamps an input value to a linguistic variable's domain.
     * 
     * @param value the input value
     * @param variable the linguistic variable
     * @return the clamped value
     */
    public static double validateAndClampInput(double value, LinguisticVariable variable) {
        return clampToDomain(value, variable.getMinValue(), variable.getMaxValue());
    }
}

