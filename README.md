# Java Computational Intelligence Library (Genetic Algorithms & Fuzzy Logic)

A flexible and extensible Java library for building Genetic Algorithms (GAs) and Fuzzy Logic Systems. Designed with Clean Code, SOLID principles, and a clear separation of concerns to allow easy customization and extension.

## 1. Architecture Overview

This library is built upon a modular, strategy-based architecture.

- **Genetic Algorithms**: The core `GeneticAlgorithm` engine is decoupled from specific implementations of selection, crossover, and mutation using the Strategy Pattern.
- **Fuzzy Logic**: The FL module follows a clean architecture, separating linguistic variables, rules, inference engines, and defuzzifiers. It supports Mamdani inference and is easily extensible.

## 2. Package Structure

The project follows a standard Maven/Gradle directory layout.

```
src/main/java/
├── ga/                  # Genetic Algorithm Library
│   ├── case_study/      # Example GA implementations
│   ├── chromosome/      # Chromosome structures (Binary, Integer, etc.)
│   ├── core/            # Core GA engine and config
│   ├── crossover/       # Crossover strategies
│   ├── evaluation/      # Fitness evaluation interfaces
│   ├── mutation/        # Mutation strategies
│   ├── population/      # Population management
│   ├── replacement/     # Replacement strategies
│   ├── selection/       # Selection strategies
│   └── utils/           # GA utilities
└── fl/                  # Fuzzy Logic Library
    ├── defuzzification/ # Defuzzification methods (Centroid, etc.)
    ├── inference/       # Inference engines (Mamdani, Sugeno)
    ├── membership/      # Membership functions (Triangular, Trapezoidal)
    ├── operators/       # Logic operators (T-Norms, S-Norms)
    ├── rule/            # Rules, Antecedents, Consequents
    ├── utils/           # FL utilities
    └── variable/        # Linguistic variables and fuzzy sets
```

## Installation

To use this library in your project, you can add it as a dependency using Maven or Gradle.

### Maven

```xml
<dependency>
    <groupId>com.your-group</groupId>
    <artifactId>java-ci-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'com.your-group:java-ci-library:1.0.0'
}
```

---

# Part 1: Genetic Algorithms

## 3. GA: Core Components

### `ga.chromosome`
- **`Chromosome<T>` (Interface)**: Represents a generic chromosome.
- **`BinaryChromosome`**, **`IntegerChromosome`**, **`FloatingPointChromosome`**: Concrete implementations.

### `ga.population`
- **`Individual`**: Wrapper for a chromosome and its fitness.
- **`Population`**: Collection of individuals.

### `ga.evaluation`
- **`Evaluation` (Interface)**: Contract for fitness evaluation (User implemented).
- **`Infeasibility` (Interface)**: Contract for handling constraints.

### `ga.selection`
- **`Selection` (Interface)**: Strategy for selecting parents.
- *Implementations*: `RouletteWheelSelection`, `TournamentSelection`.

### `ga.crossover`
- **`Crossover` (Interface)**: Strategy for recombination.
- *Implementations*: `OnePointCrossover`, `TwoPointCrossover`, `UniformCrossover`.

### `ga.mutation`
- **`Mutation` (Interface)**: Strategy for mutation.
- *Implementations*: `BinaryMutation`, `IntegerMutation`, `FloatingPointMutation`.

### `ga.replacement`
- **`Replacement` (Interface)**: Strategy for survivor selection.
- *Implementations*: `ElitismReplacement`, `GenerationalReplacement`, `SteadyStateReplacement`.

## 4. GA: How to Use

1.  **Define Your Chromosome**: Choose `Binary`, `Integer`, or `FloatingPoint`.
2.  **Implement Evaluation**: Create a class implementing `Evaluation<T>`.
3.  **Configure GA**:
    ```java
    GAConfig config = new GAConfig();
    config.setPopulationSize(100);
    config.setGenerations(1000);
    config.setSelection(new TournamentSelection());
    config.setCrossover(new OnePointCrossover());
    config.setMutation(new BinaryMutation());
    config.setEvaluation(new MyEvaluation());
    ```
4.  **Run**:
    ```java
    GeneticAlgorithm ga = new GeneticAlgorithm(config);
    ga.run();
    Individual best = ga.getBestIndividual();
    ```

## 5. GA: Example (TSP)

(See previous documentation for full TSP example code)

---

# Part 2: Fuzzy Logic

## 6. FL: Core Components

### `fl.variable`
- **`LinguisticVariable`**: Represents a variable (e.g., "Temperature") with a domain and fuzzy sets.
- **`FuzzySet`**: A linguistic term (e.g., "Hot") defined by a membership function.

### `fl.membership`
- **`MembershipFunction` (Interface)**: Defines the shape of a fuzzy set.
- *Implementations*: `TriangularMF`, `TrapezoidalMF`, `GaussianMF`.

### `fl.rule`
- **`FuzzyRule`**: An IF-THEN rule (e.g., "IF Temp is Hot THEN Fan is Fast").
- **`Antecedent`**: The IF part, supporting AND/OR operators.
- **`Consequent`**: The THEN part.
- **`RuleBase`**: A collection of fuzzy rules.

### `fl.inference`
- **`InferenceEngine` (Interface)**: Processes inputs against rules to produce fuzzy outputs.
- *Implementations*: `MamdaniInferenceEngine`.

### `fl.defuzzification`
- **`Defuzzifier` (Interface)**: Converts fuzzy output to a crisp value.
- *Implementations*: `CentroidDefuzzifier`, `MeanOfMaximumDefuzzifier`.

## 7. FL: How to Use

1.  **Setup Variables**:
    ```java
    LinguisticVariable temp = new LinguisticVariable("Temperature", 0, 50);
    temp.addFuzzySet(new FuzzySet("Hot", new TriangularMF(30, 50, 50)));
    ```

2.  **Define Rules**:
    ```java
    Condition ifHot = new Condition("Temperature", "Hot");
    Consequent thenFast = new Consequent("FanSpeed", "Fast");
    FuzzyRule rule = new FuzzyRule(new Antecedent(ifHot), thenFast);
    
    RuleBase ruleBase = new RuleBase();
    ruleBase.addRule(rule);
    ```

3.  **Configure Engine**:
    ```java
    MamdaniInferenceEngine engine = new MamdaniInferenceEngine(
        new MinimumTNorm(),       // AND operator
        new MaximumSNorm(),       // OR operator
        new MinimumImplicationOperator() // Implication
    );
    ```

4.  **Execute Pipeline**:
    ```java
    // Fuzzify
    FuzzyValue inputFuzzy = temp.fuzzify(45.0);
    
    // Infer
    MamdaniInferenceResult result = engine.infer(ruleBase, inputs, ...);
    
    // Defuzzify
    Defuzzifier defuzzifier = new CentroidDefuzzifier();
    double output = defuzzifier.defuzzify(
        result.getAggregatedMembershipFunctions().get(fanSpeed),
        0, 100
    );
    ```

## 8. FL: Example (Smart AC Fan Control)

A complete case study implementing a Smart AC Fan Control system is available in `src/main/java/casestudy/acfan`.

- **`ACFanControlSystem.java`**: Encapsulates the fuzzy logic system setup.
- **`ACFanDemo.java`**: Runs multiple scenarios.
- **`ACFanPipelineTest.java`**: Demonstrates the full pipeline with intermediate value inspection.