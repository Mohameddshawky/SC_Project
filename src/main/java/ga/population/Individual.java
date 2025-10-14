package ga.population;

import ga.chromosome.Chromosome;

/**
 * Represents a single individual in the population.
 * <p>
 * An individual is a potential solution to the problem and is composed of a
 * chromosome and its corresponding fitness score.
 *
 * @param <T> The type of the gene in the chromosome.
 */
public class Individual<T> {

    /**
     * The chromosome representing the solution's genetic material.
     */
    private Chromosome<T> chromosome;

    /**
     * The fitness score of the individual, indicating how good the solution is.
     */
    private double fitness;

    /**
     * Constructor to create a new individual with a given chromosome.
     *
     * @param chromosome The chromosome for this individual.
     */
    public Individual(Chromosome<T> chromosome) {
        // Implementation omitted.
    }

    /**
     * Gets the chromosome of the individual.
     *
     * @return The chromosome.
     */
    public Chromosome<T> getChromosome() {
        return null; // Implementation omitted.
    }

    /**
     * Gets the fitness score of the individual.
     *
     * @return The fitness score.
     */
    public double getFitness() {
        return 0.0; // Implementation omitted.
    }

    /**
     * Sets the fitness score of the individual.
     *
     * @param fitness The new fitness score.
     */
    public void setFitness(double fitness) {
        // Implementation omitted.
    }
}