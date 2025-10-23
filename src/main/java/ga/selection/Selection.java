package src.main.java.ga.selection;


import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.evaluation.Evaluation;

import java.util.List;

public interface Selection<T> {
    List<Chromosome<T>> select(List<Chromosome<T>> population, Evaluation<T> evaluation);
}
