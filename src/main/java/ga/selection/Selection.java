package ga.selection;

import ga.chromosome.Chromosome;

import java.util.List;

/**
 * A selection operator.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public interface Selection<T> {
    /**
     * Selects a list of parent chromosomes from a population.
     *
     * @param population the population to select from
     * @return a list of parent chromosomes
     */
    List<Chromosome<T>> select(List<Chromosome<T>> population);
}
