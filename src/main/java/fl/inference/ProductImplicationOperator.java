package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Implements product-based implication (scaling).
 * The consequent membership function is scaled by the firing strength.
 */
public class ProductImplicationOperator implements ImplicationOperator {

    @Override
    public MembershipFunction apply(double firingStrength, MembershipFunction consequentMF) {
        return x -> firingStrength * consequentMF.getValue(x);
    }

    @Override
    public String getName() {
        return "Product (Scaling)";
    }
}
