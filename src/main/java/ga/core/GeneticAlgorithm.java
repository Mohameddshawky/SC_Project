package src.main.java.ga.core;



import src.main.java.ga.core.GAConfig;
import src.main.java.ga.crossover.Crossover;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.mutation.Mutation;
import src.main.java.ga.replacement.Replacement;
import src.main.java.ga.selection.Selection;


public class GeneticAlgorithm<T> {

    private final GAConfig config;
    private final Evaluation<T> evaluation;
    private final Selection<T> selection;
    private final Crossover<T> crossover;
    private final Mutation<T> mutation;
    private final Replacement<T> replacement;

    public GeneticAlgorithm(GAConfig config, Evaluation<T> evaluation, Selection<T> selection,
                            Crossover<T> crossover, Mutation<T> mutation, Replacement<T> replacement) {
        this.config = config;
        this.evaluation = evaluation;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.replacement = replacement;
    }

   
    // public Population<T> run() {
    //     return null;
    // }

  
    // private Population<T> initializePopulation() {
    //     return null; 
    // }

    // private Population<T> evolve(Population<T> population) {
    //     return null; 
    // }
}
