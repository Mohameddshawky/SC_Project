package src.main.java.ga.crossover;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.FloatingPointChromosome;
import src.main.java.ga.chromosome.IntegerChromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.utils.RandomUtils;

import java.util.*;

/**
 * Single-point crossover that works with both Integer and Double chromosome types.
 */
public class SinglePointCrossover<T> implements Crossover<T> {

    private final double crossoverRate;

    public SinglePointCrossover(GAConfig config) {
        this.crossoverRate = config.getCrossoverRate();
    }

    @Override
    public List<Chromosome<T>> crossover(List<Chromosome<T>> parents) {
        List<Chromosome<T>> offspringList = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome<T> parent1 = parents.get(i);
            Chromosome<T> parent2 = parents.get((i + 1) % parents.size());

            List<T> genes1 = parent1.getGenes();
            List<T> genes2 = parent2.getGenes();
            int size = genes1.size();

            if (RandomUtils.nextDouble() > crossoverRate) {
                offspringList.add(parent1.clone());
                offspringList.add(parent2.clone());
                continue;
            }

            int point = RandomUtils.nextInt(size - 1) + 1;

            List<T> child1Genes = new ArrayList<>();
            List<T> child2Genes = new ArrayList<>();

            for (int j = 0; j < point; j++) {
                child1Genes.add(genes1.get(j));
                child2Genes.add(genes2.get(j));
            }
            for (int j = point; j < size; j++) {
                child1Genes.add(genes2.get(j));
                child2Genes.add(genes1.get(j));
            }

            // Create appropriate chromosome type based on parent type
            Chromosome<T> child1 = createChromosome(parent1, child1Genes);
            Chromosome<T> child2 = createChromosome(parent2, child2Genes);

            offspringList.add(child1);
            offspringList.add(child2);
        }

        return offspringList;
    }

    @SuppressWarnings("unchecked")
    private Chromosome<T> createChromosome(Chromosome<T> parent, List<T> genes) {
        if (parent instanceof IntegerChromosome) {
            IntegerChromosome intParent = (IntegerChromosome) parent;
            return (Chromosome<T>) new IntegerChromosome(
                (List<Integer>) genes, 
                intParent.getMinValue(), 
                intParent.getMaxValue()
            );
        } else if (parent instanceof FloatingPointChromosome) {
            return (Chromosome<T>) new FloatingPointChromosome((List<Double>) genes);
        } else {
            throw new IllegalArgumentException("Unsupported chromosome type");
        }
    }
}
