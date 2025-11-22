package src.main.java.fl.rule;

import src.main.java.fl.operators.TNorm;
import src.main.java.fl.operators.SNorm;
import src.main.java.fl.variable.FuzzyValue;

import java.util.Map;

/**
 * Represents a fuzzy IF-THEN rule.
 * A rule has an antecedent (IF part), a consequent (THEN part), 
 * and optional properties like weight and enabled status.
 */
public class FuzzyRule {
    
    private final Antecedent antecedent;
    private final Consequent consequent;
    private double weight;
    private boolean enabled;
    
    /**
     * Creates a fuzzy rule.
     * 
     * @param antecedent the IF part of the rule
     * @param consequent the THEN part of the rule
     */
    public FuzzyRule(Antecedent antecedent, Consequent consequent) {
        this(antecedent, consequent, 1.0, true);
    }
    
    /**
     * Creates a fuzzy rule with weight and enabled status.
     * 
     * @param antecedent the IF part of the rule
     * @param consequent the THEN part of the rule
     * @param weight the rule weight (default 1.0)
     * @param enabled whether the rule is enabled (default true)
     */
    public FuzzyRule(Antecedent antecedent, Consequent consequent, double weight, boolean enabled) {
        if (antecedent == null) {
            throw new IllegalArgumentException("Antecedent cannot be null");
        }
        if (consequent == null) {
            throw new IllegalArgumentException("Consequent cannot be null");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Rule weight must be in [0, 1]");
        }
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.weight = weight;
        this.enabled = enabled;
    }
    
    /**
     * Evaluates the rule given fuzzified inputs.
     * Returns the firing strength (truth value) of the rule.
     * 
     * @param fuzzifiedInputs map of variable names to their fuzzy values
     * @param tNorm the T-norm operator for AND
     * @param sNorm the S-norm operator for OR
     * @return the firing strength in [0, 1], or 0.0 if disabled
     */
    public double evaluate(Map<String, FuzzyValue> fuzzifiedInputs, TNorm tNorm, SNorm sNorm) {
        if (!enabled) {
            return 0.0;
        }
        
        double truthValue = antecedent.evaluate(fuzzifiedInputs, tNorm, sNorm);
        return truthValue * weight;
    }
    
    public Antecedent getAntecedent() {
        return antecedent;
    }
    
    public Consequent getConsequent() {
        return consequent;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Rule weight must be in [0, 1]");
        }
        this.weight = weight;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        String status = enabled ? "" : " [DISABLED]";
        String weightStr = (weight != 1.0) ? String.format(" (weight=%.2f)", weight) : "";
        return String.format("IF %s THEN %s%s%s", antecedent, consequent, weightStr, status);
    }
}

