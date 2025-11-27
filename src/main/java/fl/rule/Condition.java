package src.main.java.fl.rule;

/**
 * Represents a single condition in a fuzzy rule.
 * A condition is of the form "variable IS fuzzySet" (e.g., "Temperature IS High").
 */
public class Condition {
    
    private final String variableName;
    private final String fuzzySetName;
    private final boolean negated;
    

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

