package src.main.java.fl.inference;

import src.main.java.fl.rule.RuleBase;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;
import java.util.Map;

/**
 * Interface for a fuzzy inference engine.
 */
public interface InferenceEngine<T> {
    InferenceResult infer(T ruleBase, Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs, Map<String, Double> crispInputs, Map<String, LinguisticVariable> allVariables);
}
