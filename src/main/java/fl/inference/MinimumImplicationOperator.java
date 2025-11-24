package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Implements minimum-based implication (clipping).
 * The consequent membership function is clipped at the level of the firing strength.
 */
public class MinimumImplicationOperator implements ImplicationOperator {

    @Override
    public MembershipFunction apply(double firingStrength, MembershipFunction consequentMF) {
        return x -> Math.min(firingStrength, consequentMF.getValue(x));
    }

    @Override
    public String getName() {
        return "Minimum (Clipping)";
    }
}
