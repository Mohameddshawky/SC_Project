package src.main.java.ga.case_study;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.IntegerChromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.core.GeneticAlgorithm;
import src.main.java.ga.crossover.SinglePointCrossover;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.mutation.IntegerMutation;
import src.main.java.ga.population.Individual;
import src.main.java.ga.replacement.ElitismReplacement;
import src.main.java.ga.selection.RouletteWheelSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Employee Shift Scheduling Problem - Case Study
 * 
 * Problem Description:
 * A company needs to assign employees to shifts. Each employee has availability 
 * constraints (can only work certain shifts), and each shift requires a specific 
 * number of employees. The goal is to find an optimal assignment that:
 * 
 * 1. Meets shift requirements (right number of employees per shift)
 * 2. Respects employee availability constraints
 * 3. Minimizes constraint violations
 * 
 * Chromosome Representation:
 * - An IntegerChromosome where each gene represents an employee
 * - Gene value represents the shift assigned to that employee (1-N for shifts, 0 for no shift)
 * - Example: [1, 2, 1, 3, 0, 2] means:
 *   Employee 0 -> Shift 1, Employee 1 -> Shift 2, Employee 2 -> Shift 1,
 *   Employee 3 -> Shift 3, Employee 4 -> No shift, Employee 5 -> Shift 2
 */
public class CaseStudy {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║   EMPLOYEE SHIFT SCHEDULING - GENETIC ALGORITHM CASE STUDY    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Define the scheduling problem
        ScheduleProblem problem = createSchedulingProblem();
        
        // Display problem details
        displayProblemDetails(problem);
        
        // Create the fitness evaluation function
        Evaluation<Integer> fitnessFunction = new ShiftSchedulingFitness(problem);
        
        // Configure the Genetic Algorithm for HARD constraint problem
        GAConfig config = new GAConfig();
        config.setPopulationSize(100);      // Good population size
        config.setMaxGenerations(150);      // Enough generations to converge
        config.setCrossoverRate(0.75);      // Moderate crossover
        config.setMutationRate(0.2);        // Higher mutation to escape local optima
        config.setElitismCount(5);          // Keep best 5 solutions
        
        // Initialize GA components
        RouletteWheelSelection<Integer> selection = new RouletteWheelSelection<>(config.getPopulationSize());
        SinglePointCrossover<Integer> crossover = new SinglePointCrossover<>(config);
        IntegerMutation mutation = new IntegerMutation(config.getMutationRate());
        ElitismReplacement<Integer> replacement = new ElitismReplacement<>(
            config.getElitismCount(), 
            fitnessFunction
        );
        
        // Chromosome factory: creates random IntegerChromosomes for the problem
        int numEmployees = problem.getNumEmployees();
        int numShifts = problem.getNumShifts();
        
        // Create and run the Genetic Algorithm
        GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<>(
            config,
            fitnessFunction,
            selection,
            crossover,
            mutation,
            replacement,
            () -> new IntegerChromosome(numEmployees, 0, numShifts) // Factory for creating chromosomes
        );
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    STARTING EVOLUTION                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Run the genetic algorithm
        Individual<Integer> bestSolution = ga.run();
        
        // Display the final results
        displayResults(bestSolution, problem);
    }
    
    /**
     * Creates a HARD constraint satisfaction problem with minimal employees.
     * 
     * Challenge: Only 5 employees, 3 shifts, very tight constraints
     * This tests if the GA can find the ONLY valid solution (or one of very few)
     */
    private static ScheduleProblem createSchedulingProblem() {
        // Problem parameters
        int numEmployees = 5;
        int numShifts = 3; // Morning, Afternoon, Night
        
        // Required employees per shift - TIGHT! Need exactly 5 total assignments
        int[] requiredEmployeesPerShift = {2, 2, 1}; 
        // Morning: 2, Afternoon: 2, Night: 1 = Total: 5 employees (ALL must work!)
        
        // Employee availability matrix - VERY RESTRICTED!
        // This creates a "puzzle" where only specific combinations work
        boolean[][] availability = {
            //  M      A      N
            {true,  false,  false}, // Employee 0 - Can work Morning OR Afternoon
            {true,  false, false},  // Employee 1 - Can work Morning OR Night
            {false, true,  false},  // Employee 2 - Can work Afternoon OR Night
            {false, false,  true}, // Employee 3 - Can work Morning OR Afternoon
            {false, false,  true}   // Employee 4 - Can work Afternoon OR Night
        };
        
        /* CHALLENGE ANALYSIS:
         * Morning needs 2: Can use from {0, 1, 3} (only 3 available!)
         * Afternoon needs 2: Can use from {0, 2, 3, 4} (4 available)
         * Night needs 1: Can use from {1, 2, 4} (only 3 available!)
         * 
         * Optimal Solutions (very few exist!):
         * Solution 1: M:{0,1}, A:{3,4}, N:{2}
         * Solution 2: M:{0,3}, A:{2,4}, N:{1}
         * Solution 3: M:{1,3}, A:{0,4}, N:{2}
         * 
         * This is HARD because:
         * - ALL 5 employees MUST be assigned (no slack)
         * - Very limited choices per shift
         * - Many combinations violate constraints
         * - Only ~3-5 valid solutions exist out of 243 possible combinations
         */
        
        return new ScheduleProblem(numEmployees, numShifts, requiredEmployeesPerShift, availability);
    }
    
    /**
     * Displays the problem configuration details.
     */
    private static void displayProblemDetails(ScheduleProblem problem) {
        System.out.println("Problem Configuration:");
        System.out.println("─────────────────────");
        System.out.println("Number of Employees: " + problem.getNumEmployees());
        System.out.println("Number of Shifts: " + problem.getNumShifts());
        System.out.println("\nShift Requirements:");
        String[] shiftNames = {"Morning", "Afternoon", "Night"};
        int totalRequired = 0;
        for (int i = 0; i < problem.getNumShifts(); i++) {
            totalRequired += problem.getRequiredEmployeesPerShift()[i];
            System.out.printf("  %s Shift: %d employees needed\n", 
                shiftNames[i], problem.getRequiredEmployeesPerShift()[i]);
        }
        System.out.println("  TOTAL: " + totalRequired + " assignments needed (out of " + problem.getNumEmployees() + " employees)");
        
        System.out.println("\nEmployee Availability Matrix:");
        System.out.println("  Employee | Morning | Afternoon | Night | Available Slots");
        System.out.println("  ─────────┼─────────┼───────────┼───────┼────────────────");
        boolean[][] availability = problem.getAvailability();
        for (int i = 0; i < problem.getNumEmployees(); i++) {
            int availableCount = 0;
            for (boolean available : availability[i]) {
                if (available) availableCount++;
            }
            System.out.printf("     %2d    |   %s   |     %s     |  %s  |       %d\n", 
                i, 
                availability[i][0] ? "✓" : "✗",
                availability[i][1] ? "✓" : "✗",
                availability[i][2] ? "✓" : "✗",
                availableCount);
        }
        System.out.println("\n⚠ CHALLENGE: All " + problem.getNumEmployees() + " employees MUST be assigned to exactly one shift!");
    }
    
    /**
     * Displays the final results and analysis.
     */
    private static void displayResults(Individual<Integer> bestSolution, ScheduleProblem problem) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       FINAL RESULTS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        Chromosome<Integer> chromosome = bestSolution.getChromosome();
        List<Integer> schedule = chromosome.getGenes();
        
        System.out.println("Best Schedule Assignment:");
        System.out.println("─────────────────────────");
        
        String[] shiftNames = {"No Shift", "Morning", "Afternoon", "Night"};
        
        // Group employees by shift for better readability
        Map<Integer, List<Integer>> shiftAssignments = new HashMap<>();
        for (int i = 0; i <= problem.getNumShifts(); i++) {
            shiftAssignments.put(i, new ArrayList<>());
        }
        
        for (int empId = 0; empId < schedule.size(); empId++) {
            int shiftId = schedule.get(empId);
            shiftAssignments.get(shiftId).add(empId);
        }
        
        // Display by shift
        for (int shiftId = 1; shiftId <= problem.getNumShifts(); shiftId++) {
            System.out.println("\n" + shiftNames[shiftId] + " Shift:");
            List<Integer> employees = shiftAssignments.get(shiftId);
            if (employees.isEmpty()) {
                System.out.println("  No employees assigned");
            } else {
                for (int empId : employees) {
                    boolean isAvailable = problem.getAvailability()[empId][shiftId - 1];
                    String status = isAvailable ? "✓" : "⚠ VIOLATION";
                    System.out.printf("  Employee %2d %s\n", empId, status);
                }
            }
        }
        
        // Display unassigned employees
        List<Integer> unassigned = shiftAssignments.get(0);
        if (!unassigned.isEmpty()) {
            System.out.println("\nNot Assigned:");
            for (int empId : unassigned) {
                System.out.printf("  Employee %2d\n", empId);
            }
        }
        
        // Analyze shift coverage
        System.out.println("\nShift Coverage Analysis:");
        System.out.println("─────────────────────────");
        
        int[] actualEmployeesPerShift = new int[problem.getNumShifts() + 1];
        for (int shiftId : schedule) {
            if (shiftId >= 0 && shiftId <= problem.getNumShifts()) {
                actualEmployeesPerShift[shiftId]++;
            }
        }
        
        for (int shiftId = 1; shiftId <= problem.getNumShifts(); shiftId++) {
            int required = problem.getRequiredEmployeesPerShift()[shiftId - 1];
            int actual = actualEmployeesPerShift[shiftId];
            String status = (actual == required) ? "✓ MET" : 
                           (actual < required) ? "⚠ UNDERSTAFFED" : "⚠ OVERSTAFFED";
            
            System.out.printf("%s Shift: Required=%d, Assigned=%d %s\n", 
                shiftNames[shiftId], required, actual, status);
        }
        
        System.out.println("\nFitness Score: " + bestSolution.getFitness());
        
        // Calculate constraint violations
        int availabilityViolations = 0;
        for (int empId = 0; empId < schedule.size(); empId++) {
            int shiftId = schedule.get(empId);
            if (shiftId > 0 && shiftId <= problem.getNumShifts()) {
                if (!problem.getAvailability()[empId][shiftId - 1]) {
                    availabilityViolations++;
                }
            }
        }
        
        System.out.println("Availability Violations: " + availabilityViolations);
        
        System.out.println("\n════════════════════════════════════════════════════════════════");
    }
}
