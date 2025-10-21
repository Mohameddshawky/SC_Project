package src.main.java.ga.mutation;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.chromosome.FloatingPointChromosome;
import src.main.java.ga.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;

public class FloatingPointMutation implements Mutation<Double> {
    private final double mutationProbability;
    private final double lowerBound;
    private final double upperBound;

    public FloatingPointMutation(double mutationProbability, double lowerBound, double upperBound) {
        if (mutationProbability < 0.0 || mutationProbability > 1.0) {
            throw new IllegalArgumentException("Mutation probability must be between 0.0 and 1.0");
        }
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }
        this.mutationProbability = mutationProbability;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome) {
        List<Double> originalGenes = chromosome.getGenes();
        List<Double> mutatedGenes = new ArrayList<>(originalGenes.size());

        for (Double gene : originalGenes) {
            double randomValue = RandomUtils.nextDouble();
            if (randomValue <= mutationProbability) {
                mutatedGenes.add(uniformMutation(gene));
            } else {
                mutatedGenes.add(gene);
            }
        }

        return new FloatingPointChromosome(mutatedGenes);
    }

    private double uniformMutation(double xi) {
        double deltaL = xi - lowerBound;
        double deltaU = upperBound - xi;
        
        double ri1 = RandomUtils.nextDouble();
        double delta = (ri1 <= 0.5) ? deltaL : deltaU;
        
        double ri2 = RandomUtils.nextDouble() * delta;
        
        double newValue;
        if (ri1 <= 0.5) {
            newValue = xi - ri2;
        } else {
            newValue = xi + ri2;
        }
        
        return Math.max(lowerBound, Math.min(upperBound, newValue));
    }
}
