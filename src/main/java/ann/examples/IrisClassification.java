package src.main.java.ann.examples;

import src.main.java.ann.activation.ReLUActivation;
import src.main.java.ann.activation.SigmoidActivation;
import src.main.java.ann.core.NetworkInspector;
import src.main.java.ann.data.DataNormalizer;
import src.main.java.ann.data.DataSplitter;
import src.main.java.ann.data.Dataset;
import src.main.java.ann.evaluation.Evaluator;
import src.main.java.ann.evaluation.Predictor;
import src.main.java.ann.initialization.HeInitializer;
import src.main.java.ann.loss.MSELoss;
import src.main.java.ann.network.NetworkBuilder;
import src.main.java.ann.network.NeuralNetwork;
import src.main.java.ann.training.Trainer;
import src.main.java.ann.training.TrainingConfig;

/**
 * Iris Classification - Multi-Class Classification Case Study
 * 
 * The Iris dataset is a classic machine learning dataset containing measurements
 * of iris flowers from three species. This example demonstrates:
 * - Multi-class classification
 * - Data normalization
 * - Train/test splitting
 * - ReLU activations in hidden layers
 * - Model evaluation
 * 
 * Dataset:
 * - 150 samples (50 per class)
 * - 4 features: sepal length, sepal width, petal length, petal width
 * - 3 classes: Setosa (0), Versicolor (1), Virginica (2)
 * 
 * Network Architecture:
 * - Input Layer: 4 neurons (4 features)
 * - Hidden Layer 1: 8 neurons with ReLU activation
 * - Hidden Layer 2: 4 neurons with ReLU activation
 * - Output Layer: 3 neurons with Sigmoid activation (one-hot encoding)
 * 
 * Training Details:
 * - Loss Function: MSE
 * - Learning Rate: 0.01
 * - Epochs: 1000
 * - Batch Size: 16
 * - Data Normalization: Min-Max scaling to [0, 1]
 * - Train/Test Split: 80/20
 */
public class IrisClassification {
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("Neural Network Case Study: Iris Classification");
        System.out.println("=================================================\n");
        
        // Simplified Iris dataset (subset for demonstration)
        // In a real application, load from CSV or database
        double[][] features = {
            // Setosa (class 0) - 10 samples
            {5.1, 3.5, 1.4, 0.2}, {4.9, 3.0, 1.4, 0.2}, {4.7, 3.2, 1.3, 0.2},
            {4.6, 3.1, 1.5, 0.2}, {5.0, 3.6, 1.4, 0.2}, {5.4, 3.9, 1.7, 0.4},
            {4.6, 3.4, 1.4, 0.3}, {5.0, 3.4, 1.5, 0.2}, {4.4, 2.9, 1.4, 0.2},
            {4.9, 3.1, 1.5, 0.1},
            
            // Versicolor (class 1) - 10 samples
            {7.0, 3.2, 4.7, 1.4}, {6.4, 3.2, 4.5, 1.5}, {6.9, 3.1, 4.9, 1.5},
            {5.5, 2.3, 4.0, 1.3}, {6.5, 2.8, 4.6, 1.5}, {5.7, 2.8, 4.5, 1.3},
            {6.3, 3.3, 4.7, 1.6}, {4.9, 2.4, 3.3, 1.0}, {6.6, 2.9, 4.6, 1.3},
            {5.2, 2.7, 3.9, 1.4},
            
            // Virginica (class 2) - 10 samples
            {6.3, 3.3, 6.0, 2.5}, {5.8, 2.7, 5.1, 1.9}, {7.1, 3.0, 5.9, 2.1},
            {6.3, 2.9, 5.6, 1.8}, {6.5, 3.0, 5.8, 2.2}, {7.6, 3.0, 6.6, 2.1},
            {4.9, 2.5, 4.5, 1.7}, {7.3, 2.9, 6.3, 1.8}, {6.7, 2.5, 5.8, 1.8},
            {7.2, 3.6, 6.1, 2.5}
        };
        
        // One-hot encoded labels
        double[][] labels = {
            // Setosa (10 samples)
            {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0},
            {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0},
            
            // Versicolor (10 samples)
            {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0},
            {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0},
            
            // Virginica (10 samples)
            {0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1},
            {0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}
        };
        
        System.out.println("Dataset: 30 Iris samples (10 per class)");
        System.out.println("Features: 4 (sepal length, sepal width, petal length, petal width)");
        System.out.println("Classes: 3 (Setosa, Versicolor, Virginica)\n");
        
        // Normalize features
        System.out.println("Normalizing features using Min-Max scaling...");
        DataNormalizer normalizer = new DataNormalizer(DataNormalizer.Strategy.MIN_MAX);
        double[][] normalizedFeatures = normalizer.fitTransform(features);
        
        // Create dataset and split
        Dataset dataset = new Dataset(normalizedFeatures, labels);
        DataSplitter splitter = new DataSplitter(42);  // Seed for reproducibility
        Dataset[] splits = splitter.trainTestSplit(dataset, 0.8, true);
        
        Dataset trainDataset = splits[0];
        Dataset testDataset = splits[1];
        
        System.out.printf("Train set: %d samples\n", trainDataset.getSize());
        System.out.printf("Test set: %d samples\n\n", testDataset.getSize());
        
        // Build the neural network
        System.out.println("Building network...");
        NeuralNetwork network = new NetworkBuilder()
            .addInputLayer(4)
            .addDenseLayer(8, new ReLUActivation(), new HeInitializer())
            .addDenseLayer(4, new ReLUActivation(), new HeInitializer())
            .addOutputLayer(3, new SigmoidActivation())
            .setLearningRate(0.01)
            .setEpochs(1000)
            .setBatchSize(8)
            .setLossFunction(new MSELoss())
            .build();
        
        // Inspect network
        NetworkInspector inspector = new NetworkInspector(network);
        inspector.printArchitecture();
        
        // Configure training
        TrainingConfig config = new TrainingConfig();
        config.setLearningRate(0.01);
        config.setEpochs(1000);
        config.setBatchSize(8);
        config.setShuffle(true);
        config.setVerbose(true);
        config.setPrintEveryNEpochs(200);
        
        // Train
        System.out.println("\nTraining network...");
        Trainer trainer = new Trainer(network, config, 42);
        trainer.train(
            trainDataset.getFeatures(), 
            trainDataset.getLabels()
        );
        
        // Evaluate on test set
        System.out.println("\n=== Evaluation on Test Set ===");
        String evalResults = Evaluator.evaluate(
            network,
            testDataset.getFeatures(),
            testDataset.getLabels()
        );
        System.out.println(evalResults);
        
        // Make predictions
        System.out.println("=== Sample Predictions ===");
        Predictor predictor = new Predictor(network);
        
        String[] classNames = {"Setosa", "Versicolor", "Virginica"};
        
        for (int i = 0; i < Math.min(5, testDataset.getSize()); i++) {
            double[] input = testDataset.getFeatures()[i];
            double[] target = testDataset.getLabels()[i];
            double[] prediction = predictor.predict(input);
            
            int predictedClass = getMaxIndex(prediction);
            int actualClass = getMaxIndex(target);
            
            System.out.printf("\nSample %d:\n", i + 1);
            System.out.printf("  Features: [%.2f, %.2f, %.2f, %.2f]\n",
                              input[0], input[1], input[2], input[3]);
            System.out.printf("  Predicted: %s (%.4f)\n", 
                              classNames[predictedClass], prediction[predictedClass]);
            System.out.printf("  Actual: %s\n", classNames[actualClass]);
            System.out.printf("  Correct: %s\n", predictedClass == actualClass ? "✓" : "✗");
        }
        
        // Summary
        System.out.println("\n=== Summary ===");
        System.out.println("Successfully trained a neural network for Iris classification!");
        System.out.println("\nNetwork Design Justification:");
        System.out.println("- Input Size (4): Four flower measurements");
        System.out.println("- Hidden Layer 1 (8 neurons, ReLU): Learns complex feature combinations");
        System.out.println("- Hidden Layer 2 (4 neurons, ReLU): Refines features for classification");
        System.out.println("- Output Layer (3 neurons, Sigmoid): One-hot encoding for 3 classes");
        System.out.println("- ReLU Activation: Prevents vanishing gradient, faster training");
        System.out.println("- He Initialization: Optimal for ReLU activations");
        System.out.println("- Data Normalization: Ensures features are on similar scales");
        System.out.println("- Learning Rate (0.01): Balanced for stable convergence");
        
        System.out.println("\n=================================================");
    }
    
    private static int getMaxIndex(double[] array) {
        int maxIndex = 0;
        double maxValue = array[0];
        
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        
        return maxIndex;
    }
}

