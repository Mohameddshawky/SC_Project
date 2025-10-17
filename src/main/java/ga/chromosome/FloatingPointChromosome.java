package src.main.java.ga.chromosome;

import src.main.java.ga.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class FloatingPointChromosome implements Chromosome<Double> {
    private List<Double> genes;
    private double minValue;
    private double maxValue;
    public FloatingPointChromosome(int length, double min, double max) {
        this.genes = new ArrayList<>(length);
        this.minValue = min;
        this.maxValue = max;
        for (int i = 0; i < length; i++) {
            double gene = minValue + (maxValue - minValue) * RandomUtils.nextDouble();
            genes.add(gene);
        }
    }
    public FloatingPointChromosome(int length) {
        this(length, 0.0, 1.0);
    }

    public FloatingPointChromosome(List<Double> genes) {
        this.genes = new ArrayList<>(genes);
        this.minValue = 0.0;
        this.maxValue = 1.0;
    }

    @Override
    public Chromosome<Double> clone() {
        return new FloatingPointChromosome(new ArrayList<>(genes));
    }

    @Override
    public List<Double> getGenes() {
        return genes;
    }

    @Override
    public int getLength() {
        return genes.size();
    }

    @Override
    public void mutate() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FloatingPointChromosome: ");
        for (double gene : genes) {
            sb.append(String.format("%.3f ", gene));
        }
        return sb.toString();
    }
}
