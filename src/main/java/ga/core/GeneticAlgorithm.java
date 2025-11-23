package src.main.java.ga.core;

import src.main.java.ga.chromosome.Chromosome;
import src.main.java.ga.crossover.Crossover;
import src.main.java.ga.evaluation.Evaluation;
import src.main.java.ga.mutation.Mutation;
import src.main.java.ga.population.Individual;
import src.main.java.ga.replacement.Replacement;
import src.main.java.ga.selection.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GeneticAlgorithm<T> {

    private final GAConfig config;
    private final Evaluation<T> evaluation;
    private final Selection<T> selection;
    private final Crossover<T> crossover;
    private final Mutation<T> mutation;
    private final Replacement<T> replacement;
    private final Supplier<Chromosome<T>> chromosomeFactory;
    
    private Individual<T> bestIndividual;

    public GeneticAlgorithm(GAConfig config, Evaluation<T> evaluation, Selection<T> selection,
                            Crossover<T> crossover, Mutation<T> mutation, Replacement<T> replacement,
                            Supplier<Chromosome<T>> chromosomeFactory) {
        this.config = config;
        this.evaluation = evaluation;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.replacement = replacement;
        this.chromosomeFactory = chromosomeFactory;
        this.bestIndividual = null;
    }
    public Individual<T> run() {
        List<Chromosome<T>> population = initializePopulation();
        evaluatePopulation(population);
        updateBestIndividual(population);
        
        System.out.println("Generation 0 - Best Fitness: " + bestIndividual.getFitness());

        for (int generation = 1; generation <= config.getMaxGenerations(); generation++) {
            population = evolve(population);
            evaluatePopulation(population);
            updateBestIndividual(population);
            
            if (generation % 10 == 0 || generation == config.getMaxGenerations()) {
                System.out.println("Generation " + generation + " - Best Fitness: " + bestIndividual.getFitness());
            }
        }
        
        System.out.println("\nEvolution Complete!");
        System.out.println("Best Solution Found: " + bestIndividual.getChromosome().getGenes());
        System.out.println("Best Fitness: " + bestIndividual.getFitness());
        
        return bestIndividual;
    }

    private List<Chromosome<T>> initializePopulation() {
        List<Chromosome<T>> population = new ArrayList<>();
        for (int i = 0; i < config.getPopulationSize(); i++) {
            population.add(chromosomeFactory.get());
        }
        return population;
    }


    private void evaluatePopulation(List<Chromosome<T>> population) {
        for (Chromosome<T> chromosome : population) {
            evaluation.evaluate(chromosome);
        }
    }

    private void updateBestIndividual(List<Chromosome<T>> population) {
        for (Chromosome<T> chromosome : population) {
            double fitness = evaluation.evaluate(chromosome);
            if (bestIndividual == null || fitness > bestIndividual.getFitness()) {
                bestIndividual = new Individual<>(chromosome.clone());
                bestIndividual.setFitness(fitness);
            }
        }
    }

    private List<Chromosome<T>> evolve(List<Chromosome<T>> population) {
        // 1. Selection
        List<Chromosome<T>> parents = selection.select(population, evaluation);
        
        // 2. Crossover
        List<Chromosome<T>> offspring = crossover.crossover(parents);
        
        // 3. Mutation
        List<Chromosome<T>> mutatedOffspring = new ArrayList<>();
        for (Chromosome<T> child : offspring) {
            if (Math.random() < config.getMutationRate()) {
                mutatedOffspring.add(mutation.mutate(child));
            } else {
                mutatedOffspring.add(child);
            }
        }
        return replacement.replace(population, mutatedOffspring);
    }

    public Individual<T> getBestIndividual() {
        return bestIndividual;
    }

    public GAConfig getConfig() {
        return config;
    }
}
