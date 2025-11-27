package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;

import java.util.Map;

/**
 * The result of a Mamdani inference process.
 * It contains the aggregated fuzzy values for each output variable
 * and the aggregated membership functions for defuzzification.
 */
public class MamdaniInferenceResult implements InferenceResult {

    private final Map<LinguisticVariable, FuzzyValue> fuzzyValues;
    private final Map<LinguisticVariable, MembershipFunction> aggregatedMFs;

    public MamdaniInferenceResult(Map<LinguisticVariable, FuzzyValue> fuzzyValues,
                                   Map<LinguisticVariable, MembershipFunction> aggregatedMFs) {
        this.fuzzyValues = fuzzyValues;
        this.aggregatedMFs = aggregatedMFs;
    }

    public Map<LinguisticVariable, FuzzyValue> getFuzzyValues() {
        return fuzzyValues;
    }

    public Map<LinguisticVariable, MembershipFunction> getAggregatedMembershipFunctions() {
        return aggregatedMFs;
    }
}
