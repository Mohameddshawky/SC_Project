package src.main.java.fl.membership;

/**
 * Interface for fuzzy membership functions.
 * A membership function maps a crisp input value to a degree of membership in [0, 1].
 */
public interface MembershipFunction {
    
    /**
     * Computes the degree of membership for the given input value.
     *
     */
    double getMembership(double x);
    

    double getMinSupport();
    
    /**
     * Gets the maximum value of the membership function's support (where membership > 0).
     */
    double getMaxSupport();
    
    /**
     * Gets a string representation of the membership function type.
     */
    String getType();
}

