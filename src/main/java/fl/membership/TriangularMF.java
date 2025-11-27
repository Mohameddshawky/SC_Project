package src.main.java.fl.membership;

/**
 * Triangular membership function defined by three parameters: a, b, c
 * where a <= b <= c
 * - a: left foot (where membership starts rising from 0)
 * - b: peak (where membership = 1)
 * - c: right foot (where membership returns to 0)
 */
public class TriangularMF implements MembershipFunction {
    
    private final double a;
    private final double b;
    private final double c;
    public TriangularMF(double a, double b, double c) {
        if (a > b || b > c) {
            throw new IllegalArgumentException(
                String.format("Invalid triangular parameters: a=%.2f, b=%.2f, c=%.2f. Must satisfy a <= b <= c", a, b, c)
            );
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public double getMembership(double x) {
        if (x <= a || x >= c) {
            return 0.0;
        } else if (x == b) {
            return 1.0;
        } else if (x > a && x < b) {
            // Rising slope
            return (x - a) / (b - a);
        } else {
            // Falling slope (x > b && x < c)
            return (c - x) / (c - b);
        }
    }
    
    @Override
    public double getMinSupport() {
        return a;
    }
    
    @Override
    public double getMaxSupport() {
        return c;
    }
    
    @Override
    public String getType() {
        return "Triangular";
    }
    
    public double getA() {
        return a;
    }
    
    public double getB() {
        return b;
    }
    
    public double getC() {
        return c;
    }
    
    @Override
    public String toString() {
        return String.format("TriangularMF(a=%.2f, b=%.2f, c=%.2f)", a, b, c);
    }
}

