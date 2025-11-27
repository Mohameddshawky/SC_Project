package src.main.java.fl.operators;

/**
 * Interface for S-norm operators (triangular conorms).
 * S-norms are used to model fuzzy OR operations.
 * They must satisfy: commutativity, associativity, monotonicity, and boundary conditions.
 */
public interface SNorm {
    

    double apply(double a, double b);

    String getName();
}

