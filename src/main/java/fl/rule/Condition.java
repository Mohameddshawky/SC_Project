package src.main.java.fl.rule;

/**
 * Represents a single condition in a fuzzy rule.
 * A condition is of the form "variable IS fuzzySet" (e.g., "Temperature IS High").
 */
public class Condition {
    
    private final String variableName;
    private final String fuzzySetName;
    private final boolean negated;
    
    /**
     * Creates a condition.
     * 
     * @param variableName the name of the linguistic variable
     * @param fuzzySetName the name of the fuzzy set
     * @param negated whether this condition is negated (NOT)
     */
    public Condition(String variableName, String fuzzySetName, boolean negated) {
        if (variableName == null || variableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty");
        }
        if (fuzzySetName == null || fuzzySetName.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuzzy set name cannot be null or empty");
        }
        this.variableName = variableName;
        this.fuzzySetName = fuzzySetName;
        this.negated = negated;
    }
    
    /**
     * Creates a non-negated condition.
     * 
     * @param variableName the name of the linguistic variable
     * @param fuzzySetName the name of the fuzzy set
     */
    public Condition(String variableName, String fuzzySetName) {
        this(variableName, fuzzySetName, false);
    }
    
    public String getVariableName() {
        return variableName;
    }
    
    public String getFuzzySetName() {
        return fuzzySetName;
    }
    
    public boolean isNegated() {
        return negated;
    }
    
    /**
     * Evaluates this condition given a membership degree.
     * If negated, returns 1 - membership.
     * 
     * @param membership the membership degree
     * @return the evaluated membership degree
     */
    public double evaluate(double membership) {
        return negated ? (1.0 - membership) : membership;
    }
    
    @Override
    public String toString() {
        if (negated) {
            return String.format("NOT(%s IS %s)", variableName, fuzzySetName);
        }
        return String.format("%s IS %s", variableName, fuzzySetName);
    }
}

