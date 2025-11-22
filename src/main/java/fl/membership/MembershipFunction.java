package src.main.java.fl.membership;

/**
 * Interface for fuzzy membership functions.
 * A membership function maps a crisp input value to a degree of membership in [0, 1].
 */
public interface MembershipFunction {
    
    /**
     * Computes the degree of membership for the given input value.
     * 
     * @param x the input value
     * @return the membership degree in the range [0, 1]
     */
    double getMembership(double x);
    
    /**
     * Gets the minimum value of the membership function's support (where membership > 0).
     * 
     * @return the minimum x value where membership is non-zero
     */
    double getMinSupport();
    
    /**
     * Gets the maximum value of the membership function's support (where membership > 0).
     * 
     * @return the maximum x value where membership is non-zero
     */
    double getMaxSupport();
    
    /**
     * Gets a string representation of the membership function type.
     * 
     * @return the name/type of the membership function
     */
    String getType();
}

