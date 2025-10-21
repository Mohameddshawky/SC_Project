package src.main.java.ga.mutation;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.IntegerChromosome;
import src.main.java.ga.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;

public class IntegerMutation implements Mutation<Integer> {
    private final double mutationProbability;

    public IntegerMutation(double mutationProbability) {
        if (mutationProbability < 0.0 || mutationProbability > 1.0) {
            throw new IllegalArgumentException("Mutation probability must be between 0.0 and 1.0");
        }
        this.mutationProbability = mutationProbability;
    }

    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome) {
        double randomValue = RandomUtils.nextDouble();
        if (randomValue > mutationProbability) {
            return chromosome.clone();
        }

        List<Integer> genes = new ArrayList<>(chromosome.getGenes());
        int length = genes.size();

        if (length < 2) {
            return chromosome.clone();
        }

        int index1 = RandomUtils.nextInt(length);
        int index2 = RandomUtils.nextInt(length);
        
        while (index2 == index1) {
            index2 = RandomUtils.nextInt(length);
        }

        Integer temp = genes.get(index1);
        genes.set(index1, genes.get(index2));
        genes.set(index2, temp);

        return new IntegerChromosome(genes);
    }
}
