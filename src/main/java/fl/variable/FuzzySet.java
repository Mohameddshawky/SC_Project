package src.main.java.fl.variable;

import src.main.java.fl.membership.MembershipFunction;

/**
 * Represents a fuzzy set with a name and a membership function.
 * A fuzzy set is a linguistic term (e.g., "Low", "Medium", "High") 
 * associated with a membership function that defines the degree of membership.
 */
public class FuzzySet {
    
    private final String name;
    private final MembershipFunction membershipFunction;
    

    public FuzzySet(String name, MembershipFunction membershipFunction) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuzzy set name cannot be null or empty");
        }
        if (membershipFunction == null) {
            throw new IllegalArgumentException("Membership function cannot be null");
        }
        this.name = name;
        this.membershipFunction = membershipFunction;
    }

    public String getName() {
        return name;
    }
    

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
    

    public double getMembership(double value) {
        return membershipFunction.getMembership(value);
    }
    
    @Override
    public String toString() {
        return String.format("FuzzySet[%s, %s]", name, membershipFunction);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FuzzySet fuzzySet = (FuzzySet) obj;
        return name.equals(fuzzySet.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

