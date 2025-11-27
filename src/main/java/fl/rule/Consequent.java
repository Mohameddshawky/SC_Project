package src.main.java.fl.rule;

/**
 * Represents the consequent (THEN part) of a fuzzy rule.
 * A consequent specifies the output variable and its fuzzy set.
 */
public class Consequent {
    
    private final String variableName;
    private final String fuzzySetName;
    

    public Consequent(String variableName, String fuzzySetName) {
        if (variableName == null || variableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty");
        }
        if (fuzzySetName == null || fuzzySetName.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuzzy set name cannot be null or empty");
        }
        this.variableName = variableName;
        this.fuzzySetName = fuzzySetName;
    }
    
    public String getVariableName() {
        return variableName;
    }
    
    public String getFuzzySetName() {
        return fuzzySetName;
    }
    
    @Override
    public String toString() {
        return String.format("%s IS %s", variableName, fuzzySetName);
    }
}

