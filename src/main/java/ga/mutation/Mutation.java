package src.main.java.ga.mutation;


import src.main.java.ga.chromosome.Chromosome;

/**
 * A mutation operator.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public interface Mutation<T> {
    /**
     * Mutates a chromosome.
     *
     * @param chromosome the chromosome to mutate
     * @return the mutated chromosome
     */
    Chromosome<T> mutate(Chromosome<T> chromosome);
}
