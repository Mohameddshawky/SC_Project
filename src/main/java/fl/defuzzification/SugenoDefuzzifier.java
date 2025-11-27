package src.main.java.fl.defuzzification;

import java.util.List;

/**
 * Interface for Sugeno defuzzification.
 * Sugeno methods operate on crisp rule outputs and firing strengths.
 */
public interface SugenoDefuzzifier {

    double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths);

    String getName();
}
