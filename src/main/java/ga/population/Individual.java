package ga.population;

import ga.chromosome.Chromosome;

/**
 * Represents a single individual in the population.
 * An individual has a chromosome and a fitness score.
 * @param <T> The type of gene in the chromosome.
 */
public class Individual<T> {

    private Chromosome<T> chromosome;
    private double fitness;

    /**
     * Constructor to create an individual with a given chromosome.
     * @param chromosome The chromosome of the individual.
     */
    public Individual(Chromosome<T> chromosome) {
        // Implementation omitted.
    }

    /**
     * @return The chromosome of the individual.
     */
    public Chromosome<T> getChromosome() {
        return null;
    }

    /**
     * @return The fitness of the individual.
     */
    public double getFitness() {
        return 0.0;
    }

    /**
     * Sets the fitness of the individual.
     * @param fitness The fitness value.
     */
    public void setFitness(double fitness) {
        // Implementation omitted.
    }
}
