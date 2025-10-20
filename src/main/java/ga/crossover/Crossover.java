package src.main.java.ga.crossover;



import src.main.java.ga.chromosome.Chromosome;

import java.util.List;


public interface Crossover<T> {
   
    List<Chromosome<T>> crossover(List<Chromosome<T>> parents);
}
