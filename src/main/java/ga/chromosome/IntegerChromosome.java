package ga.chromosome;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An integer chromosome, where genes are represented by Integers.
 */
public class IntegerChromosome implements Chromosome<Integer> {
    private final List<Integer> genes;

    public IntegerChromosome(List<Integer> genes) {
        this.genes = genes;
    }

    @Override
    public List<Integer> getGenes() {
        return genes;
    }

    @Override
    public Chromosome<Integer> newChromosome(List<Integer> genes) {
        return new IntegerChromosome(genes);
    }

    @Override
    public int getLength() {
        return genes.size();
    }

    @Override
    public String toString() {
        return genes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
