package src.main.java.fl.defuzzification;

import src.main.java.fl.membership.MembershipFunction;


public class CentroidDefuzzifier implements Defuzzifier {
    
    private static final int DEFAULT_SAMPLES = 1000;
    private final int numSamples;
    
    public CentroidDefuzzifier() {
        this(DEFAULT_SAMPLES);
    }
    
    
    public CentroidDefuzzifier(int numSamples) {
        if (numSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }
        this.numSamples = numSamples;
    }
    
    @Override
    public double defuzzify(MembershipFunction membershipFunction, double minValue, double maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be less than maxValue");
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
        
        if (denominator == 0.0) {
            return (minValue + maxValue) / 2.0;
        }
        
        return numerator / denominator;
    }
    
    @Override
    public String getName() {
        return "Centroid";
    }
    
    @Override
    public String toString() {
        return String.format("CentroidDefuzzifier(samples=%d)", numSamples);
    }
}
