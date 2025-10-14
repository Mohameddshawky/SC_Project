package ga.chromosome;

import java.util.List;

/**
 * A chromosome represented by a list of floating-point numbers.
 */
public class FloatingPointChromosome implements Chromosome<Double> {

    private List<Double> genes;
    private double min;
    private double max;

    /**
     * Constructor to create a floating-point chromosome.
     * @param length The length of the chromosome.
     * @param min The minimum value of a gene.
     * @param max The maximum value of a gene.
     */
    public FloatingPointChromosome(int length, double min, double max) {
        // Implementation omitted.
    }

    /**
     * Constructor to create a floating-point chromosome with given genes.
     * @param genes The genes of the chromosome.
     */
    public FloatingPointChromosome(List<Double> genes) {
        // Implementation omitted.
    }

    @Override
    public Chromosome<Double> clone() {
        return null;
    }

    @Override
    public List<Double> getGenes() {
        return null;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public void mutate() {
        // Implementation omitted.
    }
}
