package ga.crossover;

import ga.chromosome.Chromosome;

import java.util.List;

/**
 * A crossover operator.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public interface Crossover<T> {
    /**
     * Performs crossover on a list of parent chromosomes.
     *
     * @param parents the list of parent chromosomes
     * @return a list of offspring chromosomes
     */
    List<Chromosome<T>> crossover(List<Chromosome<T>> parents);
}
