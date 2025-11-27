package src.main.java.fl.operators;

/**
 * Interface for T-norm operators (triangular norms).
 * T-norms are used to model fuzzy AND operations.
 * They must satisfy: commutativity, associativity, monotonicity, and boundary conditions.
 */
public interface TNorm {
    

    double apply(double a, double b);

    String getName();
}

