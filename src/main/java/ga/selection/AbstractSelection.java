package src.main.java.ga.selection;
import java.util.List;
import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

public abstract class AbstractSelection<T> implements Selection<T> {
    protected final int numberOfSelectedChromosomes;

    public AbstractSelection(int numberOfSelectedChromosomes) {
        this.numberOfSelectedChromosomes = numberOfSelectedChromosomes;
    }

    @Override
    public abstract List<Chromosome<T>> select(List<Chromosome<T>> population, Evaluation<T> evaluation);
}