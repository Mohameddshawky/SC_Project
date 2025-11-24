package src.main.java.fl.inference;

import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;

import java.util.Map;

/**
 * The result of a Mamdani inference process.
 * It contains the aggregated fuzzy values for each output variable.
 */
public class MamdaniInferenceResult implements InferenceResult {

    private final Map<LinguisticVariable, FuzzyValue> fuzzyValues;

    public MamdaniInferenceResult(Map<LinguisticVariable, FuzzyValue> fuzzyValues) {
        this.fuzzyValues = fuzzyValues;
    }

    public Map<LinguisticVariable, FuzzyValue> getFuzzyValues() {
        return fuzzyValues;
    }
}
