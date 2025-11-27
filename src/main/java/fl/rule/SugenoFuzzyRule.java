package src.main.java.fl.rule;

import src.main.java.fl.operators.SNorm;
import src.main.java.fl.operators.TNorm;
import src.main.java.fl.variable.FuzzyValue;

import java.util.Map;

/**
 * Represents a Sugeno fuzzy IF-THEN rule.
 * The antecedent is a standard fuzzy proposition, but the consequent is a function.
 */
public class SugenoFuzzyRule {

    private final Antecedent antecedent;
    private final SugenoConsequent consequent;
    private double weight;
    private boolean enabled;


    public SugenoFuzzyRule(Antecedent antecedent, SugenoConsequent consequent) {
        this(antecedent, consequent, 1.0, true);
    }


    public SugenoFuzzyRule(Antecedent antecedent, SugenoConsequent consequent, double weight, boolean enabled) {
        if (antecedent == null) {
            throw new IllegalArgumentException("Antecedent cannot be null");
        }
        if (consequent == null) {
            throw new IllegalArgumentException("SugenoConsequent cannot be null");
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Rule weight must be in [0, 1]");
        }
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.weight = weight;
        this.enabled = enabled;
    }


    public double getFiringStrength(Map<String, FuzzyValue> fuzzifiedInputs, TNorm tNorm, SNorm sNorm) {
        if (!enabled) {
            return 0.0;
        }
        double truthValue = antecedent.evaluate(fuzzifiedInputs, tNorm, sNorm);
        return truthValue * weight;
    }

    public Antecedent getAntecedent() {
        return antecedent;
    }

    public SugenoConsequent getConsequent() {
        return consequent;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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
