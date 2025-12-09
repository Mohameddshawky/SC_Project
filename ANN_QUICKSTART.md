# ANN Library Quick Start Guide

## Overview

This guide will help you get started with the Artificial Neural Network (ANN) library quickly.

## Basic Example: XOR Problem

The XOR problem is the simplest way to understand how to use the library.

### Step 1: Prepare Your Data

```java
// Training inputs (XOR truth table inputs)
double[][] trainInputs = {
    {0, 0},
    {0, 1},
    {1, 0},
    {1, 1}
};

// Training targets (XOR truth table outputs)
double[][] trainTargets = {
    {0},
    {1},
    {1},
    {0}
};
```

### Step 2: Build Your Network

```java
import src.main.java.ann.activation.SigmoidActivation;
import src.main.java.ann.initialization.XavierInitializer;
import src.main.java.ann.loss.MSELoss;
import src.main.java.ann.network.NetworkBuilder;
import src.main.java.ann.network.NeuralNetwork;

// Build a simple network: 2 inputs → 4 hidden → 1 output
NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(2)                                    // 2 inputs
    .addDenseLayer(4, new SigmoidActivation())          // 4 hidden neurons
    .addOutputLayer(1, new SigmoidActivation())         // 1 output
    .setLearningRate(0.5)                               // Learning rate
    .setEpochs(5000)                                    // Training epochs
    .setLossFunction(new MSELoss())                     // Loss function
    .build();
```

### Step 3: Train Your Network

```java
import src.main.java.ann.training.Trainer;
import src.main.java.ann.training.TrainingConfig;

// Configure training
TrainingConfig config = new TrainingConfig();
config.setLearningRate(0.5);
config.setEpochs(5000);
config.setBatchSize(4);
config.setVerbose(true);

// Create trainer and train
Trainer trainer = new Trainer(network, config);
trainer.train(trainInputs, trainTargets);
```

### Step 4: Make Predictions

```java
import src.main.java.ann.evaluation.Predictor;

Predictor predictor = new Predictor(network);

// Predict for input [1, 0]
double[] prediction = predictor.predict(new double[]{1, 0});
System.out.println("XOR(1, 0) = " + prediction[0]);  // Should be close to 1
```

## Complete Example: Classification Problem

### Step 1: Prepare and Normalize Data

```java
import src.main.java.ann.data.Dataset;
import src.main.java.ann.data.DataNormalizer;
import src.main.java.ann.data.DataSplitter;

// Your raw data
double[][] features = {
    {5.1, 3.5, 1.4, 0.2},
    {7.0, 3.2, 4.7, 1.4},
    // ... more data
};

double[][] labels = {
    {1, 0, 0},  // Class 0
    {0, 1, 0},  // Class 1
    // ... more labels
};

// Normalize features
DataNormalizer normalizer = new DataNormalizer(DataNormalizer.Strategy.MIN_MAX);
double[][] normalizedFeatures = normalizer.fitTransform(features);

// Split into train/test
Dataset dataset = new Dataset(normalizedFeatures, labels);
DataSplitter splitter = new DataSplitter();
Dataset[] splits = splitter.trainTestSplit(dataset, 0.8, true);

Dataset trainSet = splits[0];
Dataset testSet = splits[1];
```

### Step 2: Build a Deeper Network

```java
import src.main.java.ann.activation.ReLUActivation;
import src.main.java.ann.initialization.HeInitializer;

NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(4)                                          // 4 features
    .addDenseLayer(8, new ReLUActivation(), new HeInitializer())    // Hidden layer 1
    .addDenseLayer(4, new ReLUActivation(), new HeInitializer())    // Hidden layer 2
    .addOutputLayer(3, new SigmoidActivation())                     // 3 classes
    .setLearningRate(0.01)
    .setEpochs(1000)
    .setBatchSize(16)
    .build();
```

### Step 3: Train with Validation

```java
TrainingConfig config = new TrainingConfig();
config.setLearningRate(0.01);
config.setEpochs(1000);
config.setBatchSize(16);
config.setVerbose(true);
config.setPrintEveryNEpochs(100);

Trainer trainer = new Trainer(network, config);

// Train with validation set for monitoring
trainer.train(
    trainSet.getFeatures(),
    trainSet.getLabels(),
    testSet.getFeatures(),
    testSet.getLabels()
);
```

### Step 4: Evaluate Performance

```java
import src.main.java.ann.evaluation.Evaluator;

String results = Evaluator.evaluate(
    network,
    testSet.getFeatures(),
    testSet.getLabels()
);

System.out.println(results);
```

## Common Patterns

### Pattern 1: Binary Classification

```java
NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(inputSize)
    .addDenseLayer(hiddenSize, new ReLUActivation())
    .addOutputLayer(1, new SigmoidActivation())        // 1 output for binary
    .setLearningRate(0.01)
    .setLossFunction(new MSELoss())
    .build();
```

### Pattern 2: Multi-Class Classification

```java
NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(inputSize)
    .addDenseLayer(hiddenSize1, new ReLUActivation())
    .addDenseLayer(hiddenSize2, new ReLUActivation())
    .addOutputLayer(numClasses, new SigmoidActivation())  // One-hot encoding
    .setLearningRate(0.01)
    .setLossFunction(new CrossEntropyLoss())
    .build();
```

### Pattern 3: Regression

```java
import src.main.java.ann.activation.LinearActivation;

NeuralNetwork network = new NetworkBuilder()
    .addInputLayer(inputSize)
    .addDenseLayer(hiddenSize, new ReLUActivation())
    .addOutputLayer(1, new LinearActivation())         // Linear for regression
    .setLearningRate(0.001)
    .setLossFunction(new MSELoss())
    .build();
```

## Debugging Your Network

### Inspect Architecture

```java
import src.main.java.ann.core.NetworkInspector;

NetworkInspector inspector = new NetworkInspector(network);
inspector.printArchitecture();           // Print layer details
inspector.printWeightStatistics();       // Check weight distributions
```

### Monitor Training

```java
import src.main.java.ann.core.TrainingMonitor;

TrainingMonitor monitor = new TrainingMonitor();

// During training loop
monitor.recordLoss(loss);

// Check status
if (monitor.hasVanishingGradient()) {
    System.out.println("Warning: Vanishing gradient detected!");
}

if (monitor.hasExplodingGradient()) {
    System.out.println("Warning: Exploding gradient detected!");
}
```

### Visualize Layer Outputs

```java
// See what each layer outputs for a given input
inspector.printLayerOutputs(new double[]{1.0, 0.5});
```

## Configuration Tips

### Learning Rate Selection

- **Too High (> 0.5)**: Training unstable, loss may increase
- **Good Range (0.001 - 0.1)**: Stable training
- **Too Low (< 0.0001)**: Very slow convergence

```java
.setLearningRate(0.01)  // Good starting point
```

### Batch Size Selection

- **Small (1-16)**: More noise, slower training, better generalization
- **Medium (16-64)**: Balanced
- **Large (64-256)**: Faster training, more stable, needs more memory

```java
.setBatchSize(32)  // Good default
```

### Activation Function Selection

- **Hidden Layers**: Use `ReLUActivation()` (prevents vanishing gradient)
- **Binary Output**: Use `SigmoidActivation()` (outputs 0-1)
- **Regression Output**: Use `LinearActivation()` (unbounded output)
- **Zero-Centered**: Use `TanhActivation()` (outputs -1 to 1)

### Weight Initialization Selection

- **With ReLU**: Use `HeInitializer()`
- **With Sigmoid/Tanh**: Use `XavierInitializer()`
- **Default**: Use `XavierInitializer()`

```java
.addDenseLayer(64, new ReLUActivation(), new HeInitializer())
```

## Troubleshooting

### Problem: Network not learning (loss not decreasing)

**Solutions:**
1. Check learning rate (try 0.01 or 0.001)
2. Normalize your input data
3. Increase number of epochs
4. Try different initialization (Xavier vs He)
5. Check if network has enough capacity (add more neurons)

### Problem: Loss is NaN

**Solutions:**
1. Reduce learning rate
2. Check for invalid input data (NaN, Inf)
3. Use gradient clipping
4. Normalize input features

### Problem: Overfitting (high train accuracy, low test accuracy)

**Solutions:**
1. Get more training data
2. Reduce network size (fewer neurons/layers)
3. Implement early stopping
4. Use validation set to monitor

### Problem: Very slow training

**Solutions:**
1. Use ReLU instead of Sigmoid in hidden layers
2. Increase batch size
3. Use better initialization (He for ReLU)
4. Normalize input data

## Common Mistakes to Avoid

1. **Not normalizing data**: Always normalize inputs to similar ranges
2. **Wrong activation for output**: Use Sigmoid for binary, Linear for regression
3. **Wrong initialization**: Use He with ReLU, Xavier with Sigmoid
4. **Learning rate too high**: Start with 0.01 and adjust
5. **Not enough epochs**: Some problems need 1000+ epochs
6. **Batch size = 1**: Usually too noisy, try 16-32
7. **Not shuffling data**: Can lead to poor learning
8. **Forgetting to split data**: Always use separate train/test sets

## Running the Examples

### XOR Example
```bash
cd "D:\Soft Computing\Soft Computing Project"
javac src/main/java/ann/examples/XORExample.java
java src.main.java.ann.examples.XORExample
```

### Iris Classification Example
```bash
javac src/main/java/ann/examples/IrisClassification.java
java src.main.java.ann.examples.IrisClassification
```

## Next Steps

1. **Read the full README**: `src/main/java/ann/README.md`
2. **Study the examples**: `ann/examples/XORExample.java` and `IrisClassification.java`
3. **Experiment**: Try different architectures, learning rates, activations
4. **Build your own**: Apply to your own datasets and problems

## Quick Reference

### Imports You'll Need

```java
// Core
import src.main.java.ann.network.NetworkBuilder;
import src.main.java.ann.network.NeuralNetwork;

// Activations
import src.main.java.ann.activation.*;

// Loss
import src.main.java.ann.loss.*;

// Training
import src.main.java.ann.training.Trainer;
import src.main.java.ann.training.TrainingConfig;

// Evaluation
import src.main.java.ann.evaluation.Predictor;
import src.main.java.ann.evaluation.Evaluator;

// Data
import src.main.java.ann.data.*;

// Debugging
import src.main.java.ann.core.NetworkInspector;
import src.main.java.ann.core.TrainingMonitor;

// Initialization
import src.main.java.ann.initialization.*;
```

### Minimal Working Example

```java
// Data
double[][] X = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
double[][] y = {{0}, {1}, {1}, {0}};

// Network
NeuralNetwork net = new NetworkBuilder()
    .addInputLayer(2)
    .addDenseLayer(4, new SigmoidActivation())
    .addOutputLayer(1, new SigmoidActivation())
    .setLearningRate(0.5)
    .setEpochs(5000)
    .build();

// Train
new Trainer(net, new TrainingConfig()).train(X, y);

// Predict
double[] pred = new Predictor(net).predict(new double[]{1, 0});
System.out.println(pred[0]);
```

## Happy Training! 🚀

