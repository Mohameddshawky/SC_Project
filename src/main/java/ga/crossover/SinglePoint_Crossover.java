package src.main.java.ga.crossover;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.FloatingPointChromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.utils.RandomUtils;

import java.util.*;

public class SinglePoint_Crossover implements Crossover<Double> {

    private final double crossoverRate;

    public SinglePoint_Crossover(GAConfig config) {
        this.crossoverRate = config.getCrossoverRate();
    }

    @Override
    public List<Chromosome<Double>> crossover(List<Chromosome<Double>> parents) {
        List<Chromosome<Double>> offspringList = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome<Double> parent1 = parents.get(i);
            Chromosome<Double> parent2 = parents.get((i + 1) % parents.size()); // wrap if odd

            List<Double> genes1 = parent1.getGenes();
            List<Double> genes2 = parent2.getGenes();
            int size = genes1.size();

            if (RandomUtils.nextDouble() > crossoverRate) {
                offspringList.add(parent1.clone());
                offspringList.add(parent2.clone());
                continue;
            }

            int point = RandomUtils.nextInt(size - 1) + 1;

            List<Double> child1Genes = new ArrayList<>();
            List<Double> child2Genes = new ArrayList<>();

            for (int j = 0; j < point; j++) {
                child1Genes.add(genes1.get(j));
                child2Genes.add(genes2.get(j));
            }
            for (int j = point; j < size; j++) {
                child1Genes.add(genes2.get(j));
                child2Genes.add(genes1.get(j));
            }

            Chromosome<Double> child1 = new FloatingPointChromosome(child1Genes);
            Chromosome<Double> child2 = new FloatingPointChromosome(child2Genes);

            offspringList.add(child1);
            offspringList.add(child2);
        }

        return offspringList;
    }
}
