package src.main.java.fl.inference;

import src.main.java.fl.operators.SNorm;
import src.main.java.fl.operators.TNorm;
import src.main.java.fl.rule.SugenoFuzzyRule;
import src.main.java.fl.rule.SugenoRuleBase;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the Sugeno fuzzy inference engine.
 */
public class SugenoInferenceEngine implements InferenceEngine<SugenoRuleBase> {

    private final TNorm tNorm;
    private final SNorm sNorm;

    public SugenoInferenceEngine(TNorm tNorm, SNorm sNorm) {
        this.tNorm = tNorm;
        this.sNorm = sNorm;
    }

    @Override
    public SugenoInferenceResult infer(SugenoRuleBase ruleBase, Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs, Map<String, Double> crispInputs, Map<String, LinguisticVariable> allVariables) {
        Map<String, List<Double>> firingStrengths = new HashMap<>();
        Map<String, List<Double>> ruleOutputs = new HashMap<>();

        // Process each rule
        for (SugenoFuzzyRule rule : ruleBase.getEnabledRules()) {
             Map<String, FuzzyValue> stringFuzzifiedInputs = new HashMap<>();
            for(LinguisticVariable lv : fuzzifiedInputs.keySet()){
                stringFuzzifiedInputs.put(lv.getName(), fuzzifiedInputs.get(lv));
            }
            double strength = rule.getFiringStrength(stringFuzzifiedInputs, tNorm, sNorm);

            if (strength > 0) {
                String outputVarName = rule.getConsequent().getOutputVariable();
                double outputValue = rule.getConsequent().evaluate(crispInputs);

                firingStrengths.computeIfAbsent(outputVarName, k -> new ArrayList<>()).add(strength);
                ruleOutputs.computeIfAbsent(outputVarName, k -> new ArrayList<>()).add(outputValue);
            }
        }

        return new SugenoInferenceResult(firingStrengths, ruleOutputs);
    }
}
