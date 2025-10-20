package src.main.java.ga.case_study;


public class ScheduleProblem {
    private final int numEmployees;
    private final int numShifts;
    private final int[] requiredEmployeesPerShift;
    private final boolean[][] availability;
    public ScheduleProblem(int numEmployees, int numShifts, int[] requiredEmployeesPerShift, boolean[][] availability) {
        this.numEmployees = numEmployees;
        this.numShifts = numShifts;
        this.requiredEmployeesPerShift = requiredEmployeesPerShift;
        this.availability = availability;
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public int getNumShifts() {
        return numShifts;
    }

    public int[] getRequiredEmployeesPerShift() {
        return requiredEmployeesPerShift;
    }

    public boolean[][] getAvailability() {
        return availability;
    }
}
