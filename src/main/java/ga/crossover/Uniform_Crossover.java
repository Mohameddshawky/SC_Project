package src.main.java.ga.crossover;

import src.main.java.ga.chromosome.BinaryChromosome;
import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Uniform_Crossover implements Crossover<Boolean>{
    private  final GAConfig config;
    private final double crossoverRate;
    private static final double uniformrate = 0.5;

    public Uniform_Crossover(GAConfig config) {
        this.config = config;
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

            List<Boolean> child1Genes = new ArrayList<>();
            List<Boolean> child2Genes = new ArrayList<>();

            // Decide whether to perform crossover or copy parents directly
            if (RandomUtils.nextDouble() < crossoverRate) {
                for (int g = 0; g < genes1.size(); g++) {
                    if (RandomUtils.nextDouble() < uniformrate) {
                        child1Genes.add(genes1.get(g));
                        child2Genes.add(genes2.get(g));
                    } else {
                        child1Genes.add(genes2.get(g));
                        child2Genes.add(genes1.get(g));
                    }
                }
            } else {
                // No crossover — copy parents directly
                child1Genes.addAll(genes1);
                child2Genes.addAll(genes2);
            }

            // Create new BinaryChromosome objects for offspring
            offspring.add(new BinaryChromosome(child1Genes));
            offspring.add(new BinaryChromosome(child2Genes));
        }

        return offspring;
    }
}
