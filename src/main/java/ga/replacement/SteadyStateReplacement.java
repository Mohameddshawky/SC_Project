package src.main.java.ga.replacement;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.*;


public class SteadyStateReplacement<T> implements Replacement<T> {

    private final int replaceCount;
    private final Evaluation<T> evaluation;

    public SteadyStateReplacement(int replaceCount, Evaluation<T> evaluation) {
        this.replaceCount = replaceCount;
        this.evaluation = evaluation;
    }

    @Override
    public List<Chromosome<T>> replace(List<Chromosome<T>> population, List<Chromosome<T>> offspring) {
        // Precompute fitness values
        Map<Chromosome<T>, Double> fitness = new HashMap<>();
        for (Chromosome<T> c : population) fitness.put(c, evaluation.evaluate(c));
        for (Chromosome<T> c : offspring) fitness.put(c, evaluation.evaluate(c));

        // Sort population ascending
        population.sort((a, b) -> Double.compare(fitness.get(a), fitness.get(b)));

        // Sort offspring descending
        offspring.sort((a, b) -> Double.compare(fitness.get(b), fitness.get(a)));

        // Replace the worst individuals with the best offspring
        int count = Math.min(replaceCount, Math.min(population.size(), offspring.size()));
        for (int i = 0; i < count; i++) {
            population.set(i, offspring.get(i));
        }

        return new ArrayList<>(population);
    }
}
