package src.main.java.fl.inference;

import src.main.java.fl.membership.MembershipFunction;
import src.main.java.fl.operators.SNorm;
import src.main.java.fl.operators.TNorm;
import src.main.java.fl.rule.FuzzyRule;
import src.main.java.fl.rule.RuleBase;
import src.main.java.fl.variable.FuzzySet;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the Mamdani fuzzy inference engine.
 */
public class MamdaniInferenceEngine implements InferenceEngine<RuleBase> {

    private final TNorm tNorm;
    private final SNorm sNorm;
    private final ImplicationOperator implicationOperator;

    /**
     * Creates a Mamdani inference engine with specified operators.
     *
     * @param tNorm               the T-norm for AND operations
     * @param sNorm               the S-norm for OR and aggregation operations
     * @param implicationOperator the implication operator
     */
    public MamdaniInferenceEngine(TNorm tNorm, SNorm sNorm, ImplicationOperator implicationOperator) {
        this.tNorm = tNorm;
        this.sNorm = sNorm;
        this.implicationOperator = implicationOperator;
    }

    @Override
    public MamdaniInferenceResult infer(RuleBase ruleBase, Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs, Map<String, Double> crispInputs, Map<String, LinguisticVariable> allVariables) {
        Map<LinguisticVariable, MembershipFunction> aggregatedMFs = new HashMap<>();

        // Process each rule
        for (FuzzyRule rule : ruleBase.getEnabledRules()) {
            Map<String, FuzzyValue> stringFuzzifiedInputs = new HashMap<>();
            for(LinguisticVariable lv : fuzzifiedInputs.keySet()){
                stringFuzzifiedInputs.put(lv.getName(), fuzzifiedInputs.get(lv));
            }
            double firingStrength = rule.evaluate(stringFuzzifiedInputs, tNorm, sNorm);

            if (firingStrength > 0) {
                String outputVarName = rule.getConsequent().getVariableName();
                String outputSetName = rule.getConsequent().getFuzzySetName();

                // Find the output linguistic variable from the provided map
                LinguisticVariable outputVar = allVariables.get(outputVarName);
                if (outputVar == null) {
                    throw new IllegalArgumentException("Output variable '" + outputVarName + "' not found in the list of all variables.");
                }

                FuzzySet consequentSet = outputVar.getFuzzySet(outputSetName);
                if (consequentSet == null) {
                    throw new IllegalArgumentException("Fuzzy set '" + outputSetName + "' not found in variable '" + outputVarName + "'.");
                }

                // Apply implication
                MembershipFunction impliedMF = implicationOperator.apply(firingStrength, consequentSet.getMf());

                // Aggregate the results for this output variable
                aggregatedMFs.merge(outputVar, impliedMF, (current, implied) ->
                    x -> sNorm.apply(current.getValue(x), implied.getValue(x))
                );
            }
        }

        // Create the final fuzzy values for each output variable
        Map<LinguisticVariable, FuzzyValue> outputFuzzyValues = new HashMap<>();
        for (Map.Entry<LinguisticVariable, MembershipFunction> entry : aggregatedMFs.entrySet()) {
            LinguisticVariable outputVar = entry.getKey();
            MembershipFunction finalMF = entry.getValue();
            
            FuzzyValue resultFuzzyValue = new FuzzyValue();
            // Create a new fuzzy set that represents the aggregated result.
            // The defuzzifier will use this fuzzy set.
            FuzzySet aggregatedSet = new FuzzySet("aggregated_" + outputVar.getName(), finalMF);
            
            // The FuzzyValue for an output variable will contain a single entry:
            // the aggregated fuzzy set resulting from inference.
            resultFuzzyValue.setMembership(aggregatedSet.getName(), 1.0); // The membership here is trivial
            outputFuzzyValues.put(outputVar, resultFuzzyValue);
        }

        return new MamdaniInferenceResult(outputFuzzyValues);
    }
}
