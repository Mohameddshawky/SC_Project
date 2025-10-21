package src.main.java.ga.chromosome;
import src.main.java.ga.mutation.BinaryMutation;
import src.main.java.ga.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;


public class BinaryChromosome implements Chromosome<Boolean> {

    private List<Boolean> genes;
    public BinaryChromosome(int length) {
        genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            genes.add(RandomUtils.nextBoolean());
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
        // Default mutation with 0.01 probability
        BinaryMutation mutation = new BinaryMutation(0.01);
        Chromosome<Boolean> mutated = mutation.mutate(this);
        this.genes = new ArrayList<>(mutated.getGenes());
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
