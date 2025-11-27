package src.main.java.fl.rule;

import java.util.Map;

/**
 * Represents the consequent of a Sugeno fuzzy rule.
 * For a zero-order Sugeno model, the output is a constant value.
 * For a first-order Sugeno model, the output is a linear function of the input variables.
 */
public class SugenoConsequent {

    private final String outputVariable;
    private final Map<String, Double> coefficients; // For first-order
    private final double constant; // For zero-order

    public SugenoConsequent(String outputVariable, double constant) {
        this.outputVariable = outputVariable;
        this.constant = constant;
        this.coefficients = null;
    }


    public SugenoConsequent(String outputVariable, Map<String, Double> coefficients, double constant) {
        this.outputVariable = outputVariable;
        this.coefficients = coefficients;
        this.constant = constant;
    }


    public double evaluate(Map<String, Double> crispInputs) {
        if (isZeroOrder()) {
            return constant;
        } else {
            double result = constant;
            for (Map.Entry<String, Double> entry : coefficients.entrySet()) {
                String varName = entry.getKey();
                double coeff = entry.getValue();
                if (!crispInputs.containsKey(varName)) {
                    throw new IllegalArgumentException("Crisp input for variable '" + varName + "' not provided.");
                }
                result += coeff * crispInputs.get(varName);
            }
            return result;
        }
    }

    public boolean isZeroOrder() {
        return coefficients == null || coefficients.isEmpty();
    }

    public String getOutputVariable() {
        return outputVariable;
    }

    @Override
    public String toString() {
        if (isZeroOrder()) {
            return outputVariable + " = " + constant;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(outputVariable).append(" = ");
            boolean first = true;
            for (Map.Entry<String, Double> entry : coefficients.entrySet()) {
                if (!first) {
                    sb.append(" + ");
                }
                sb.append(entry.getValue()).append("*").append(entry.getKey());
                first = false;
            }
            sb.append(" + ").append(constant);
            return sb.toString();
        }
    }
}
