# Artificial Neural Network (ANN) Library

A comprehensive Java library for building, training, and evaluating feedforward neural networks from scratch.

## Overview

This library provides a complete implementation of artificial neural networks (ANNs) with support for:
- Multiple activation functions (Sigmoid, ReLU, Tanh, Linear)
- Various weight initialization strategies (Random, Xavier, He)
- Backpropagation learning algorithm
- Mini-batch gradient descent
- Customizable network architectures
- Data preprocessing utilities
- Performance evaluation metrics
- Network inspection and debugging tools

## Architecture

The library is organized into modular packages following clean code principles:

```
ann/
├── activation/       # Activation functions
├── initialization/   # Weight initialization strategies
├── layer/           # Layer implementations
├── loss/            # Loss functions
├── network/         # Network architecture and builder
├── training/        # Training pipeline
├── evaluation/      # Prediction and metrics
├── data/            # Data handling utilities
├── core/            # Debugging and monitoring
├── utils/           # Helper functions
└── examples/        # Case studies and examples
```

## Quick Start

### Building a Simple Network

```java
// Create a simple 3-layer network
NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(2)
    .addDenseLayer(4, new SigmoidActivation())
    .addOutputLayer(1, new SigmoidActivation())
    .setLearningRate(0.5)
    .setEpochs(5000)
    .build();
```

### Training the Network

```java
// Prepare training data
double[][] trainInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
double[][] trainTargets = {{0}, {1}, {1}, {0}};

// Configure and train
TrainingConfig config = new TrainingConfig();
config.setLearningRate(0.5);
config.setEpochs(5000);

Trainer trainer = new Trainer(network, config);
TrainingHistory history = trainer.train(trainInputs, trainTargets);
```

### Making Predictions

```java
Predictor predictor = new Predictor(network);
double[] output = predictor.predict(new double[]{1, 0});
System.out.println("Prediction: " + output[0]);
```

## Key Components

### Activation Functions

- **Sigmoid**: `σ(x) = 1/(1+e^(-x))` - Binary classification, output layer
- **ReLU**: `f(x) = max(0, x)` - Hidden layers, prevents vanishing gradient
- **Tanh**: `tanh(x)` - Hidden layers, zero-centered
- **Linear**: `f(x) = x` - Regression output layer

### Weight Initialization

- **Random Uniform**: Uniform distribution in [-1, 1]
- **Xavier/Glorot**: For sigmoid/tanh activations
- **He**: For ReLU activations

### Loss Functions

- **MSE (Mean Squared Error)**: For regression tasks
- **Cross-Entropy**: For classification tasks

### Training Features

- Mini-batch gradient descent
- Batch shuffling
- Early stopping
- Training history tracking
- Real-time monitoring

## Data Preprocessing

### Normalization

```java
DataNormalizer normalizer = new DataNormalizer(DataNormalizer.Strategy.MIN_MAX);
double[][] normalized = normalizer.fitTransform(features);
```

Supported strategies:
- **MIN_MAX**: Scale to [0, 1]
- **Z_SCORE**: Standardize to mean=0, std=1
- **MEAN_NORM**: Normalize to [-1, 1]

### Train/Test Splitting

```java
DataSplitter splitter = new DataSplitter();
Dataset[] splits = splitter.trainTestSplit(dataset, 0.8, true);
```

### Data Validation

```java
// Check for missing values
boolean hasInvalid = DataValidator.containsInvalidValues(data);

// Handle missing values
double[][] cleaned = DataValidator.handleMissingValues(
    data, 
    DataValidator.MissingValueStrategy.MEAN_IMPUTE
);
```

## Evaluation

### Metrics

```java
// Regression metrics
double mse = Evaluator.computeMSE(predictions, targets);
double rmse = Evaluator.computeRMSE(predictions, targets);
double mae = Evaluator.computeMAE(predictions, targets);

// Classification metrics
double accuracy = Evaluator.computeAccuracy(predictions, targets);
```

### Confusion Matrix

```java
ConfusionMatrix cm = ConfusionMatrix.build(predictions, targets, numClasses);
System.out.println("Accuracy: " + cm.getAccuracy());
System.out.println("Precision: " + cm.getMacroPrecision());
System.out.println("Recall: " + cm.getMacroRecall());
System.out.println("F1-Score: " + cm.getMacroF1Score());
```

## Debugging and Monitoring

### Network Inspection

```java
NetworkInspector inspector = new NetworkInspector(network);

// Print architecture summary
inspector.printArchitecture();

// Visualize layer outputs
inspector.printLayerOutputs(input);

// Check weights and gradients
inspector.printWeights();
inspector.printGradientMagnitudes();
inspector.printWeightStatistics();
```

### Training Monitoring

```java
TrainingMonitor monitor = new TrainingMonitor();

// Track training progress
monitor.recordLoss(loss);

// Check training status
boolean converging = monitor.isConverging();
boolean stalled = monitor.hasStalled(0.001);
boolean vanishing = monitor.hasVanishingGradient();
boolean exploding = monitor.hasExplodingGradient();

// Generate summary
String summary = monitor.generateSummary(history);
```

## Case Studies

### 1. XOR Problem (Classic Neural Network Problem)

The XOR problem demonstrates the necessity of hidden layers for non-linearly separable problems.

**Key Features:**
- 2 inputs, 1 output
- 4 hidden neurons
- Sigmoid activation
- Solves classic perceptron limitation

See `XORExample.java` for complete implementation.

### 2. Iris Classification (Multi-Class Classification)

The Iris dataset is a classic machine learning benchmark for classification.

**Key Features:**
- 4 input features
- 3 output classes
- Multi-layer architecture (8 → 4 → 3)
- ReLU hidden layers
- Data normalization
- Train/test split

See `IrisClassification.java` for complete implementation.

## Mathematical Foundation

### Forward Propagation

For each layer:
1. Compute weighted sum: `z = W × input + b`
2. Apply activation: `a = σ(z)`
3. Pass to next layer

### Backpropagation

Based on Lec13-NN-BackPropagation.pdf:

**Output Layer Error:**
```
δ_output = a × (1 - a) × (target - a)
```

**Hidden Layer Error:**
```
δ_hidden = a × (1 - a) × Σ(δ_next × W_next)
```

**Weight Update:**
```
W_new = W_old + η × δ × input
```

### Activation Derivatives

- **Sigmoid**: `σ'(x) = σ(x) × (1 - σ(x))`
- **ReLU**: `f'(x) = 1 if x > 0, else 0`
- **Tanh**: `tanh'(x) = 1 - tanh²(x)`
- **Linear**: `f'(x) = 1`

## Design Principles

### Clean Code
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Easy to extend with new activations, layers, etc.
- **Dependency Inversion**: Depend on abstractions (interfaces)
- **Descriptive Naming**: Clear, self-documenting code

### Modularity
- Independent components
- Easy to test
- Reusable across projects

### Extensibility
- Add new activation functions by implementing `ActivationFunction`
- Add new layers by extending `Layer`
- Add new loss functions by implementing `LossFunction`
- Add new initializers by implementing `WeightInitializer`

## Advanced Usage

### Custom Network Architectures

```java
NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(784)
    .addDenseLayer(128, new ReLUActivation(), new HeInitializer())
    .addDenseLayer(64, new ReLUActivation(), new HeInitializer())
    .addDenseLayer(32, new ReLUActivation(), new HeInitializer())
    .addOutputLayer(10, new SigmoidActivation())
    .setLearningRate(0.001)
    .setEpochs(100)
    .setBatchSize(32)
    .setLossFunction(new CrossEntropyLoss())
    .setEarlyStopping(true)
    .setEarlyStoppingParams(10, 0.001)
    .build();
```

### Training with Validation

```java
Dataset[] splits = splitter.trainValTestSplit(dataset, 0.7, 0.15, true);
Dataset trainSet = splits[0];
Dataset valSet = splits[1];
Dataset testSet = splits[2];

TrainingHistory history = trainer.train(
    trainSet.getFeatures(),
    trainSet.getLabels(),
    valSet.getFeatures(),
    valSet.getLabels()
);
```

## Performance Tips

1. **Choose appropriate activation functions:**
   - Use ReLU for hidden layers (faster training)
   - Use Sigmoid for binary classification output
   - Use Linear for regression output

2. **Select proper initialization:**
   - Xavier for Sigmoid/Tanh
   - He for ReLU
   - Affects convergence speed significantly

3. **Normalize your data:**
   - Ensures features on similar scales
   - Improves training stability
   - Faster convergence

4. **Tune learning rate:**
   - Too high: unstable, may diverge
   - Too low: slow convergence
   - Typical range: 0.001 - 0.1

5. **Use mini-batch gradient descent:**
   - Batch size 32-128 usually works well
   - Balances speed and stability
   - Provides regularization effect

6. **Monitor training:**
   - Use validation set to detect overfitting
   - Enable early stopping
   - Check for vanishing/exploding gradients

## References

- Lecture slides: Lec12-NN-Intro1.pdf (Network structure, forward propagation)
- Lecture slides: Lec13-NN-BackPropagation.pdf (Backpropagation algorithm)
- Glorot & Bengio (2010): Xavier initialization
- He et al. (2015): He initialization for ReLU

## API Documentation

For detailed API documentation, see the Javadoc comments in each class.

## Examples

Run the provided examples to see the library in action:

1. **XOR Problem**: `ann.examples.XORExample`
2. **Iris Classification**: `ann.examples.IrisClassification`

Both examples include detailed comments explaining design decisions and demonstrating best practices.

