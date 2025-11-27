package src.main.java.fl.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a collection of fuzzy rules.
 * Provides CRUD operations for managing rules.
 */
public class RuleBase {
    
    private final List<FuzzyRule> rules;
    
    /**
     * Creates an empty rule base.
     */
    public RuleBase() {
        this.rules = new ArrayList<>();
    }
    

    public void addRule(FuzzyRule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }
        rules.add(rule);
    }
    

    public boolean removeRule(FuzzyRule rule) {
        return rules.remove(rule);
    }

    public FuzzyRule removeRule(int index) {
        return rules.remove(index);
    }
    

    public FuzzyRule getRule(int index) {
        return rules.get(index);
    }
    

    public List<FuzzyRule> getAllRules() {
        return new ArrayList<>(rules);
    }
    

    public List<FuzzyRule> getEnabledRules() {
        return rules.stream()
            .filter(FuzzyRule::isEnabled)
            .collect(Collectors.toList());
    }
    

    public int getRuleCount() {
        return rules.size();
    }
    

    public int getEnabledRuleCount() {
        return (int) rules.stream().filter(FuzzyRule::isEnabled).count();
    }
    

    public void clear() {
        rules.clear();
    }

    public boolean isEmpty() {
        return rules.isEmpty();
    }
    

    public void enableAllRules() {
        for (FuzzyRule rule : rules) {
            rule.setEnabled(true);
        }
    }
    

    public void disableAllRules() {
        for (FuzzyRule rule : rules) {
            rule.setEnabled(false);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RuleBase with " + rules.size() + " rules:\n");
        for (int i = 0; i < rules.size(); i++) {
            sb.append(String.format("[%d] %s\n", i, rules.get(i)));
        }
        return sb.toString();
    }
}

