package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Interface for implication operators used in fuzzy inference.
 * Implication applies the rule firing strength to the consequent membership function.
 */
public interface ImplicationOperator {
    

    MembershipFunction apply(double firingStrength, MembershipFunction consequentMF);
    String getName();
}

