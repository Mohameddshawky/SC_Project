package src.main.java.fl.casestudy.acfan;

import src.main.java.fl.defuzzification.CentroidDefuzzifier;
import src.main.java.fl.defuzzification.Defuzzifier;
import src.main.java.fl.inference.MamdaniInferenceEngine;
import src.main.java.fl.inference.MamdaniInferenceResult;
import src.main.java.fl.inference.MinimumImplicationOperator;
import src.main.java.fl.membership.MembershipFunction;
import src.main.java.fl.membership.TrapezoidalMF;
import src.main.java.fl.membership.TriangularMF;
import src.main.java.fl.operators.MaximumSNorm;
import src.main.java.fl.operators.MinimumTNorm;
import src.main.java.fl.rule.*;
import src.main.java.fl.variable.FuzzySet;
import src.main.java.fl.variable.FuzzyValue;
import src.main.java.fl.variable.LinguisticVariable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Smart AC Fan Control System using Fuzzy Logic.
 * 
 * This system controls the speed of an AC fan based on:
 * - Room Temperature (0-50°C)
 * - Room Humidity (0-100%)
 * 
 * Output: Fan Speed (0-100%)
 * 
 * Demonstrates the complete fuzzy logic pipeline:
 * Fuzzification → Inference → Aggregation → Defuzzification
 */
public class ACFanControlSystem {
    
    // Linguistic Variables
    private LinguisticVariable temperature;
    private LinguisticVariable humidity;
    private LinguisticVariable fanSpeed;
    
    // Fuzzy System Components
    private RuleBase ruleBase;
    private MamdaniInferenceEngine inferenceEngine;
    private Defuzzifier defuzzifier;
    
    /**
     * Creates and initializes the AC Fan Control System.
     */
    public ACFanControlSystem() {
        setupVariables();
        setupRules();
        setupInferenceEngine();
    }
    
    /**
     * Sets up the linguistic variables with their fuzzy sets.
     */
    private void setupVariables() {
        // ===== INPUT 1: Temperature (0-50°C) =====
        temperature = new LinguisticVariable("Temperature", 0, 50);
        
        // Cold: Triangular (0, 0, 25) - Peak at 0°C, extends to 25°C
        temperature.addFuzzySet(new FuzzySet("Cold", new TriangularMF(0, 0, 25)));
        
        // Warm: Trapezoidal (15, 25, 35, 40) - Plateau from 25-35°C
        temperature.addFuzzySet(new FuzzySet("Warm", new TrapezoidalMF(15, 25, 35, 40)));
        
        // Hot: Triangular (30, 50, 50) - Starts at 30°C, peak at 50°C
        temperature.addFuzzySet(new FuzzySet("Hot", new TriangularMF(30, 50, 50)));
        
        // ===== INPUT 2: Humidity (0-100%) =====
        humidity = new LinguisticVariable("Humidity", 0, 100);
        
        // Low: Triangular (0, 0, 50) - Peak at 0%, extends to 50%
        humidity.addFuzzySet(new FuzzySet("Low", new TriangularMF(0, 0, 50)));
        
        // Medium: Triangular (25, 50, 75) - Peak at 50%
        humidity.addFuzzySet(new FuzzySet("Medium", new TriangularMF(25, 50, 75)));
        
        // High: Triangular (50, 100, 100) - Starts at 50%, peak at 100%
        humidity.addFuzzySet(new FuzzySet("High", new TriangularMF(50, 100, 100)));
        
        // ===== OUTPUT: Fan Speed (0-100%) =====
        fanSpeed = new LinguisticVariable("FanSpeed", 0, 100);
        
        // Slow: Triangular (0, 0, 50) - Peak at 0%, extends to 50%
        fanSpeed.addFuzzySet(new FuzzySet("Slow", new TriangularMF(0, 0, 50)));
        
        // Moderate: Triangular (25, 50, 75) - Peak at 50%
        fanSpeed.addFuzzySet(new FuzzySet("Moderate", new TriangularMF(25, 50, 75)));
        
        // Fast: Triangular (50, 100, 100) - Starts at 50%, peak at 100%
        fanSpeed.addFuzzySet(new FuzzySet("Fast", new TriangularMF(50, 100, 100)));
    }
    
    /**
     * Sets up the fuzzy rule base.
     * Creates 9 rules covering all combinations of temperature and humidity.
     */
    private void setupRules() {
        ruleBase = new RuleBase();
        
        // Rule 1: IF Temperature IS Cold AND Humidity IS Low THEN FanSpeed IS Slow
        ruleBase.addRule(createRule("Cold", "Low", "Slow"));
        
        // Rule 2: IF Temperature IS Cold AND Humidity IS Medium THEN FanSpeed IS Slow
        ruleBase.addRule(createRule("Cold", "Medium", "Slow"));
        
        // Rule 3: IF Temperature IS Cold AND Humidity IS High THEN FanSpeed IS Moderate
        ruleBase.addRule(createRule("Cold", "High", "Moderate"));
        
        // Rule 4: IF Temperature IS Warm AND Humidity IS Low THEN FanSpeed IS Moderate
        ruleBase.addRule(createRule("Warm", "Low", "Moderate"));
        
        // Rule 5: IF Temperature IS Warm AND Humidity IS Medium THEN FanSpeed IS Moderate
        ruleBase.addRule(createRule("Warm", "Medium", "Moderate"));
        
        // Rule 6: IF Temperature IS Warm AND Humidity IS High THEN FanSpeed IS Fast
        ruleBase.addRule(createRule("Warm", "High", "Fast"));
        
        // Rule 7: IF Temperature IS Hot AND Humidity IS Low THEN FanSpeed IS Fast
        ruleBase.addRule(createRule("Hot", "Low", "Fast"));
        
        // Rule 8: IF Temperature IS Hot AND Humidity IS Medium THEN FanSpeed IS Fast
        ruleBase.addRule(createRule("Hot", "Medium", "Fast"));
        
        // Rule 9: IF Temperature IS Hot AND Humidity IS High THEN FanSpeed IS Fast
        ruleBase.addRule(createRule("Hot", "High", "Fast"));
    }
    
    /**
     * Helper method to create a fuzzy rule.
     * 
     * @param tempSet the temperature fuzzy set name
     * @param humSet the humidity fuzzy set name
     * @param speedSet the fan speed fuzzy set name
     * @return the created fuzzy rule
     */
    private FuzzyRule createRule(String tempSet, String humSet, String speedSet) {
        Condition tempCondition = new Condition("Temperature", tempSet);
        Condition humCondition = new Condition("Humidity", humSet);
        
        Antecedent antecedent = new Antecedent(
            Arrays.asList(tempCondition, humCondition),
            Antecedent.Operator.AND
        );
        
        Consequent consequent = new Consequent("FanSpeed", speedSet);
        
        return new FuzzyRule(antecedent, consequent);
    }
    
    /**
     * Sets up the inference engine and defuzzifier.
     */
    private void setupInferenceEngine() {
        // Use Minimum T-norm for AND operations
        MinimumTNorm tNorm = new MinimumTNorm();
        
        // Use Maximum S-norm for OR and aggregation operations
        MaximumSNorm sNorm = new MaximumSNorm();
        
        // Use Minimum implication operator (Mamdani style)
        MinimumImplicationOperator implicationOp = new MinimumImplicationOperator();
        
        // Create Mamdani inference engine
        inferenceEngine = new MamdaniInferenceEngine(tNorm, sNorm, implicationOp);
        
        // Use Centroid defuzzification method
        defuzzifier = new CentroidDefuzzifier();
    }
    
    /**
     * Computes the fan speed for given temperature and humidity values.
     * This method demonstrates the complete fuzzy logic pipeline.
     * 
     * @param tempValue the temperature value in °C (0-50)
     * @param humValue the humidity value in % (0-100)
     * @return the computed fan speed in % (0-100)
     */
    public double computeFanSpeed(double tempValue, double humValue) {
        // Step 1: FUZZIFICATION
        FuzzyValue tempFuzzy = temperature.fuzzify(tempValue);
        FuzzyValue humFuzzy = humidity.fuzzify(humValue);
        
        // Prepare fuzzified inputs map
        Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put(temperature, tempFuzzy);
        fuzzifiedInputs.put(humidity, humFuzzy);
        
        // Prepare crisp inputs map (for reference)
        Map<String, Double> crispInputs = new HashMap<>();
        crispInputs.put("Temperature", tempValue);
        crispInputs.put("Humidity", humValue);
        
        // Prepare all variables map
        Map<String, LinguisticVariable> allVariables = new HashMap<>();
        allVariables.put("Temperature", temperature);
        allVariables.put("Humidity", humidity);
        allVariables.put("FanSpeed", fanSpeed);
        
        // Step 2: INFERENCE (includes rule evaluation and aggregation)
        MamdaniInferenceResult inferenceResult = inferenceEngine.infer(
            ruleBase, 
            fuzzifiedInputs, 
            crispInputs, 
            allVariables
        );
        
        // Step 3: DEFUZZIFICATION
        // Get the aggregated membership function for FanSpeed
        Map<LinguisticVariable, MembershipFunction> aggregatedMFs = 
            inferenceResult.getAggregatedMembershipFunctions();
        
        MembershipFunction fanSpeedMF = aggregatedMFs.get(fanSpeed);
        
        if (fanSpeedMF == null) {
            // No rules fired, return middle value
            return 50.0;
        }
        
        // Defuzzify using the Centroid method
        double crispOutput = defuzzifier.defuzzify(
            fanSpeedMF, 
            fanSpeed.getMinValue(), 
            fanSpeed.getMaxValue()
        );
        
        return crispOutput;
    }
    
    /**
     * Gets detailed information about the fuzzification step.
     * 
     * @param tempValue the temperature value
     * @param humValue the humidity value
     * @return a formatted string with fuzzification details
     */
    public String getFuzzificationDetails(double tempValue, double humValue) {
        FuzzyValue tempFuzzy = temperature.fuzzify(tempValue);
        FuzzyValue humFuzzy = humidity.fuzzify(humValue);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Fuzzification:\n");
        
        // Temperature memberships
        sb.append(String.format("  Temperature (%.1f°C): ", tempValue));
        sb.append(String.format("{Cold=%.2f, Warm=%.2f, Hot=%.2f}\n",
            tempFuzzy.getMembership("Cold"),
            tempFuzzy.getMembership("Warm"),
            tempFuzzy.getMembership("Hot")));
        
        // Humidity memberships
        sb.append(String.format("  Humidity (%.1f%%): ", humValue));
        sb.append(String.format("{Low=%.2f, Medium=%.2f, High=%.2f}\n",
            humFuzzy.getMembership("Low"),
            humFuzzy.getMembership("Medium"),
            humFuzzy.getMembership("High")));
        
        return sb.toString();
    }
    
    /**
     * Gets the active rules for given input values.
     * 
     * @param tempValue the temperature value
     * @param humValue the humidity value
     * @return a formatted string with active rules
     */
    public String getActiveRules(double tempValue, double humValue) {
        FuzzyValue tempFuzzy = temperature.fuzzify(tempValue);
        FuzzyValue humFuzzy = humidity.fuzzify(humValue);
        
        Map<String, FuzzyValue> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put("Temperature", tempFuzzy);
        fuzzifiedInputs.put("Humidity", humFuzzy);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Active Rules (firing strength > 0):\n");
        
        MinimumTNorm tNorm = new MinimumTNorm();
        MaximumSNorm sNorm = new MaximumSNorm();
        
        int ruleNum = 1;
        for (FuzzyRule rule : ruleBase.getAllRules()) {
            double firingStrength = rule.evaluate(fuzzifiedInputs, tNorm, sNorm);
            if (firingStrength > 0.001) { // Threshold to avoid floating point noise
                sb.append(String.format("  [Rule %d] %s (strength=%.2f)\n",
                    ruleNum, rule.toString(), firingStrength));
            }
            ruleNum++;
        }
        
        return sb.toString();
    }
    
    // Getters for accessing the components
    public LinguisticVariable getTemperature() {
        return temperature;
    }
    
    public LinguisticVariable getHumidity() {
        return humidity;
    }
    
    public LinguisticVariable getFanSpeed() {
        return fanSpeed;
    }
    
    public RuleBase getRuleBase() {
        return ruleBase;
    }
}
