package ga.chromosome;

import java.util.List;

/**
 * Represents a chromosome in a genetic algorithm.
 *
 * @param <T> the type of the genes in the chromosome
 */
public interface Chromosome<T> {
    /**
     * Returns the list of genes that make up the chromosome.
     *
     * @return the list of genes
     */
    List<T> getGenes();

    /**
     * Creates a new chromosome with the given genes.
     *
     * @param genes the list of genes
     * @return a new chromosome
     */
    Chromosome<T> newChromosome(List<T> genes);

    /**
     * Returns the length of the chromosome.
     *
     * @return the length of the chromosome
     */
    int getLength();
}
