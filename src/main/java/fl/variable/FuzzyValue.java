package src.main.java.fl.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a fuzzified value - a mapping from fuzzy set names to membership degrees.
 * For example, a temperature of 25°C might have:
 * - "Low": 0.2
 * - "Medium": 0.7
 * - "High": 0.1
 */
public class FuzzyValue {
    
    private final Map<String, Double> memberships;
    
    /**
     * Creates an empty fuzzy value.
     */
    public FuzzyValue() {
        this.memberships = new HashMap<>();
    }
    
    /**
     * Creates a fuzzy value with initial memberships.
     * 
     * @param memberships map of fuzzy set names to membership degrees
     */
    public FuzzyValue(Map<String, Double> memberships) {
        this.memberships = new HashMap<>(memberships);
    }
    
    /**
     * Sets the membership degree for a fuzzy set.
     * 
     * @param fuzzySetName the name of the fuzzy set
     * @param membershipDegree the membership degree in [0, 1]
     * @throws IllegalArgumentException if membership degree is not in [0, 1]
     */
    public void setMembership(String fuzzySetName, double membershipDegree) {
        if (membershipDegree < 0.0 || membershipDegree > 1.0) {
            throw new IllegalArgumentException(
                String.format("Membership degree must be in [0, 1], got: %.4f", membershipDegree)
            );
        }
        memberships.put(fuzzySetName, membershipDegree);
    }
    
    /**
     * Gets the membership degree for a fuzzy set.
     * 
     * @param fuzzySetName the name of the fuzzy set
     * @return the membership degree, or 0.0 if not set
     */
    public double getMembership(String fuzzySetName) {
        return memberships.getOrDefault(fuzzySetName, 0.0);
    }
    
    /**
     * Gets all fuzzy set names in this fuzzy value.
     * 
     * @return set of fuzzy set names
     */
    public Set<String> getFuzzySetNames() {
        return memberships.keySet();
    }
    
    /**
     * Gets a copy of all memberships.
     * 
     * @return map of fuzzy set names to membership degrees
     */
    public Map<String, Double> getAllMemberships() {
        return new HashMap<>(memberships);
    }
    
    /**
     * Checks if this fuzzy value is empty (no memberships defined).
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return memberships.isEmpty();
    }
    
    /**
     * Gets the fuzzy set with the maximum membership degree.
     * 
     * @return the name of the fuzzy set with maximum membership, or null if empty
     */
    public String getMaxMembershipSet() {
        if (memberships.isEmpty()) {
            return null;
        }
        return memberships.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    /**
     * Gets the maximum membership degree across all fuzzy sets.
     * 
     * @return the maximum membership degree, or 0.0 if empty
     */
    public double getMaxMembership() {
        return memberships.values().stream()
            .max(Double::compareTo)
            .orElse(0.0);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FuzzyValue{");
        boolean first = true;
        for (Map.Entry<String, Double> entry : memberships.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append("=").append(String.format("%.3f", entry.getValue()));
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
}

