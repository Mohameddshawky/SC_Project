package src.main.java.casestudy.acfan;

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
 * Comprehensive test demonstrating the complete Fuzzy Logic Pipeline:
 * Fuzzify → Infer → Aggregate → Defuzzify → Crisp Output
 */
public class ACFanPipelineTest {
    
    public static void main(String[] args) {
        System.out.println("╔" + "═".repeat(78) + "╗");
        System.out.println("║" + centerText("FUZZY LOGIC PIPELINE TEST - COMPLETE TRACE", 78) + "║");
        System.out.println("║" + centerText("Smart AC Fan Control System", 78) + "║");
        System.out.println("╚" + "═".repeat(78) + "╝");
        System.out.println();
        
        // Test with specific input values
        double temperatureInput = 35.0;
        double humidityInput = 70.0;
        
        System.out.println("  CRISP INPUTS:");
        System.out.println("   Temperature: " + temperatureInput + "°C");
        System.out.println("   Humidity: " + humidityInput + "%");
        System.out.println();
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 1: SETUP LINGUISTIC VARIABLES =====
        System.out.println("  STEP 1: LINGUISTIC VARIABLES SETUP");
        System.out.println();
        
        LinguisticVariable temperature = setupTemperature();
        LinguisticVariable humidity = setupHumidity();
        LinguisticVariable fanSpeed = setupFanSpeed();
        
        displayVariableSetup(temperature);
        displayVariableSetup(humidity);
        displayVariableSetup(fanSpeed);
        
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 2: FUZZIFICATION =====
        System.out.println("  STEP 2: FUZZIFICATION (Crisp → Fuzzy)");
        System.out.println();
        
        FuzzyValue tempFuzzy = temperature.fuzzify(temperatureInput);
        FuzzyValue humFuzzy = humidity.fuzzify(humidityInput);
        
        System.out.println("Temperature Fuzzification:");
        displayFuzzification(temperature, temperatureInput, tempFuzzy);
        System.out.println();
        
        System.out.println("Humidity Fuzzification:");
        displayFuzzification(humidity, humidityInput, humFuzzy);
        System.out.println();
        
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 3: RULE BASE SETUP =====
        System.out.println("  STEP 3: RULE BASE CONSTRUCTION");
        System.out.println();
        
        RuleBase ruleBase = setupRuleBase();
        System.out.println("Total Rules: " + ruleBase.getRuleCount());
        System.out.println();
        
        int ruleNum = 1;
        for (FuzzyRule rule : ruleBase.getAllRules()) {
            System.out.println("   [Rule " + ruleNum + "] " + rule.toString());
            ruleNum++;
        }
        System.out.println();
        
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 4: INFERENCE (Rule Evaluation) =====
        System.out.println("   STEP 4: INFERENCE (Rule Evaluation & Firing Strength)");
        System.out.println();
        
        Map<String, FuzzyValue> fuzzifiedInputsMap = new HashMap<>();
        fuzzifiedInputsMap.put("Temperature", tempFuzzy);
        fuzzifiedInputsMap.put("Humidity", humFuzzy);
        
        MinimumTNorm tNorm = new MinimumTNorm();
        MaximumSNorm sNorm = new MaximumSNorm();
        
        System.out.println("Using T-Norm: " + tNorm.getName() + " (for AND operations)");
        System.out.println("Using S-Norm: " + sNorm.getName() + " (for OR operations)");
        System.out.println();
        
        ruleNum = 1;
        for (FuzzyRule rule : ruleBase.getAllRules()) {
            double firingStrength = rule.evaluate(fuzzifiedInputsMap, tNorm, sNorm);
            
            if (firingStrength > 0.001) {
                System.out.println("   ✓ [Rule " + ruleNum + "] FIRED");
                System.out.println("      " + rule.toString());
                System.out.println("      Firing Strength: " + String.format("%.4f", firingStrength));
                
                // Show how firing strength was calculated
                Antecedent ant = rule.getAntecedent();
                for (Condition cond : ant.getConditions()) {
                    String varName = cond.getVariableName();
                    String setName = cond.getFuzzySetName();
                    double membership = fuzzifiedInputsMap.get(varName).getMembership(setName);
                    System.out.println("      - " + varName + " IS " + setName + ": μ = " + String.format("%.4f", membership));
                }
                System.out.println();
            } else {
                System.out.println("   ✗ [Rule " + ruleNum + "] NOT FIRED (strength ≈ 0)");
            }
            ruleNum++;
        }
        
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 5: AGGREGATION & IMPLICATION =====
        System.out.println("  STEP 5: IMPLICATION & AGGREGATION");
        System.out.println();
        
        Map<LinguisticVariable, FuzzyValue> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put(temperature, tempFuzzy);
        fuzzifiedInputs.put(humidity, humFuzzy);
        
        Map<String, Double> crispInputs = new HashMap<>();
        crispInputs.put("Temperature", temperatureInput);
        crispInputs.put("Humidity", humidityInput);
        
        Map<String, LinguisticVariable> allVariables = new HashMap<>();
        allVariables.put("Temperature", temperature);
        allVariables.put("Humidity", humidity);
        allVariables.put("FanSpeed", fanSpeed);
        
        MinimumImplicationOperator implicationOp = new MinimumImplicationOperator();
        MamdaniInferenceEngine engine = new MamdaniInferenceEngine(tNorm, sNorm, implicationOp);
        
        System.out.println("Implication Operator: " + implicationOp.getName());
        System.out.println("Aggregation Operator: " + sNorm.getName() + " (S-Norm)");
        System.out.println();
        System.out.println("Process:");
        System.out.println("   1. For each fired rule, apply implication to clip/scale the consequent MF");
        System.out.println("   2. Aggregate all implied MFs using " + sNorm.getName() + " operator");
        System.out.println();
        
        MamdaniInferenceResult result = engine.infer(ruleBase, fuzzifiedInputs, crispInputs, allVariables);
        
        System.out.println("✓ Aggregated membership function created for FanSpeed");
        System.out.println();
        
        System.out.println("─".repeat(80));
        System.out.println();
        
        // ===== STEP 6: DEFUZZIFICATION =====
        System.out.println("  STEP 6: DEFUZZIFICATION (Fuzzy → Crisp)");
        System.out.println();
        
        Defuzzifier defuzzifier = new CentroidDefuzzifier();
        System.out.println("Defuzzification Method: " + defuzzifier.getName() + " (Center of Gravity)");
        System.out.println();
        
        Map<LinguisticVariable, MembershipFunction> aggregatedMFs = result.getAggregatedMembershipFunctions();
        MembershipFunction fanSpeedMF = aggregatedMFs.get(fanSpeed);
        
        if (fanSpeedMF != null) {
            System.out.println("Aggregated Membership Function Details:");
            System.out.println("   Domain: [" + fanSpeed.getMinValue() + ", " + fanSpeed.getMaxValue() + "]");
            System.out.println();
            
            // Sample the aggregated MF at key points
            System.out.println("   Sample Points of Aggregated MF:");
            double[] samplePoints = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
            for (double x : samplePoints) {
                double membership = fanSpeedMF.getMembership(x);
                if (membership > 0.001) {
                    System.out.println("      FanSpeed = " + String.format("%5.1f", x) + "% → μ = " + String.format("%.4f", membership));
                }
            }
            System.out.println();
            
            double crispOutput = defuzzifier.defuzzify(fanSpeedMF, fanSpeed.getMinValue(), fanSpeed.getMaxValue());
            
            System.out.println("   Centroid Calculation:");
            System.out.println("      ∫ x·μ(x) dx / ∫ μ(x) dx");
            System.out.println();
            System.out.println("   ✓ Defuzzified Value: " + String.format("%.2f", crispOutput) + "%");
            System.out.println();
            
            System.out.println("─".repeat(80));
            System.out.println();
            
            // ===== FINAL RESULT =====
            System.out.println("╔" + "═".repeat(78) + "╗");
            System.out.println("║" + centerText("FINAL RESULT", 78) + "║");
            System.out.println("╚" + "═".repeat(78) + "╝");
            System.out.println();
            System.out.println("  INPUTS:");
            System.out.println("   Temperature: " + temperatureInput + "°C");
            System.out.println("   Humidity: " + humidityInput + "%");
            System.out.println();
            System.out.println("  OUTPUT:");
            System.out.println("   Fan Speed: " + String.format("%.2f", crispOutput) + "%");
            System.out.println();
            System.out.println("  INTERPRETATION:");
            System.out.println("   " + interpretFanSpeed(crispOutput));
            System.out.println();
            
            // ===== PIPELINE SUMMARY =====
            System.out.println("─".repeat(80));
            System.out.println();
            System.out.println("  PIPELINE EXECUTION SUMMARY:");
            System.out.println();
            System.out.println("   1. ✓ Fuzzification: Crisp inputs converted to fuzzy values");
            System.out.println("   2. ✓ Inference: Rules evaluated with firing strengths");
            System.out.println("   3. ✓ Implication: Consequent MFs clipped by firing strengths");
            System.out.println("   4. ✓ Aggregation: All implied MFs combined into single output MF");
            System.out.println("   5. ✓ Defuzzification: Fuzzy output converted to crisp value");
            System.out.println();
            System.out.println("   All intermediate values were accessible and displayed!");
            System.out.println();
            
        } else {
            System.out.println("     No rules fired - using default value");
        }
        
        System.out.println("═".repeat(80));
    }
    
    // Helper methods
    
    private static LinguisticVariable setupTemperature() {
        LinguisticVariable temp = new LinguisticVariable("Temperature", 0, 50);
        temp.addFuzzySet(new FuzzySet("Cold", new TriangularMF(0, 0, 25)));
        temp.addFuzzySet(new FuzzySet("Warm", new TrapezoidalMF(15, 25, 35, 40)));
        temp.addFuzzySet(new FuzzySet("Hot", new TriangularMF(30, 50, 50)));
        return temp;
    }
    
    private static LinguisticVariable setupHumidity() {
        LinguisticVariable hum = new LinguisticVariable("Humidity", 0, 100);
        hum.addFuzzySet(new FuzzySet("Low", new TriangularMF(0, 0, 50)));
        hum.addFuzzySet(new FuzzySet("Medium", new TriangularMF(25, 50, 75)));
        hum.addFuzzySet(new FuzzySet("High", new TriangularMF(50, 100, 100)));
        return hum;
    }
    
    private static LinguisticVariable setupFanSpeed() {
        LinguisticVariable speed = new LinguisticVariable("FanSpeed", 0, 100);
        speed.addFuzzySet(new FuzzySet("Slow", new TriangularMF(0, 0, 50)));
        speed.addFuzzySet(new FuzzySet("Moderate", new TriangularMF(25, 50, 75)));
        speed.addFuzzySet(new FuzzySet("Fast", new TriangularMF(50, 100, 100)));
        return speed;
    }
    
    private static RuleBase setupRuleBase() {
        RuleBase ruleBase = new RuleBase();
        
        ruleBase.addRule(createRule("Cold", "Low", "Slow"));
        ruleBase.addRule(createRule("Cold", "Medium", "Slow"));
        ruleBase.addRule(createRule("Cold", "High", "Moderate"));
        ruleBase.addRule(createRule("Warm", "Low", "Moderate"));
        ruleBase.addRule(createRule("Warm", "Medium", "Moderate"));
        ruleBase.addRule(createRule("Warm", "High", "Fast"));
        ruleBase.addRule(createRule("Hot", "Low", "Fast"));
        ruleBase.addRule(createRule("Hot", "Medium", "Fast"));
        ruleBase.addRule(createRule("Hot", "High", "Fast"));
        
        return ruleBase;
    }
    
    private static FuzzyRule createRule(String tempSet, String humSet, String speedSet) {
        Condition tempCondition = new Condition("Temperature", tempSet);
        Condition humCondition = new Condition("Humidity", humSet);
        
        Antecedent antecedent = new Antecedent(
            Arrays.asList(tempCondition, humCondition),
            Antecedent.Operator.AND
        );
        
        Consequent consequent = new Consequent("FanSpeed", speedSet);
        
        return new FuzzyRule(antecedent, consequent);
    }
    
    private static void displayVariableSetup(LinguisticVariable var) {
        System.out.println("   " + var.getName() + ":");
        System.out.println("      Domain: [" + var.getMinValue() + ", " + var.getMaxValue() + "]");
        System.out.print("      Fuzzy Sets: ");
        System.out.println(String.join(", ", var.getFuzzySetNames()));
    }
    
    private static void displayFuzzification(LinguisticVariable var, double crispValue, FuzzyValue fuzzyValue) {
        System.out.println("   Input: " + crispValue + " " + getUnit(var.getName()));
        System.out.println("   Membership Degrees:");
        
        for (String setName : var.getFuzzySetNames()) {
            double membership = fuzzyValue.getMembership(setName);
            String bar = createBar(membership, 40);
            System.out.println("      " + String.format("%-10s", setName) + ": " + 
                             String.format("%.4f", membership) + " " + bar);
        }
    }
    
    private static String createBar(double value, int maxLength) {
        int filledLength = (int) (value * maxLength);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < maxLength; i++) {
            if (i < filledLength) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }
    
    private static String getUnit(String varName) {
        switch (varName) {
            case "Temperature": return "°C";
            case "Humidity": return "%";
            case "FanSpeed": return "%";
            default: return "";
        }
    }
    
    private static String interpretFanSpeed(double fanSpeed) {
        if (fanSpeed < 25) {
            return "Very Low - Minimal cooling needed";
        } else if (fanSpeed < 40) {
            return "Low - Slight cooling required";
        } else if (fanSpeed < 60) {
            return "Moderate - Comfortable cooling";
        } else if (fanSpeed < 75) {
            return "High - Strong cooling needed";
        } else {
            return "Very High - Maximum cooling required";
        }
    }
    
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        while (sb.length() < width) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
