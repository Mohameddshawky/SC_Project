package src.main.java.ga.chromosome;
import src.main.java.ga.mutation.IntegerMutation;
import src.main.java.ga.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;


public class IntegerChromosome implements Chromosome<Integer> {

    private List<Integer> genes;
    private int minValue;
    private int maxValue;


    public IntegerChromosome(int length, int min, int max) {
        genes=new ArrayList<>(length);
        minValue=min;
        maxValue=max;
        for (int i = 0; i < length; i++)
        {
            int gene = RandomUtils.nextInt((maxValue - minValue) + 1) + minValue;
            genes.add(gene);
        }
    }
    
    public IntegerChromosome(List<Integer> genes, int min, int max) {
        this.genes = new ArrayList<>(genes);
        this.minValue = min;
        this.maxValue = max;
    }

    @Override
    public Chromosome<Integer> clone() {
        return new IntegerChromosome(new ArrayList<>(genes), minValue, maxValue);
    }

    @Override
    public List<Integer> getGenes() {
        return genes;
    }

    @Override
    public int getLength() {
        return genes.size();
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public void mutate() {
        // Default mutation with 0.05 probability
        IntegerMutation mutation = new IntegerMutation(0.05);
        Chromosome<Integer> mutated = mutation.mutate(this);
        this.genes = new ArrayList<>(mutated.getGenes());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("IntegerChromosome: ");
        for (int gene : genes) {
            sb.append(gene);
        }
        return sb.toString();
    }
}