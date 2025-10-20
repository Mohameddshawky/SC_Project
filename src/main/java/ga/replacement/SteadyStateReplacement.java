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
       
        Map<Chromosome<T>, Double> fitnessMap = new HashMap<>();

        for (Chromosome<T> c : population) {
            fitnessMap.put(c, evaluation.evaluate(c));
        }
        for (Chromosome<T> c : offspring) {
            fitnessMap.put(c, evaluation.evaluate(c));
        }
        Comparator<Chromosome<T>> byFitness = Comparator.comparingDouble(fitnessMap::get);
        population.sort(byFitness);
        offspring.sort(byFitness.reversed()); 

        // Replace the worst individuals
        int count = Math.min(replaceCount, Math.min(population.size(), offspring.size()));
        for (int i = 0; i < count; i++) {
            population.set(i, offspring.get(i));
        }

        return new ArrayList<>(population);
    }
}
