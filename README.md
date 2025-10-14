# Java Genetic Algorithm Library

A flexible and extensible Java library for building Genetic Algorithms (GAs). Designed with Clean Code, SOLID principles, and a clear separation of concerns to allow easy customization and extension for solving various optimization problems.

## 1. Architecture Overview

This library is built upon a modular, strategy-based architecture. The core `GeneticAlgorithm` engine is decoupled from the specific implementation details of its components, such as selection, crossover, and mutation. This is achieved by programming to interfaces (Dependency Inversion), making the system highly configurable and adhering to the Open/Closed Principle.

- **Strategy Pattern**: Core GA operations (Selection, Crossover, Mutation, Replacement) are modeled as interchangeable strategies. You can plug in existing implementations or create new ones without modifying the GA engine.
- **Generics**: The `Chromosome` interface uses generics (`Chromosome<T>`) to provide type safety for different gene types (e.g., `Boolean` for binary, `Integer` for integer).
- **Separation of Concerns**: Each package has a distinct responsibility, promoting high cohesion and low coupling. Problem-specific logic (like fitness evaluation) is completely separated from the core GA workflow.

## 2. Package Structure

The project follows a standard Maven/Gradle directory layout. All library code resides within the `ga` package.

```
src/main/java/
└── ga/
    ├── case_study/      # Example problem implementations.
    ├── chromosome/      # Defines chromosome structures and types.
    ├── core/            # Core GA engine, configuration, and data structures.
    ├── crossover/       # Crossover (recombination) strategies.
    ├── evaluation/      # Fitness evaluation and feasibility handling.
    ├── mutation/        # Mutation strategies.
    ├── population/      # Population and individual management.
    ├── replacement/     # Offspring replacement strategies.
    ├── selection/       # Parent selection strategies.
    └── utils/           # Utility classes.
```

## 3. Core Components (Classes & Interfaces)

### `ga.chromosome`
- **`Chromosome<T>` (Interface)**: Represents a generic chromosome, which is a potential solution to the problem. It defines the contract for all chromosome types.
- **`BinaryChromosome`**: A `Chromosome<Boolean>` implementation for binary-encoded problems.
- **`IntegerChromosome`**: A `Chromosome<Integer>` implementation for integer-based problems.
- **`FloatingPointChromosome`**: A `Chromosome<Double>` implementation for floating-point problems.

### `ga.population`
- **`Individual`**: A wrapper for a `Chromosome` that stores its fitness value.
- **`Population`**: A collection of `Individual` objects that represents one generation.

### `ga.evaluation`
- **`Evaluation` (Interface)**: The contract for fitness evaluation. It must be implemented by the user to define the problem's objective function.
- **`Infeasibility` (Interface)**: A contract for handling constraints. Implement this to penalize or discard invalid solutions.

### `ga.selection`
- **`Selection` (Interface)**: The strategy interface for selecting parent individuals for reproduction.
- *Implementations*: `RouletteWheelSelection`, `TournamentSelection`.

### `ga.crossover`
- **`Crossover` (Interface)**: The strategy interface for combining the genetic material of two parents to create offspring.
- *Implementations*: `OnePointCrossover`, `TwoPointCrossover`, `UniformCrossover`.

### `ga.mutation`
- **`Mutation` (Interface)**: The strategy interface for randomly altering a chromosome's genes to introduce diversity.
- *Implementations*: `BinaryMutation`, `IntegerMutation`, `FloatingPointMutation`.

### `ga.replacement`
- **`Replacement` (Interface)**: The strategy interface for deciding which individuals from the current and offspring populations will survive to the next generation.
- *Implementations*: `ElitismReplacement`, `GenerationalReplacement`, `SteadyStateReplacement`.

### `ga.core`
- **`GAConfig`**: A simple data class to hold all configuration parameters for the GA, such as population size, mutation rate, crossover rate, number of generations, and the selected strategies.
- **`GeneticAlgorithm`**: The main engine that orchestrates the entire evolutionary process. It is configured with a `GAConfig` object and runs the main loop: evaluation -> selection -> crossover -> mutation -> replacement.

### `ga.utils`
- **`RandomUtils`**: A utility class for generating random numbers, intended to be used across the library for consistency.

## 4. How to Use the Library

To solve a problem using this library, you need to follow these steps:

1.  **Define Your Chromosome**: Choose one of the existing `Chromosome` implementations (`Binary`, `Integer`, `FloatingPoint`) that best represents your problem's solution encoding.

2.  **Implement the Evaluation Interface**: This is the most critical step. Create a class that implements the `Evaluation` interface. Inside the `calculateFitness` method, you will write the logic to assess how good a given chromosome is.

3.  **Implement the Infeasibility Interface (Optional)**: If your problem has constraints (e.g., a solution cannot exceed a certain weight), create a class that implements `Infeasibility` to check for and handle these cases.

4.  **Configure the Genetic Algorithm**:
    - Instantiate the strategy objects you want to use (e.g., `TournamentSelection`, `OnePointCrossover`, `BinaryMutation`, `ElitismReplacement`).
    - Create an instance of your custom `Evaluation` implementation.
    - Create a `GAConfig` object and set all the parameters: population size, generations, and the strategy instances.

5.  **Instantiate and Run the GA**:
    - Create an instance of the `GeneticAlgorithm` class, passing the `GAConfig` object to its constructor.
    - Call the `run()` method to start the evolution.
    - Get the best solution found by calling `getBestIndividual()`.

## 5. Extending the Library

The library is designed for easy extension.

-   **Adding a New Strategy**: To add a new Crossover, Selection, Mutation, or Replacement strategy, simply create a new class that implements the corresponding interface (e.g., `public class MyAwesomeCrossover implements Crossover`). You can then plug it into the `GAConfig` just like any other strategy.

-   **Adding a New Chromosome Type**: To support a new type of gene (e.g., `String`), create a new class that implements the `Chromosome<String>` interface. You will also likely need to create corresponding `Mutation` and `Crossover` strategies that can operate on this new chromosome type.

## 6. Example: Solving a Custom Problem (Conceptual)

Let's imagine you want to solve the **Traveling Salesman Problem (TSP)**.

1.  **Problem Representation**: You decide that a solution can be represented as a permutation of city IDs. You would choose `IntegerChromosome` to store the sequence of cities.

2.  **Fitness Evaluation**: You would create a `TSPEvaluation` class:
    ```java
    // User-defined class
    public class TSPEvaluation implements Evaluation<Integer> {
        private Map<Integer, CityCoordinates> cityData;

        public TSPEvaluation(Map<Integer, CityCoordinates> cityData) {
            this.cityData = cityData;
        }

        @Override
        public double calculateFitness(Chromosome<Integer> chromosome) {
            // Logic to calculate the total distance of the tour defined by the chromosome.
            // Lower distance = higher fitness, so return 1.0 / totalDistance.
            double totalDistance = 0.0;
            List<Integer> tour = chromosome.getGenes();
            for (int i = 0; i < tour.size() - 1; i++) {
                // Calculate distance between city tour.get(i) and tour.get(i+1)
            }
            // Add distance from last city back to the first
            return 1.0 / totalDistance;
        }
    }
    ```

3.  **Configuration and Execution**: In your `main` method, you would assemble the components.
    ```java
    // User's main application
    public static void main(String[] args) {
        // 1. Load city data
        Map<Integer, CityCoordinates> cityData = loadCities();

        // 2. Create evaluation instance
        Evaluation tspEvaluator = new TSPEvaluation(cityData);

        // 3. Configure the GA
        GAConfig config = new GAConfig();
        config.setPopulationSize(100);
        config.setGenerations(1000);
        config.setSelection(new TournamentSelection());
        config.setCrossover(new OrderCrossover()); // A custom crossover for TSP
        config.setMutation(new SwapMutation());     // A custom mutation for TSP
        config.setReplacement(new ElitismReplacement());
        config.setEvaluation(tspEvaluator);

        // 4. Run the GA
        GeneticAlgorithm ga = new GeneticAlgorithm(config);
        ga.run();

        // 5. Get the best tour found
        Individual bestSolution = ga.getBestIndividual();
        System.out.println("Best tour: " + bestSolution.getChromosome().getGenes());
        System.out.println("Best fitness: " + bestSolution.getFitness());
    }
    ```