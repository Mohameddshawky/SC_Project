package ga.core;

/**
 * Configuration for the Genetic Algorithm.
 * Holds all hyperparameters for a GA run.
 */
public class GAConfig {

    private int populationSize;
    private int maxGenerations;
    private double crossoverRate;
    private double mutationRate;

    /**
     * @return The size of the population.
     */
    public int getPopulationSize() { return 0; }

    /**
     * @return The maximum number of generations.
     */
    public int getMaxGenerations() { return 0; }

    /**
     * @return The crossover rate.
     */
    public double getCrossoverRate() { return 0.0; }

    /**
     * @return The mutation rate.
     */
    public double getMutationRate() { return 0.0; }

    // Setters for builder pattern
    public void setPopulationSize(int populationSize) {}
    public void setMaxGenerations(int maxGenerations) {}
    public void setCrossoverRate(double crossoverRate) {}
    public void setMutationRate(double mutationRate) {}
}
