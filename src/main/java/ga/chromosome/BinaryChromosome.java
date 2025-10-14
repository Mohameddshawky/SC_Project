package ga.chromosome;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A binary chromosome, where genes are represented by Booleans.
 */
public class BinaryChromosome implements Chromosome<Boolean> {
    private final List<Boolean> genes;

    public BinaryChromosome(List<Boolean> genes) {
        this.genes = genes;
    }

    @Override
    public List<Boolean> getGenes() {
        return genes;
    }

    @Override
    public Chromosome<Boolean> newChromosome(List<Boolean> genes) {
        return new BinaryChromosome(genes);
    }

    @Override
    public int getLength() {
        return genes.size();
    }

    @Override
    public String toString() {
        return genes.stream()
                .map(g -> g ? "1" : "0")
                .collect(Collectors.joining());
    }
}
