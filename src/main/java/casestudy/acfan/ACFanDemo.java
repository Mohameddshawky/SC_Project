package src.main.java.casestudy.acfan;

/**
 * Demonstration of the Smart AC Fan Control System.
 *
 * 1. Fuzzification of crisp inputs
 * 2. Rule evaluation and inference
 * 3. Aggregation of results
 * 4. Defuzzification to crisp output
 */
public class ACFanDemo {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     SMART AC FAN CONTROL SYSTEM - FUZZY LOGIC DEMO");
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Create the AC Fan Control System
        ACFanControlSystem system = new ACFanControlSystem();
        
        System.out.println("System Initialized Successfully!");
        System.out.println("Input Variables: Temperature (0-50°C), Humidity (0-100%)");
        System.out.println("Output Variable: Fan Speed (0-100%)");
        System.out.println("Rule Base: 9 rules covering all combinations");
        System.out.println("Inference Method: Mamdani");
        System.out.println("Defuzzification Method: Centroid");
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Run multiple test scenarios
        runScenario(system, 1, 35.0, 70.0, "Hot day with high humidity");
        runScenario(system, 2, 10.0, 30.0, "Cold day with low humidity");
        runScenario(system, 3, 25.0, 50.0, "Moderate temperature and humidity");
        runScenario(system, 4, 45.0, 90.0, "Very hot and very humid");
        runScenario(system, 5, 20.0, 80.0, "Cool but very humid");
        runScenario(system, 6, 40.0, 20.0, "Hot but dry");
        
        System.out.println("=".repeat(60));
        System.out.println("                    DEMO COMPLETED");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Runs a single test scenario and displays the results.
     * 
     * @param system the AC fan control system
     * @param scenarioNum the scenario number
     * @param temp the temperature value
     * @param hum the humidity value
     * @param description a description of the scenario
     */
    private static void runScenario(ACFanControlSystem system, int scenarioNum, 
                                    double temp, double hum, String description) {
        System.out.println(String.format("SCENARIO %d: %s", scenarioNum, description));
        System.out.println("-".repeat(60));
        System.out.println(String.format("Inputs: Temperature = %.1f°C, Humidity = %.1f%%", temp, hum));
        System.out.println();
        
        // Display fuzzification details
        System.out.println(system.getFuzzificationDetails(temp, hum));
        
        // Display active rules
        System.out.println(system.getActiveRules(temp, hum));
        
        // Compute fan speed
        double fanSpeed = system.computeFanSpeed(temp, hum);
        
        // Display result
        System.out.println("Defuzzification (Centroid Method):");
        System.out.println(String.format("  Fan Speed = %.2f%%", fanSpeed));
        System.out.println();
        
        // Interpretation
        String interpretation = interpretFanSpeed(fanSpeed);
        System.out.println("Interpretation: " + interpretation);
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println();
    }
    
    /**
     * Provides a human-readable interpretation of the fan speed.
     * 
     * @param fanSpeed the computed fan speed
     * @return an interpretation string
     */
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
}
