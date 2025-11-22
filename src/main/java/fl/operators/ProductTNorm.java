package src.main.java.fl.operators;

/**
 * Product T-norm operator: a * b
 * Also known as algebraic product.
 */
public class ProductTNorm implements TNorm {
    
    @Override
    public double apply(double a, double b) {
        return a * b;
    }
    
    @Override
    public String getName() {
        return "Product";
    }
    
    @Override
    public String toString() {
        return "ProductTNorm";
    }
}

