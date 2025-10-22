package src.main.java.ga.selection;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.utils.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProbabilityBasedSelection<T> extends AbstractSelection<T> {
    public ProbabilityBasedSelection(int numberOfSelectedChromosomes) {
        super(numberOfSelectedChromosomes);
    }


    protected List<Chromosome<T>>  calculateNewPopulation(List<Chromosome<T>> population, Map<Chromosome<T>, Double> chromosomeProbabilityHashMap) {

        List<Map.Entry<Chromosome<T>, Double>> entries = new ArrayList<>(chromosomeProbabilityHashMap.entrySet());
        population.clear();
        for (int i = 0; i < this.numberOfSelectedChromosomes; i++) {
            double randomNumber = RandomUtils.nextDouble();
            if (randomNumber < entries.getFirst().getValue()) {
                population.add(entries.get(i).getKey());
                continue;
            }

            for (int j = 0; j < entries.size() - 1; j++) {
                Map.Entry<Chromosome<T>, Double> current = entries.get(j);
                Map.Entry<Chromosome<T>, Double> next = entries.get(j + 1);
                if(randomNumber > current.getValue() &&  randomNumber <= next.getValue()) {
                    population.add(entries.get(i).getKey());
                    break;
                }
            }
        }
        return  population;
    }

}
