package src.main.java.fl.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a collection of Sugeno fuzzy rules.
 */
public class SugenoRuleBase {
    
    private final List<SugenoFuzzyRule> rules;
    
    /**
     * Creates an empty Sugeno rule base.
     */
    public SugenoRuleBase() {
        this.rules = new ArrayList<>();
    }
    
    /**
     * Adds a rule to the rule base.
     * 
     * @param rule the rule to add
     */
    public void addRule(SugenoFuzzyRule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }
        rules.add(rule);
    }
    
    /**
     * Gets all rules in the rule base.
     * 
     * @return list of all rules
     */
    public List<SugenoFuzzyRule> getAllRules() {
        return new ArrayList<>(rules);
    }
    
    /**
     * Gets only enabled rules.
     * 
     * @return list of enabled rules
     */
    public List<SugenoFuzzyRule> getEnabledRules() {
        return rules.stream()
            .filter(SugenoFuzzyRule::isEnabled)
            .collect(Collectors.toList());
    }
}
