package src.main.java.ga.selection;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.List;

public class TournamentSelection<T> extends AbstractSelection<T> {
    public TournamentSelection(int numberOfSelectedChromosomes) {
        super(numberOfSelectedChromosomes);
    }

    @Override
    public List<Chromosome<T>> select(List<Chromosome<T>> population, Evaluation evaluation) {
        return List.of();
    }
}
