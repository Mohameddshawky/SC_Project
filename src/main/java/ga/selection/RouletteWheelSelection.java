package src.main.java.ga.selection;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.utils.RandomUtils;


import java.util.*;

public class RouletteWheelSelection<T> extends ProbabilityBasedSelection<T>{
    public RouletteWheelSelection(int numberOfSelectedChromosomes) {
        super(numberOfSelectedChromosomes);
    }

    @Override
    public List<Chromosome<T>> select(List<Chromosome<T>> population, Evaluation evaluation) {
        Map<Chromosome<T>, Double> chromosomeProbabilityHashMap = new HashMap<>();
        double cumulativeSum = 0.0;
        for (Chromosome<T> c : population) {
            cumulativeSum += evaluation.evaluate(c);
            chromosomeProbabilityHashMap.put(c, cumulativeSum);
        }

        return super.calculateNewPopulation(population, chromosomeProbabilityHashMap);
    }


}
