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
    

    public void addFuzzySet(FuzzySet fuzzySet) {
        if (fuzzySets.containsKey(fuzzySet.getName())) {
            throw new IllegalArgumentException(
                String.format("Fuzzy set '%s' already exists in variable '%s'", 
                    fuzzySet.getName(), name)
            );
        }
        fuzzySets.put(fuzzySet.getName(), fuzzySet);
    }

    public boolean removeFuzzySet(String fuzzySetName) {
        return fuzzySets.remove(fuzzySetName) != null;
    }
    

    public FuzzySet getFuzzySet(String fuzzySetName) {
        return fuzzySets.get(fuzzySetName);
    }
    

    public List<FuzzySet> getAllFuzzySets() {
        return new ArrayList<>(fuzzySets.values());
    }
    

    public List<String> getFuzzySetNames() {
        return new ArrayList<>(fuzzySets.keySet());
    }
    

    public boolean hasFuzzySet(String fuzzySetName) {
        return fuzzySets.containsKey(fuzzySetName);
    }

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
    

    public double clampToDomain(double value) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        }
        return value;
    }
    

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

