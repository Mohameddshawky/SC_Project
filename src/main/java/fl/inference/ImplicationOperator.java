package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Interface for implication operators used in fuzzy inference.
 * Implication applies the rule firing strength to the consequent membership function.
 */
public interface ImplicationOperator {
    
    /**
     * Applies implication to a membership function.
     * 
     * @param firingStrength the rule firing strength in [0, 1]
     * @param consequentMF the consequent membership function
     * @return a new membership function after applying implication
     */
    MembershipFunction apply(double firingStrength, MembershipFunction consequentMF);
    
    /**
     * Gets the name of this implication operator.
     * 
     * @return the operator name
     */
    String getName();
}

