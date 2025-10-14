package src.main.java.ga.chromosome;

import java.util.List;

/**
 * An interface representing a chromosome, which is a collection of genes.
 * @param <T> The type of the genes in the chromosome.
 */
public interface Chromosome<T> {

    /**
     * @return A deep copy of the chromosome.
     */
    Chromosome<T> clone();

    /**
     * @return The list of genes that make up the chromosome.
     */
    List<T> getGenes();

    /**
     * @return The length of the chromosome.
     */
    int getLength();

    /**
     * Mutates the chromosome.
     */
    void mutate();
}