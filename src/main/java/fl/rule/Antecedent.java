package src.main.java.fl.rule;

import src.main.java.fl.operators.TNorm;
import src.main.java.fl.operators.SNorm;
import src.main.java.fl.variable.FuzzyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the antecedent (IF part) of a fuzzy rule.
 * Can contain multiple conditions connected by AND or OR operators.
 */
public class Antecedent {
    
    public enum Operator {
        AND, OR
    }
    
    private final List<Condition> conditions;
    private final Operator operator;
    

    public Antecedent(Condition condition) {
        this.conditions = new ArrayList<>();
        this.conditions.add(condition);
        this.operator = Operator.AND; // Default, doesn't matter for single condition
    }
    

    public Antecedent(List<Condition> conditions, Operator operator) {
        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("Conditions cannot be null or empty");
        }
        if (operator == null) {
            throw new IllegalArgumentException("Operator cannot be null");
        }
        this.conditions = new ArrayList<>(conditions);
        this.operator = operator;
    }

    public double evaluate(Map<String, FuzzyValue> fuzzifiedInputs, TNorm tNorm, SNorm sNorm) {
        if (conditions.isEmpty()) {
            return 0.0;
        }
        
        // Evaluate first condition
        Condition firstCondition = conditions.get(0);
        FuzzyValue fuzzyValue = fuzzifiedInputs.get(firstCondition.getVariableName());
        if (fuzzyValue == null) {
            throw new IllegalArgumentException(
                String.format("Variable '%s' not found in fuzzified inputs", firstCondition.getVariableName())
            );
        }
        
        double membership = fuzzyValue.getMembership(firstCondition.getFuzzySetName());
        double result = firstCondition.evaluate(membership);
        
        // Apply operator to combine with remaining conditions
        for (int i = 1; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            fuzzyValue = fuzzifiedInputs.get(condition.getVariableName());
            if (fuzzyValue == null) {
                throw new IllegalArgumentException(
                    String.format("Variable '%s' not found in fuzzified inputs", condition.getVariableName())
                );
            }
            
            membership = fuzzyValue.getMembership(condition.getFuzzySetName());
            double conditionValue = condition.evaluate(membership);
            
            if (operator == Operator.AND) {
                result = tNorm.apply(result, conditionValue);
            } else {
                result = sNorm.apply(result, conditionValue);
            }
        }
        
        return result;
    }
    
    public List<Condition> getConditions() {
        return new ArrayList<>(conditions);
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    @Override
    public String toString() {
        if (conditions.size() == 1) {
            return conditions.get(0).toString();
        }
        
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                sb.append(" ").append(operator).append(" ");
            }
            sb.append(conditions.get(i).toString());
        }
        sb.append(")");
        return sb.toString();
    }
}

