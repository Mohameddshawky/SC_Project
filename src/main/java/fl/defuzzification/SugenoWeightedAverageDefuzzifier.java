package src.main.java.fl.defuzzification;

import java.util.List;

/**
 * Sugeno Weighted Average defuzzifier.
 *
 * Formula:
 *      output = Σ(w_i * z_i) / Σ(w_i)
 *
 * where:
 *   w_i = rule firing strength
 *   z_i = crisp rule output
 */
public class SugenoWeightedAverageDefuzzifier implements SugenoDefuzzifier {

    @Override
    public double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths) {

        if (ruleOutputs == null || firingStrengths == null) {
            throw new IllegalArgumentException("Lists cannot be null");
        }

        if (ruleOutputs.size() != firingStrengths.size()) {
            throw new IllegalArgumentException("Outputs and strengths must have same size");
        }

        if (ruleOutputs.isEmpty()) {
            throw new IllegalArgumentException("No rules fired for this output variable");
        }

        double numerator = 0.0;
        double denominator = 0.0;

        for (int i = 0; i < ruleOutputs.size(); i++) {
            double w = firingStrengths.get(i);
            numerator += w * ruleOutputs.get(i);
            denominator += w;
        }

        if (denominator == 0.0) {
            return 0.0; // no rule fired
        }

        return numerator / denominator;
    }

    @Override
    public String getName() {
        return "Sugeno Weighted Average";
    }

    @Override
    public String toString() {
        return "SugenoWeightedAverageDefuzzifier";
    }
}
