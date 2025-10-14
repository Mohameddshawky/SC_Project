package src.main.java.ga.core;

/**
 * Holds all the hyperparameters and configuration settings for the Genetic Algorithm.
 * <p>
 * This class is a simple data container (POJO) to provide configuration to the
 *  engine.
 */
public class GAConfig {

    /**
     * The number of individuals in the population.
     */
    private int populationSize;

    /**
     * The maximum number of generations to run the algorithm for.
     */
    private int maxGenerations;

    /**
     * The probability of crossover, between 0.0 and 1.0.
     */
    private double crossoverRate;

    /**
     * The probability of mutation, between 0.0 and 1.0.
     */
    private double mutationRate;

    /**
     * The number of elite individuals to carry over to the next generation.
     */
    private int elitismCount;

    // Getters and setters for each parameter would be here.
    // For this declaration-only phase, we omit the method bodies.

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getElitismCount() {
        return elitismCount;
    }

    public void setElitismCount(int elitismCount) {
        this.elitismCount = elitismCount;
    }
}