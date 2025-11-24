package src.main.java.fl.inference;

import src.main.java.fl.rule.RuleBase;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;
import java.util.Map;

/**
 * Interface for a fuzzy inference engine.
 * @param <T> The type of rule base used by the engine (e.g., RuleBase, SugenoRuleBase).
 */
public interface InferenceEngine<T> {
    /**
     * Performs fuzzy inference based on a rule base and fuzzified inputs.
     *
     * @param ruleBase The rule base containing the fuzzy rules.
     * @param fuzzifiedInputs A map of linguistic variables to their corresponding fuzzy values.
     * @param crispInputs A map of input variable names to their crisp values (for Sugeno).
     * @param allVariables A map of all linguistic variable names to their instances.
     * @return The result of the inference process.
     */
    InferenceResult infer(T ruleBase, Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs, Map<String, Double> crispInputs, Map<String, LinguisticVariable> allVariables);
}
