package src.main.java.ga.evaluation;


import src.main.java.ga.chromosome.Chromosome;


public interface Evaluation<T> {
    
    double evaluate(Chromosome<T> chromosome);
}
