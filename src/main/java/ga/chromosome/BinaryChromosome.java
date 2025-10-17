package src.main.java.ga.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryChromosome implements Chromosome<Boolean> {

    private List<Boolean> genes;
    public BinaryChromosome(int length) {
        genes = new ArrayList<>(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            genes.add(random.nextBoolean());
        }
    }

    public BinaryChromosome(List<Boolean> genes) {
        this.genes = new ArrayList<>(genes);
    }
    @Override
    public Chromosome<Boolean> clone() {
        return new BinaryChromosome(new ArrayList<>(genes));
    }

    @Override
    public List<Boolean> getGenes() {
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
        StringBuilder sb = new StringBuilder("BinaryChromosome: ");
        for (Boolean gene : genes) {
            sb.append(gene ? 1 : 0).append(" ");
        }
        return sb.toString();
    }
}
