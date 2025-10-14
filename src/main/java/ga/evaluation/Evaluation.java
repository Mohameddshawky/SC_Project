package src.main.java.ga.evaluation;


import src.main.java.ga.chromosome.Chromosome;

/**
 * An evaluation function.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public interface Evaluation<T> {
    /**
     * Evaluates a chromosome and returns its fitness score.
     *
     * @param chromosome the chromosome to evaluate
     * @return the fitness score
     */
    double evaluate(Chromosome<T> chromosome);
}
