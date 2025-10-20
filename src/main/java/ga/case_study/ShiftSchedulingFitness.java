package src.main.java.ga.case_study;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;
import java.util.List;

public class ShiftSchedulingFitness implements Evaluation<Integer> {

    private final ScheduleProblem problem;

    public ShiftSchedulingFitness(ScheduleProblem problem) {
        this.problem = problem;
    }

    @Override
    public double evaluate(Chromosome<Integer> chromosome) {
        double baseFitness = 100.0;
        double penalties = 0.0;

        List<Integer> genes = chromosome.getGenes();
        int numShifts = problem.getNumShifts();
        int[] actualEmployeesPerShift = new int[numShifts + 1];

        for (int employeeIndex = 0; employeeIndex < genes.size(); employeeIndex++) {
            int shiftId = genes.get(employeeIndex);
            if (shiftId > 0 && shiftId <= numShifts) {
                actualEmployeesPerShift[shiftId]++;
                if (!problem.getAvailability()[employeeIndex][shiftId - 1]) {
                    penalties += 30.0;
                }
            } else {
                penalties += 50.0;
            }
        }

        int[] requiredEmployees = problem.getRequiredEmployeesPerShift();
        for (int shiftId = 1; shiftId <= numShifts; shiftId++) {
            int requiredCount = requiredEmployees[shiftId - 1];
            int actualCount = actualEmployeesPerShift[shiftId];
            int deviation = Math.abs(requiredCount - actualCount);
            penalties += deviation * 10.0;
        }

        double finalFitness = baseFitness - penalties;
        return Math.max(0.0, finalFitness);
    }
}
