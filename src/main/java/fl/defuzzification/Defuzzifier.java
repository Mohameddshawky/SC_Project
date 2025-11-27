package src.main.java.fl.defuzzification;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Interface for defuzzification methods.
 * Defuzzification converts a fuzzy set back to a crisp value.
 */
public interface Defuzzifier {
    

    double defuzzify(MembershipFunction membershipFunction, double minValue, double maxValue);
    String getName();
}

