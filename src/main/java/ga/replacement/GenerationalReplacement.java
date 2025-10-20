package src.main.java.ga.replacement;

import src.main.java.ga.chromosome.Chromosome;
import java.util.*;

public class GenerationalReplacement<T> implements Replacement<T> {

    @Override
    public List<Chromosome<T>> replace(List<Chromosome<T>> population, List<Chromosome<T>> offspring) {
        // Next generation is entirely new
        return new ArrayList<>(offspring);
    }
}
