package src.main.java.fl.defuzzification;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Interface for defuzzification methods.
 * Defuzzification converts a fuzzy set back to a crisp value.
 */
public interface Defuzzifier {
    
    /**
     * Defuzzifies a fuzzy membership function to a crisp value.
     * 
     * @param membershipFunction the aggregated output membership function
     * @param minValue the minimum value of the output domain
     * @param maxValue the maximum value of the output domain
     * @return the defuzzified crisp value
     */
    double defuzzify(MembershipFunction membershipFunction, double minValue, double maxValue);
    
    /**
     * Gets the name of this defuzzification method.
     * 
     * @return the method name
     */
    String getName();
}

