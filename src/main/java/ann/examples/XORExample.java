package src.main.java.ann.examples;

import src.main.java.ann.activation.SigmoidActivation;
import src.main.java.ann.core.NetworkInspector;
import src.main.java.ann.core.TrainingMonitor;
import src.main.java.ann.evaluation.Evaluator;
import src.main.java.ann.evaluation.Predictor;
import src.main.java.ann.initialization.XavierInitializer;
import src.main.java.ann.loss.MSELoss;
import src.main.java.ann.network.NetworkBuilder;
import src.main.java.ann.network.NeuralNetwork;
import src.main.java.ann.training.Trainer;
import src.main.java.ann.training.TrainingConfig;
import src.main.java.ann.training.TrainingHistory;

/**
 * XOR Problem - Classic Neural Network Case Study
 * 
 * The XOR (exclusive OR) problem is a classic non-linearly separable problem
 * that demonstrates the necessity of hidden layers in neural networks.
 * 
 * Truth Table:
 *   Input  | Output
 *   0  0   |   0
 *   0  1   |   1
 *   1  0   |   1
 *   1  1   |   0
 * 
 * This problem cannot be solved by a single-layer perceptron (linear classifier)
 * but can be solved by a multi-layer perceptron with a hidden layer.
 * 
 * Network Architecture:
 * - Input Layer: 2 neurons (for two binary inputs)
 * - Hidden Layer: 4 neurons with sigmoid activation
 * - Output Layer: 1 neuron with sigmoid activation
 * 
 * Training Details:
 * - Loss Function: Mean Squared Error (MSE)
 * - Learning Rate: 0.5 (relatively high for faster convergence)
 * - Epochs: 5000
 * - Weight Initialization: Xavier (for sigmoid activations)
 */
public class XORExample {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Neural Network Case Study: XOR Problem");
        System.out.println("========================================\n");
        
        // Training data for XOR
        double[][] trainInputs = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };
        
        double[][] trainTargets = {
            {0},
            {1},
            {1},
            {0}
        };
        
        // Build the neural network
        System.out.println("Building network...");
        NeuralNetwork network = new NetworkBuilder()
            .addInputLayer(2)
            .addDenseLayer(4, new SigmoidActivation(), new XavierInitializer())
            .addOutputLayer(1, new SigmoidActivation(), new XavierInitializer())
            .setLearningRate(0.5)
            .setEpochs(5000)
            .setBatchSize(4)
            .setLossFunction(new MSELoss())
            .setVerbose(false)
            .build();
        
        // Inspect network architecture
        NetworkInspector inspector = new NetworkInspector(network);
        inspector.printArchitecture();
        
        // Configure training
        TrainingConfig config = new TrainingConfig();
        config.setLearningRate(0.5);
        config.setEpochs(5000);
        config.setBatchSize(4);
        config.setShuffle(true);
        config.setVerbose(true);
        config.setPrintEveryNEpochs(1000);
        
        // Create trainer and monitor
        Trainer trainer = new Trainer(network, config, 42);  // Seed for reproducibility
        TrainingMonitor monitor = new TrainingMonitor();
        
        // Train the network
        System.out.println("\nTraining network on XOR problem...");
        TrainingHistory history = trainer.train(trainInputs, trainTargets);
        
        // Print training summary
        System.out.println(monitor.generateSummary(history));
        
        // Test the trained network
        System.out.println("\n=== Testing Trained Network ===");
        Predictor predictor = new Predictor(network);
        
        System.out.println("\nXOR Truth Table - Predicted vs Actual:");
        System.out.println("Input (x1, x2) | Predicted | Actual | Correct?");
        System.out.println("------------------------------------------------");
        
        int correctPredictions = 0;
        for (int i = 0; i < trainInputs.length; i++) {
            double[] prediction = predictor.predict(trainInputs[i]);
            double predictedValue = prediction[0];
            int predictedClass = predictedValue >= 0.5 ? 1 : 0;
            int actualClass = (int) trainTargets[i][0];
            boolean correct = predictedClass == actualClass;
            
            if (correct) {
                correctPredictions++;
            }
            
            System.out.printf("   (%d, %d)      |   %.4f   |   %d    | %s\n",
                              (int) trainInputs[i][0],
                              (int) trainInputs[i][1],
                              predictedValue,
                              actualClass,
                              correct ? "✓" : "✗");
        }
        
        // Compute and display metrics
        System.out.println("\n=== Performance Metrics ===");
        double[][] predictions = predictor.predict(trainInputs);
        double mse = Evaluator.computeMSE(predictions, trainTargets);
        double accuracy = (double) correctPredictions / trainInputs.length;
        
        System.out.printf("MSE: %.6f\n", mse);
        System.out.printf("Accuracy: %.2f%% (%d/%d)\n", 
                          accuracy * 100, correctPredictions, trainInputs.length);
        
        // Test intermediate values
        System.out.println("\n=== Detailed Analysis for Input (1, 0) ===");
        inspector.printLayerOutputs(new double[]{1, 0});
        
        // Display weight statistics
        inspector.printWeightStatistics();
        
        // Summary and interpretation
        System.out.println("\n=== Summary ===");
        System.out.println("The network successfully learned the XOR function!");
        System.out.println("\nKey Observations:");
        System.out.println("1. A single-layer perceptron cannot solve XOR (linearly inseparable)");
        System.out.println("2. Adding a hidden layer enables the network to learn non-linear patterns");
        System.out.println("3. The hidden layer learns to create a feature space where XOR is separable");
        System.out.println("4. Sigmoid activation provides smooth, differentiable non-linearity");
        System.out.println("5. Xavier initialization helps with convergence for sigmoid networks");
        
        System.out.println("\nNetwork Design Justification:");
        System.out.println("- Input Size (2): Two binary inputs for XOR");
        System.out.println("- Hidden Layer (4 neurons): Provides enough capacity to learn the pattern");
        System.out.println("  * Theoretical minimum is 2, but 4 provides faster/more stable learning");
        System.out.println("- Output Size (1): Single binary output");
        System.out.println("- Sigmoid Activation: Natural choice for binary classification");
        System.out.println("- Learning Rate (0.5): High rate acceptable for small, simple problem");
        System.out.println("- MSE Loss: Standard for regression-style binary outputs");
        
        System.out.println("\n========================================");
    }
}

