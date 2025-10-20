package src.main.java.ga.replacement;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.*;

/**
 * A replacement strategy that keeps the top-performing individuals
 * (elitismCount) from the current population and fills the rest from offspring.
 *
 * @param <T> the type of the genes in the chromosomes
 */
public class ElitismReplacement<T> implements Replacement<T> {

    private final int elitismCount;
    private final Evaluation<T> evaluation;

    /**
     * @param elitismCount number of elite individuals to keep
     * @param evaluation   fitness evaluation function
     */
    public ElitismReplacement(int elitismCount, Evaluation<T> evaluation) {
        this.elitismCount = elitismCount;
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

        // Sort both lists by evaluated fitness (descending)
        Comparator<Object> byFitness = Comparator.comparingDouble(fitnessMap::get).reversed();
        population.sort(byFitness);
        offspring.sort(byFitness);

        // Keep the elite individuals
        List<Chromosome<T>> nextGen = new ArrayList<>(population.subList(0, Math.min(elitismCount, population.size())));

        // Fill the rest with offspring
        int remaining = population.size() - nextGen.size();
        nextGen.addAll(offspring.subList(0, Math.min(remaining, offspring.size())));

        return nextGen;
    }
}
