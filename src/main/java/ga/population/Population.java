package ga.population;

import ga.chromosome.Chromosome;
import java.util.List;

/**
 * Represents a collection of individuals (a population).
 * @param <T> The type of gene in the chromosomes of the individuals.
 */
public class Population<T> {

    private List<Individual<T>> individuals;

    /**
     * Default constructor.
     */
    public Population() {
        // Implementation omitted.
    }

    /**
     * Constructor to create a population with a given size.
     * @param populationSize The size of the population.
     */
    public Population(int populationSize) {
        // Implementation omitted.
    }

    /**
     * @return The individuals in the population.
     */
    public List<Individual<T>> getIndividuals() {
        return null;
    }

    /**
     * @return The size of the population.
     */
    public int size() {
        return 0;
    }

    /**
     * Gets the fittest individual in the population.
     * @return The fittest individual.
     */
    public Individual<T> getFittest() {
        return null;
    }
}
