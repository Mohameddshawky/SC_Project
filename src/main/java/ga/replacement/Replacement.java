package src.main.java.ga.replacement;



import src.main.java.ga.chromosome.Chromosome;

import java.util.List;

public interface Replacement<T> {
    
    List<Chromosome<T>> replace(List<Chromosome<T>> population, List<Chromosome<T>> offspring);
}
