package src.main.java.ga.mutation;


import src.main.java.ga.chromosome.Chromosome;

public interface Mutation<T> {
    Chromosome<T> mutate(Chromosome<T> chromosome);
}
