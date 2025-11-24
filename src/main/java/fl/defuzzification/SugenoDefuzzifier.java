package src.main.java.fl.defuzzification;

import java.util.List;

/**
 * Interface for Sugeno defuzzification.
 * Sugeno methods operate on crisp rule outputs and firing strengths.
 */
public interface SugenoDefuzzifier {

    /**
     * Defuzzifies one Sugeno output variable.
     *
     * @param ruleOutputs      crisp outputs of fired rules
     * @param firingStrengths  corresponding firing strengths
     * @return final crisp output value
     */
    double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths);

    /**
     * @return the method name
     */
    String getName();
}
