package src.main.java.fl.operators;

/**
 * Interface for T-norm operators (triangular norms).
 * T-norms are used to model fuzzy AND operations.
 * They must satisfy: commutativity, associativity, monotonicity, and boundary conditions.
 */
public interface TNorm {
    
    /**
     * Applies the T-norm operator to two values.
     * 
     * @param a first value in [0, 1]
     * @param b second value in [0, 1]
     * @return the result of applying the T-norm, in [0, 1]
     */
    double apply(double a, double b);
    
    /**
     * Gets the name of this T-norm operator.
     * 
     * @return the operator name
     */
    String getName();
}

