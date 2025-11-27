package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;

import java.util.function.DoubleUnaryOperator;

/**
 * A wrapper class for creating derived membership functions from existing ones.
 * This is used in inference operations where we need to create new membership functions
 * based on transformations of existing ones (e.g., implication, aggregation).
 */
public class DerivedMembershipFunction implements MembershipFunction {
    
    private final DoubleUnaryOperator membershipCalculator;
    private final MembershipFunction baseMF;
    private final String type;

    public DerivedMembershipFunction(DoubleUnaryOperator membershipCalculator, 
                                     MembershipFunction baseMF, 
                                     String type) {
        this.membershipCalculator = membershipCalculator;
        this.baseMF = baseMF;
        this.type = type;
    }
    
    @Override
    public double getMembership(double x) {
        return membershipCalculator.applyAsDouble(x);
    }
    
    @Override
    public double getMinSupport() {
        return baseMF.getMinSupport();
    }
    
    @Override
    public double getMaxSupport() {
        return baseMF.getMaxSupport();
    }
    
    @Override
    public String getType() {
        return type;
    }
}
