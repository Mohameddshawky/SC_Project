package src.main.java.fl.utils;

import src.main.java.fl.variable.LinguisticVariable;

/**
 * Utility class for validating fuzzy logic inputs and parameters.
 */
public class FuzzyValidator {

    public static void validateMembershipDegree(double value, String paramName) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(
                String.format("%s must be in [0, 1], got: %.4f", paramName, value)
            );
        }
    }
    

    public static void validateDomain(double value, double minValue, double maxValue, String paramName) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException(
                String.format("%s=%.2f is outside domain [%.2f, %.2f]", 
                    paramName, value, minValue, maxValue)
            );
        }
    }
    

    public static double clampToDomain(double value, double minValue, double maxValue) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static void validateNotEmpty(String value, String paramName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(paramName + " cannot be null or empty");
        }
    }
    

    public static void validateNotNull(Object value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException(paramName + " cannot be null");
        }
    }
    

    public static void validatePositive(double value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                String.format("%s must be positive, got: %.4f", paramName, value)
            );
        }
    }
    

    public static void validateNonNegative(double value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(
                String.format("%s must be non-negative, got: %.4f", paramName, value)
            );
        }
    }
    

    public static void validateMinMax(double minValue, double maxValue, String paramName) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException(
                String.format("%s: minValue=%.2f must be less than maxValue=%.2f", 
                    paramName, minValue, maxValue)
            );
        }
    }
    

    public static double validateAndClampInput(double value, LinguisticVariable variable) {
        return clampToDomain(value, variable.getMinValue(), variable.getMaxValue());
    }
}

