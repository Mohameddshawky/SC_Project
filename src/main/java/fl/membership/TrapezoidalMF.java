package src.main.java.fl.membership;

/**
 * Trapezoidal membership function defined by four parameters: a, b, c, d
 * where a <= b <= c <= d
 * - a: left foot (where membership starts rising from 0)
 * - b: left shoulder (where membership reaches 1)
 * - c: right shoulder (where membership starts falling from 1)
 * - d: right foot (where membership returns to 0)
 */
public class TrapezoidalMF implements MembershipFunction {
    
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidalMF(double a, double b, double c, double d) {
        if (a > b || b > c || c > d) {
            throw new IllegalArgumentException(
                String.format("Invalid trapezoidal parameters: a=%.2f, b=%.2f, c=%.2f, d=%.2f. Must satisfy a <= b <= c <= d", 
                    a, b, c, d)
            );
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    @Override
    public double getMembership(double x) {
        if (x <= a || x >= d) {
            return 0.0;
        } else if (x >= b && x <= c) {
            return 1.0;
        } else if (x > a && x < b) {
            // Rising slope
            return (x - a) / (b - a);
        } else {
            // Falling slope (x > c && x < d)
            return (d - x) / (d - c);
        }
    }
    
    @Override
    public double getMinSupport() {
        return a;
    }
    
    @Override
    public double getMaxSupport() {
        return d;
    }
    
    @Override
    public String getType() {
        return "Trapezoidal";
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
    
    public double getD() {
        return d;
    }
    
    @Override
    public String toString() {
        return String.format("TrapezoidalMF(a=%.2f, b=%.2f, c=%.2f, d=%.2f)", a, b, c, d);
    }
}

