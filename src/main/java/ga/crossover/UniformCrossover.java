package src.main.java.ga.crossover;

import src.main.java.ga.chromosome.BinaryChromosome;
import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class UniformCrossover implements Crossover<Boolean>{
    private final double crossoverRate;
    private static final double uniformrate = 0.5;

    public UniformCrossover(GAConfig config) {
        crossoverRate=config.getCrossoverRate();
    }

    @Override
    public List<Chromosome<Boolean>> crossover(List<Chromosome<Boolean>> parents) {
        List<Chromosome<Boolean>> offspring = new ArrayList<>();

        // Loop through parents in pairs
        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome<Boolean> parent1 = parents.get(i);
            Chromosome<Boolean> parent2 = parents.get((i + 1) % parents.size());

            List<Boolean> genes1 = parent1.getGenes();
            List<Boolean> genes2 = parent2.getGenes();

            List<Boolean> child1 = new ArrayList<>();
            List<Boolean> child2 = new ArrayList<>();

            if (RandomUtils.nextDouble() < crossoverRate) {
                for (int g = 0; g < genes1.size(); g++) {
                    if (RandomUtils.nextDouble() < uniformrate) {
                        child1.add(genes1.get(g));
                        child2.add(genes2.get(g));
                    } else {
                        child1.add(genes2.get(g));
                        child2.add(genes1.get(g));
                    }
                }
            } else {
                child1.addAll(genes1);
                child2.addAll(genes2);
            }

            offspring.add(new BinaryChromosome(child1));
            offspring.add(new BinaryChromosome(child2));
        }
        return offspring;
    }
}
