package ga.replacement;

import ga.chromosome.Chromosome;

import java.util.List;

/**
 * A replacement operator.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public interface Replacement<T> {
    /**
     * Selects which chromosomes from the current population and offspring
     * will be in the next generation.
     *
     * @param population the current population
     * @param offspring  the offspring
     * @return the next generation
     */
    List<Chromosome<T>> replace(List<Chromosome<T>> population, List<Chromosome<T>> offspring);
}
