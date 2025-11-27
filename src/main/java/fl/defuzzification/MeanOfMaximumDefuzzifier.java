package src.main.java.fl.defuzzification;

import src.main.java.fl.membership.MembershipFunction;



/**
 * Defuzzification using the Mean of Maximum (MoM) method.
 *
 * Steps:
 * 1. Sample the membership function over the domain.
 * 2. Find the maximum membership value.
 * 3. Collect all x values having membership == max.
 * 4. Return the arithmetic mean of these x values.
 */
public class MeanOfMaximumDefuzzifier implements Defuzzifier {

    private static final int DEFAULT_SAMPLES = 1000;
    private final int numSamples;

    public MeanOfMaximumDefuzzifier() {
        this(DEFAULT_SAMPLES);
    }

    public MeanOfMaximumDefuzzifier(int numSamples) {
        if (numSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }
        this.numSamples = numSamples;
    }

    @Override
    public double defuzzify(MembershipFunction membershipFunction, double minValue, double maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be less than maxValue");
        }

        double stepSize = (maxValue - minValue) / numSamples;

        double maxMembership = 0.0;

        // First pass: find maximum membership
        for (int i = 0; i < numSamples; i++) {
            double x = minValue + (i + 0.5) * stepSize;
            double membership = membershipFunction.getMembership(x);

            if (membership > maxMembership) {
                maxMembership = membership;
            }
        }

        // if maxMembership is zero , means in middle point
        if (maxMembership == 0.0) {
            return (minValue + maxValue) / 2.0;
        }

        // Second pass: collect all x with membership == maxMembership
        double sum = 0.0;
        int count = 0;

        // Use small tolerance for floating comparison
        final double EPS = 1e-9;

        for (int i = 0; i < numSamples; i++) {
            double x = minValue + (i + 0.5) * stepSize;
            double membership = membershipFunction.getMembership(x);

            if (Math.abs(membership - maxMembership) < EPS) {
                sum += x;
                count++;
            }
        }

        if (count == 0) {
            // Should not happen, but fallback to center
            return (minValue + maxValue) / 2.0;
        }

        return sum / count;
    }

    @Override
    public String getName() {
        return "Mean of Maximum";
    }

    @Override
    public String toString() {
        return String.format("MeanOfMaximumDefuzzifier(samples=%d)", numSamples);
    }
}
