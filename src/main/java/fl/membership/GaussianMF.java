package src.main.java.fl.membership;

/**
 * Gaussian membership function defined by two parameters: mean (μ) and sigma (σ)
 * The function is bell-shaped and theoretically extends from -∞ to +∞,
 * but for practical purposes we define support bounds.
 * 
 * Formula: μ(x) = e^(-(x-mean)²/(2*sigma²))
 */
public class GaussianMF implements MembershipFunction {
    
    private final double mean;
    private final double sigma;
    
    // For practical support bounds, consider membership < 0.01 as negligible
    // This occurs at approximately mean ± 3*sigma
    private static final double SIGMA_MULTIPLIER = 3.0;
    
    /**
     * Creates a Gaussian membership function.
     * 
     * @param mean the center of the bell curve (μ)
     * @param sigma the standard deviation (σ), must be positive
     * @throws IllegalArgumentException if sigma <= 0
     */
    public GaussianMF(double mean, double sigma) {
        if (sigma <= 0) {
            throw new IllegalArgumentException(
                String.format("Invalid Gaussian parameters: sigma=%.2f. Sigma must be positive", sigma)
            );
        }
        this.mean = mean;
        this.sigma = sigma;
    }
    
    @Override
    public double getMembership(double x) {
        double exponent = -Math.pow(x - mean, 2) / (2 * sigma * sigma);
        return Math.exp(exponent);
    }
    
    @Override
    public double getMinSupport() {
        return mean - SIGMA_MULTIPLIER * sigma;
    }
    
    @Override
    public double getMaxSupport() {
        return mean + SIGMA_MULTIPLIER * sigma;
    }
    
    @Override
    public String getType() {
        return "Gaussian";
    }
    
    public double getMean() {
        return mean;
    }
    
    public double getSigma() {
        return sigma;
    }
    
    @Override
    public String toString() {
        return String.format("GaussianMF(mean=%.2f, sigma=%.2f)", mean, sigma);
    }
}

