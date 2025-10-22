package src.main.java.ga.selection;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankSelection<T> extends ProbabilityBasedSelection<T>{
    public RankSelection(int numberOfSelectedChromosomes) {
        super(numberOfSelectedChromosomes);
    }

    @Override
    public List<Chromosome<T>> select(List<Chromosome<T>> population,  Evaluation evaluation) {
        population.sort(Comparator.comparingDouble(c -> evaluation.evaluate(c)));
        Map<Chromosome<T>, Double> chromosomeProbabilityHashMap = new HashMap<>();
        double cumulativeSum = 0.0;
        int n =  population.size();
        int numberOfAllRanks = n * (n +  1) / 2;
        for (Chromosome<T> c : population) {
            cumulativeSum += 1;
            chromosomeProbabilityHashMap.put(c, cumulativeSum / numberOfAllRanks);
        }

        return super.calculateNewPopulation(population, chromosomeProbabilityHashMap);
    }
}
