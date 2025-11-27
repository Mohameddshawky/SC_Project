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
    

    public FuzzyValue(Map<String, Double> memberships) {
        this.memberships = new HashMap<>(memberships);
    }
    

    public void setMembership(String fuzzySetName, double membershipDegree) {
        if (membershipDegree < 0.0 || membershipDegree > 1.0) {
            throw new IllegalArgumentException(
                String.format("Membership degree must be in [0, 1], got: %.4f", membershipDegree)
            );
        }
        memberships.put(fuzzySetName, membershipDegree);
    }
    

    public double getMembership(String fuzzySetName) {
        return memberships.getOrDefault(fuzzySetName, 0.0);
    }
    

    public Set<String> getFuzzySetNames() {
        return memberships.keySet();
    }
    

    public Map<String, Double> getAllMemberships() {
        return new HashMap<>(memberships);
    }
    

    public boolean isEmpty() {
        return memberships.isEmpty();
    }
    

    public String getMaxMembershipSet() {
        if (memberships.isEmpty()) {
            return null;
        }
        return memberships.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    

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

