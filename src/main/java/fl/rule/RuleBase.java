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
    
    /**
     * Adds a rule to the rule base.
     * 
     * @param rule the rule to add
     */
    public void addRule(FuzzyRule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }
        rules.add(rule);
    }
    
    /**
     * Removes a rule from the rule base.
     * 
     * @param rule the rule to remove
     * @return true if removed, false if not found
     */
    public boolean removeRule(FuzzyRule rule) {
        return rules.remove(rule);
    }
    
    /**
     * Removes a rule at the specified index.
     * 
     * @param index the index of the rule to remove
     * @return the removed rule
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public FuzzyRule removeRule(int index) {
        return rules.remove(index);
    }
    
    /**
     * Gets a rule at the specified index.
     * 
     * @param index the index of the rule
     * @return the rule at the index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public FuzzyRule getRule(int index) {
        return rules.get(index);
    }
    
    /**
     * Gets all rules in the rule base.
     * 
     * @return list of all rules
     */
    public List<FuzzyRule> getAllRules() {
        return new ArrayList<>(rules);
    }
    
    /**
     * Gets only enabled rules.
     * 
     * @return list of enabled rules
     */
    public List<FuzzyRule> getEnabledRules() {
        return rules.stream()
            .filter(FuzzyRule::isEnabled)
            .collect(Collectors.toList());
    }
    
    /**
     * Gets the number of rules in the rule base.
     * 
     * @return the rule count
     */
    public int getRuleCount() {
        return rules.size();
    }
    
    /**
     * Gets the number of enabled rules.
     * 
     * @return the enabled rule count
     */
    public int getEnabledRuleCount() {
        return (int) rules.stream().filter(FuzzyRule::isEnabled).count();
    }
    
    /**
     * Clears all rules from the rule base.
     */
    public void clear() {
        rules.clear();
    }
    
    /**
     * Checks if the rule base is empty.
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return rules.isEmpty();
    }
    
    /**
     * Enables all rules in the rule base.
     */
    public void enableAllRules() {
        for (FuzzyRule rule : rules) {
            rule.setEnabled(true);
        }
    }
    
    /**
     * Disables all rules in the rule base.
     */
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

