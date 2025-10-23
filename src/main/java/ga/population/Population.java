package src.main.java.ga.population;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of individuals.
 * <p>
 * The population holds all the candidate solutions for a given generation.
 *
 * @param <T> The type of the gene in the chromosomes of the individuals.
 */
public class Population<T> {

    /**
     * The list of individuals in the population.
     */
    private List<Individual<T>> individuals;

    /**
     * Constructor to create a population with a list of individuals.
     *
     * @param individuals The list of individuals.
     */
    public Population(List<Individual<T>> individuals) {
        this.individuals = individuals;
    }

    /**
     * Constructor to create an empty population.
     */
    public Population() {
        this.individuals = new ArrayList<>();
    }

    /**
     * Gets the list of individuals in the population.
     *
     * @return The list of individuals.
     */
    public List<Individual<T>> getIndividuals() {
        return individuals;
    }

    /**
     * Sets the list of individuals in the population.
     *
     * @param individuals The new list of individuals.
     */
    public void setIndividuals(List<Individual<T>> individuals) {
        this.individuals = individuals;
    }

    /**
     * Gets the fittest individual in the population.
     *
     * @return The individual with the highest fitness score.
     */
    public Individual<T> getFittestIndividual() {
        if (individuals == null || individuals.isEmpty()) {
            return null;
        }
        Individual<T> fittest = individuals.get(0);
        for (Individual<T> individual : individuals) {
            if (individual.getFitness() > fittest.getFitness()) {
                fittest = individual;
            }
        }
        return fittest;
    }

    /**
     * Gets the size of the population.
     *
     * @return The number of individuals.
     */
    public int size() {
        return individuals != null ? individuals.size() : 0;
    }

    /**
     * Adds an individual to the population.
     *
     * @param individual The individual to add.
     */
    public void addIndividual(Individual<T> individual) {
        if (individuals == null) {
            individuals = new ArrayList<>();
        }
        individuals.add(individual);
    }
}