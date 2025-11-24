package src.main.java.fl.inference;

import java.util.List;
import java.util.Map;

/**
 * The result of a Sugeno inference process.
 * It contains the firing strengths and the output levels for each rule, grouped by output variable.
 */
public class SugenoInferenceResult implements InferenceResult {

    private final Map<String, List<Double>> firingStrengths;
    private final Map<String, List<Double>> ruleOutputs;

    public SugenoInferenceResult(Map<String, List<Double>> firingStrengths, Map<String, List<Double>> ruleOutputs) {
        this.firingStrengths = firingStrengths;
        this.ruleOutputs = ruleOutputs;
    }

    public Map<String, List<Double>> getFiringStrengths() {
        return firingStrengths;
    }

    public Map<String, List<Double>> getRuleOutputs() {
        return ruleOutputs;
    }
}
