package src.main.java.fl.variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a linguistic variable with a domain and multiple fuzzy sets.
 * A linguistic variable is a variable whose values are words or sentences 
 * in natural language (e.g., temperature with values "Low", "Medium", "High").
 */
public class LinguisticVariable {
    
    private final String name;
    private final double minValue;
    private final double maxValue;
    private final Map<String, FuzzySet> fuzzySets;
    
    /**
     * Creates a linguistic variable.
     * 
     * @param name the name of the variable (e.g., "Temperature")
     * @param minValue the minimum value of the domain
     * @param maxValue the maximum value of the domain
     * @throws IllegalArgumentException if name is null/empty or minValue >= maxValue
     */
    public LinguisticVariable(String name, double minValue, double maxValue) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty");
        }
        if (minValue >= maxValue) {
            throw new IllegalArgumentException(
                String.format("Invalid domain: minValue=%.2f must be less than maxValue=%.2f", 
                    minValue, maxValue)
            );
        }
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fuzzySets = new HashMap<>();
    }
    
    /**
     * Adds a fuzzy set to this linguistic variable.
     * 
     * @param fuzzySet the fuzzy set to add
     * @throws IllegalArgumentException if a fuzzy set with the same name already exists
     */
    public void addFuzzySet(FuzzySet fuzzySet) {
        if (fuzzySets.containsKey(fuzzySet.getName())) {
            throw new IllegalArgumentException(
                String.format("Fuzzy set '%s' already exists in variable '%s'", 
                    fuzzySet.getName(), name)
            );
        }
        fuzzySets.put(fuzzySet.getName(), fuzzySet);
    }
    
    /**
     * Removes a fuzzy set from this linguistic variable.
     * 
     * @param fuzzySetName the name of the fuzzy set to remove
     * @return true if removed, false if not found
     */
    public boolean removeFuzzySet(String fuzzySetName) {
        return fuzzySets.remove(fuzzySetName) != null;
    }
    
    /**
     * Gets a fuzzy set by name.
     * 
     * @param fuzzySetName the name of the fuzzy set
     * @return the fuzzy set, or null if not found
     */
    public FuzzySet getFuzzySet(String fuzzySetName) {
        return fuzzySets.get(fuzzySetName);
    }
    
    /**
     * Gets all fuzzy sets of this variable.
     * 
     * @return list of all fuzzy sets
     */
    public List<FuzzySet> getAllFuzzySets() {
        return new ArrayList<>(fuzzySets.values());
    }
    
    /**
     * Gets the names of all fuzzy sets.
     * 
     * @return list of fuzzy set names
     */
    public List<String> getFuzzySetNames() {
        return new ArrayList<>(fuzzySets.keySet());
    }
    
    /**
     * Checks if a fuzzy set exists in this variable.
     * 
     * @param fuzzySetName the name of the fuzzy set
     * @return true if exists, false otherwise
     */
    public boolean hasFuzzySet(String fuzzySetName) {
        return fuzzySets.containsKey(fuzzySetName);
    }
    
    /**
     * Fuzzifies a crisp input value into a fuzzy value.
     * Computes the membership degree for each fuzzy set.
     * 
     * @param crispValue the crisp input value
     * @return the fuzzified value
     */
    public FuzzyValue fuzzify(double crispValue) {
        // Clamp the value to the domain
        double clampedValue = clampToDomain(crispValue);
        
        FuzzyValue fuzzyValue = new FuzzyValue();
        for (FuzzySet fuzzySet : fuzzySets.values()) {
            double membership = fuzzySet.getMembership(clampedValue);
            fuzzyValue.setMembership(fuzzySet.getName(), membership);
        }
        return fuzzyValue;
    }
    
    /**
     * Clamps a value to the domain [minValue, maxValue].
     * 
     * @param value the value to clamp
     * @return the clamped value
     */
    public double clampToDomain(double value) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        }
        return value;
    }
    
    /**
     * Validates if a value is within the domain.
     * 
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public boolean isInDomain(double value) {
        return value >= minValue && value <= maxValue;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMinValue() {
        return minValue;
    }
    
    public double getMaxValue() {
        return maxValue;
    }
    
    public int getFuzzySetCount() {
        return fuzzySets.size();
    }
    
    @Override
    public String toString() {
        return String.format("LinguisticVariable[%s, domain=[%.2f, %.2f], sets=%s]", 
            name, minValue, maxValue, fuzzySets.keySet());
    }
}

