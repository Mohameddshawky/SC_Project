package src.main.java.ga.mutation;

import src.main.java.ga.chromosome.BinaryChromosome;
import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;

public class BinaryMutation implements Mutation<Boolean> {
    private final double mutationProbability;

    public BinaryMutation(double mutationProbability) {
        if (mutationProbability < 0.0 || mutationProbability > 1.0) {
            throw new IllegalArgumentException("Mutation probability must be between 0.0 and 1.0");
        }
        this.mutationProbability = mutationProbability;
    }

    @Override
    public Chromosome<Boolean> mutate(Chromosome<Boolean> chromosome) {
        List<Boolean> originalGenes = chromosome.getGenes();
        List<Boolean> mutatedGenes = new ArrayList<>(originalGenes.size());

        for (Boolean gene : originalGenes) {
            double randomValue = RandomUtils.nextDouble();
            if (randomValue <= mutationProbability) {
                mutatedGenes.add(!gene); // Flip bit
            } else {
                mutatedGenes.add(gene); // No change
            }
        }

        return new BinaryChromosome(mutatedGenes);
    }
}
