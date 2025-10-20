package src.main.java.ga.crossover;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.IntegerChromosome;
import src.main.java.ga.core.GAConfig;
import src.main.java.ga.utils.RandomUtils;

import java.util.*;

public class Order_Crossover implements Crossover<Integer> {

    private final double crossoverRate;

    public Order_Crossover(GAConfig config) {
        this.crossoverRate = config.getCrossoverRate();
    }

    private void fillRemaining(List<Integer> child, List<Integer> Gene, int start, int end) {
        int size = Gene.size();
        int current = (end + 1) % size;

        for (int i = end + 1; i < size; i++) {
            if (!child.contains(Gene.get(i))) {
                child.set(current, Gene.get(i));
                current = (current + 1) % size;
            }
        }

        for (int i = 0; i <= end; i++) {
            if (!child.contains(Gene.get(i))) {
                child.set(current, Gene.get(i));
                current = (current + 1) % size;
            }
        }
    }

    @Override
    public List<Chromosome<Integer>> crossover(List<Chromosome<Integer>> parents) {
        List<Chromosome<Integer>> offspringList = new ArrayList<>();
        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome<Integer> parent1 = parents.get(i);
            Chromosome<Integer> parent2 = parents.get((i + 1) % parents.size());

            if (RandomUtils.nextDouble() > crossoverRate) {
                offspringList.add(new IntegerChromosome(parent1.getGenes()));
                offspringList.add(new IntegerChromosome(parent2.getGenes()));
                continue;
            }

            List<Integer> genes1 = parent1.getGenes();
            List<Integer> genes2 = parent2.getGenes();
            int size = genes1.size();

            int start = RandomUtils.nextInt(size);
            int end = RandomUtils.nextInt(size);
            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }

            List<Integer> child1 = new ArrayList<>(Collections.nCopies(size, null));
            List<Integer> child2 = new ArrayList<>(Collections.nCopies(size, null));

            for (int j = start; j <= end; j++) {
                child1.set(j, genes1.get(j));
                child2.set(j, genes2.get(j));
            }

            fillRemaining(child1, genes2, start, end);
            fillRemaining(child2, genes1, start, end);

            offspringList.add(new IntegerChromosome(child1));
            offspringList.add(new IntegerChromosome(child2));
        }

        return offspringList;
    }
}
