package src.main.java.ga.chromosome;
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
    public IntegerChromosome(List<Integer> genes) {
       this(genes.size(), genes.get(0), genes.get(genes.size()-1));
    }

    @Override
    public Chromosome<Integer> clone() {
        return new IntegerChromosome(new ArrayList<>(genes));
    }

    @Override
    public List<Integer> getGenes() {
        return genes;
    }

    @Override
    public int getLength() {
        return genes.size();
    }

    @Override
    public void mutate() {
        // Implementation omitted.
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