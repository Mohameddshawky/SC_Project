package src.main.java.fl.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a collection of Sugeno fuzzy rules.
 */
public class SugenoRuleBase {
    
    private final List<SugenoFuzzyRule> rules;
    

    public SugenoRuleBase() {
        this.rules = new ArrayList<>();
    }
    

    public void addRule(SugenoFuzzyRule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }
        rules.add(rule);
    }
    

    public List<SugenoFuzzyRule> getAllRules() {
        return new ArrayList<>(rules);
    }

    public List<SugenoFuzzyRule> getEnabledRules() {
        return rules.stream()
            .filter(SugenoFuzzyRule::isEnabled)
            .collect(Collectors.toList());
    }
}
