package src.main.java.fl.operators;

/**
 * Interface for S-norm operators (triangular conorms).
 * S-norms are used to model fuzzy OR operations.
 * They must satisfy: commutativity, associativity, monotonicity, and boundary conditions.
 */
public interface SNorm {
    
    /**
     * Applies the S-norm operator to two values.
     * 
     * @param a first value in [0, 1]
     * @param b second value in [0, 1]
     * @return the result of applying the S-norm, in [0, 1]
     */
    double apply(double a, double b);
    
    /**
     * Gets the name of this S-norm operator.
     * 
     * @return the operator name
     */
    String getName();
}

