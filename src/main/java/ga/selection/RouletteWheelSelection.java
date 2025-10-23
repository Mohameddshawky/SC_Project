package src.main.java.ga.selection;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.utils.RandomUtils;

import java.util.*;

public class RouletteWheelSelection<T> extends ProbabilityBasedSelection<T> {
    public RouletteWheelSelection(int numberOfSelectedChromosomes) {
        super(numberOfSelectedChromosomes);
    }

    @Override
    public List<Chromosome<T>> select(List<Chromosome<T>> population, Evaluation<T> evaluation) {
        Map<Chromosome<T>, Double> chromosomeProbabilityHashMap = new HashMap<>();
        double cumulativeSum = 0.0;
        
        for (Chromosome<T> c : population) {
            cumulativeSum += evaluation.evaluate(c);
            chromosomeProbabilityHashMap.put(c, cumulativeSum);
        }
        
        // Handle case when total fitness is 0 - use random selection
        if (cumulativeSum == 0.0) {
            List<Chromosome<T>> selected = new ArrayList<>();
            for (int i = 0; i < numberOfSelectedChromosomes; i++) {
                int randomIndex = RandomUtils.nextInt(population.size());
                selected.add(population.get(randomIndex).clone());
            }
            return selected;
        }

        return super.calculateNewPopulation(population, chromosomeProbabilityHashMap);
    }
}
