package src.main.java.fl.utils;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Utility class for mathematical operations used in fuzzy logic.
 */
public class FuzzyMath {
    
    private static final double EPSILON = 1e-10;
    
    /**
     * Computes the centroid of a membership function using numerical integration.
     * 
     * Formula: x* = ∫μ(x)·x dx / ∫μ(x) dx
     * 
     * @param membershipFunction the membership function
     * @param minValue the minimum value of the domain
     * @param maxValue the maximum value of the domain
     * @param numSamples the number of samples for integration
     * @return the centroid value
     */
    public static double computeCentroid(MembershipFunction membershipFunction, 
                                        double minValue, double maxValue, 
                                        int numSamples) {
        if (numSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }
        
        double stepSize = (maxValue - minValue) / numSamples;
        double numerator = 0.0;
        double denominator = 0.0;
        
        for (int i = 0; i < numSamples; i++) {
            double x = minValue + (i + 0.5) * stepSize;
            double membership = membershipFunction.getMembership(x);
            
            numerator += membership * x;
            denominator += membership;
        }
        
        if (Math.abs(denominator) < EPSILON) {
            return (minValue + maxValue) / 2.0;
        }
        
        return numerator / denominator;
    }
    
    /**
     * Computes the area under a membership function using numerical integration.
     * 
     * @param membershipFunction the membership function
     * @param minValue the minimum value of the domain
     * @param maxValue the maximum value of the domain
     * @param numSamples the number of samples for integration
     * @return the area
     */
    public static double computeArea(MembershipFunction membershipFunction,
                                    double minValue, double maxValue,
                                    int numSamples) {
        if (numSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }
        
        double stepSize = (maxValue - minValue) / numSamples;
        double area = 0.0;
        
        for (int i = 0; i < numSamples; i++) {
            double x = minValue + (i + 0.5) * stepSize;
            double membership = membershipFunction.getMembership(x);
            area += membership * stepSize;
        }
        
        return area;
    }
    
    /**
     * Finds the maximum membership value in a domain.
     * 
     * @param membershipFunction the membership function
     * @param minValue the minimum value of the domain
     * @param maxValue the maximum value of the domain
     * @param numSamples the number of samples to check
     * @return the maximum membership value
     */
    public static double findMaxMembership(MembershipFunction membershipFunction,
                                          double minValue, double maxValue,
                                          int numSamples) {
        if (numSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }
        
        double stepSize = (maxValue - minValue) / numSamples;
        double maxMembership = 0.0;
        
        for (int i = 0; i < numSamples; i++) {
            double x = minValue + (i + 0.5) * stepSize;
            double membership = membershipFunction.getMembership(x);
            if (membership > maxMembership) {
                maxMembership = membership;
            }
        }
        
        return maxMembership;
    }
    
    /**
     * Computes the fuzzy NOT operation (complement).
     * 
     * @param membership the membership degree
     * @return 1 - membership
     */
    public static double fuzzyNot(double membership) {
        return 1.0 - membership;
    }
    
    /**
     * Computes the minimum of multiple values.
     * 
     * @param values the values
     * @return the minimum value
     */
    public static double min(double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value is required");
        }
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }
    
    /**
     * Computes the maximum of multiple values.
     * 
     * @param values the values
     * @return the maximum value
     */
    public static double max(double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value is required");
        }
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }
    
    /**
     * Checks if two values are approximately equal within epsilon.
     * 
     * @param a first value
     * @param b second value
     * @return true if approximately equal
     */
    public static boolean approximatelyEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
    
    /**
     * Clamps a value to the range [0, 1].
     * 
     * @param value the value to clamp
     * @return the clamped value
     */
    public static double clampMembership(double value) {
        if (value < 0.0) return 0.0;
        if (value > 1.0) return 1.0;
        return value;
    }
}

