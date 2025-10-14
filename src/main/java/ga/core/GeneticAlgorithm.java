package ga.core;

import ga.chromosome.Chromosome;
import ga.crossover.Crossover;
import ga.evaluation.Evaluation;
import ga.mutation.Mutation;
import ga.replacement.Replacement;
import ga.selection.Selection;

import java.util.List;

/**
 * The main class for the Genetic Algorithm.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public class GeneticAlgorithm<T> {
    private final Evaluation<T> evaluation;
    private final Selection<T> selection;
    private final Crossover<T> crossover;
    private final Mutation<T> mutation;
    private final Replacement<T> replacement;

    public GeneticAlgorithm(
            Evaluation<T> evaluation,
            Selection<T> selection,
            Crossover<T> crossover,
            Mutation<T> mutation,
            Replacement<T> replacement) {
        this.evaluation = evaluation;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.replacement = replacement;
    }

    public Chromosome<T> run(List<Chromosome<T>> initialPopulation) {
        // Implementation of the genetic algorithm will go here
        return null;
    }
}
