package src.main.java.ga.replacement;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.*;

/**
 * A steady-state replacement strategy.
 * Replaces a small number of the worst individuals in the population
 * with the best individuals from the offspring.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public class SteadyStateReplacement<T> implements Replacement<T> {

    private final int replaceCount;
    private final Evaluation<T> evaluation;

    /**
     * @param replaceCount number of individuals to replace each generation
     * @param evaluation   fitness evaluation function
     */
    public SteadyStateReplacement(int replaceCount, Evaluation<T> evaluation) {
        this.replaceCount = replaceCount;
        this.evaluation = evaluation;
    }

    @Override
    public List<Chromosome<T>> replace(List<Chromosome<T>> population, List<Chromosome<T>> offspring) {
        // Compute fitness for all chromosomes
        Map<Chromosome<T>, Double> fitnessMap = new HashMap<>();

        for (Chromosome<T> c : population) {
            fitnessMap.put(c, evaluation.evaluate(c));
        }
        for (Chromosome<T> c : offspring) {
            fitnessMap.put(c, evaluation.evaluate(c));
        }

        // Sort population (worst first) and offspring (best first)
        Comparator<Chromosome<T>> byFitness = Comparator.comparingDouble(fitnessMap::get);
        population.sort(byFitness); // worst first
        offspring.sort(byFitness.reversed()); // best first

        // Replace the worst individuals
        int count = Math.min(replaceCount, Math.min(population.size(), offspring.size()));
        for (int i = 0; i < count; i++) {
            population.set(i, offspring.get(i));
        }

        return new ArrayList<>(population);
    }
}
