package src.main.java.ann.core;

import src.main.java.ann.layer.DenseLayer;
import src.main.java.ann.layer.Layer;
import src.main.java.ann.network.NeuralNetwork;

/**
 * Network inspector for debugging and visualization.
 * Provides access to internal network state during training and inference.
 */
public class NetworkInspector {
    
    private NeuralNetwork network;
    
    /**
     * Creates an inspector for a neural network.
     * 
     * @param network the network to inspect
     */
    public NetworkInspector(NeuralNetwork network) {
        this.network = network;
    }
    
    /**
     * Prints a summary of the network architecture.
     */
    public void printArchitecture() {
        System.out.println("=== Network Architecture ===");
        System.out.println(network.toString());
        
        int totalParams = 0;
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            
            if (layer instanceof DenseLayer) {
                int layerParams = layer.getInputSize() * layer.getOutputSize() + layer.getOutputSize();
                totalParams += layerParams;
                
                System.out.printf("\nLayer %d: %s\n", i, layer);
                System.out.printf("  Parameters: %d (weights: %d, biases: %d)\n", 
                                  layerParams, 
                                  layer.getInputSize() * layer.getOutputSize(),
                                  layer.getOutputSize());
            }
        }
        
        System.out.printf("\nTotal Parameters: %d\n", totalParams);
    }
    
    /**
     * Prints the outputs of all layers for a given input.
     * 
     * @param input input vector
     */
    public void printLayerOutputs(double[] input) {
        System.out.println("\n=== Layer Outputs ===");
        System.out.println("Input: " + formatVector(input));
        
        double[] activation = input;
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            activation = layer.forward(activation);
            
            System.out.printf("\nLayer %d Output: %s\n", i, formatVector(activation));
        }
    }
    
    /**
     * Prints the weights of all layers.
     */
    public void printWeights() {
        System.out.println("\n=== Network Weights ===");
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            double[][] weights = layer.getWeights();
            
            if (weights != null) {
                System.out.printf("\nLayer %d Weights:\n", i);
                printMatrix(weights, 5, 5);  // Print first 5x5
            }
        }
    }
    
    /**
     * Prints the biases of all layers.
     */
    public void printBiases() {
        System.out.println("\n=== Network Biases ===");
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            double[] biases = layer.getBiases();
            
            if (biases != null) {
                System.out.printf("\nLayer %d Biases: %s\n", i, formatVector(biases, 10));
            }
        }
    }
    
    /**
     * Prints the gradient magnitudes for debugging gradient flow.
     */
    public void printGradientMagnitudes() {
        System.out.println("\n=== Gradient Magnitudes ===");
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            
            if (layer instanceof DenseLayer) {
                DenseLayer denseLayer = (DenseLayer) layer;
                double[][] weightGrads = denseLayer.getWeightGradients();
                double[] biasGrads = denseLayer.getBiasGradients();
                
                double weightGradMag = computeMagnitude(weightGrads);
                double biasGradMag = computeMagnitude(biasGrads);
                
                System.out.printf("Layer %d: Weight Grad Mag = %.6e, Bias Grad Mag = %.6e\n",
                                  i, weightGradMag, biasGradMag);
            }
        }
    }
    
    /**
     * Computes statistics about network weights.
     */
    public void printWeightStatistics() {
        System.out.println("\n=== Weight Statistics ===");
        
        for (int i = 0; i < network.getLayerCount(); i++) {
            Layer layer = network.getLayer(i);
            double[][] weights = layer.getWeights();
            
            if (weights != null) {
                double[] stats = computeStatistics(weights);
                System.out.printf("Layer %d: min=%.4f, max=%.4f, mean=%.4f, std=%.4f\n",
                                  i, stats[0], stats[1], stats[2], stats[3]);
            }
        }
    }
    
    /**
     * Computes the magnitude (L2 norm) of a matrix.
     */
    private double computeMagnitude(double[][] matrix) {
        double sum = 0.0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j] * matrix[i][j];
            }
        }
        return Math.sqrt(sum);
    }
    
    /**
     * Computes the magnitude (L2 norm) of a vector.
     */
    private double computeMagnitude(double[] vector) {
        double sum = 0.0;
        for (double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }
    
    /**
     * Computes statistics (min, max, mean, std) for a matrix.
     */
    private double[] computeStatistics(double[][] matrix) {
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        double sum = 0.0;
        int count = 0;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double val = matrix[i][j];
                min = Math.min(min, val);
                max = Math.max(max, val);
                sum += val;
                count++;
            }
        }
        
        double mean = sum / count;
        
        double variance = 0.0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double diff = matrix[i][j] - mean;
                variance += diff * diff;
            }
        }
        double std = Math.sqrt(variance / count);
        
        return new double[] { min, max, mean, std };
    }
    
    /**
     * Formats a vector for printing.
     */
    private String formatVector(double[] vector) {
        return formatVector(vector, vector.length);
    }
    
    /**
     * Formats a vector for printing (limited elements).
     */
    private String formatVector(double[] vector, int maxElements) {
        StringBuilder sb = new StringBuilder("[");
        int limit = Math.min(vector.length, maxElements);
        
        for (int i = 0; i < limit; i++) {
            sb.append(String.format("%.4f", vector[i]));
            if (i < limit - 1) {
                sb.append(", ");
            }
        }
        
        if (vector.length > maxElements) {
            sb.append(", ...");
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Prints a matrix (limited to maxRows x maxCols).
     */
    private void printMatrix(double[][] matrix, int maxRows, int maxCols) {
        int rows = Math.min(matrix.length, maxRows);
        int cols = matrix.length > 0 ? Math.min(matrix[0].length, maxCols) : 0;
        
        for (int i = 0; i < rows; i++) {
            System.out.print("  [");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%7.4f", matrix[i][j]);
                if (j < cols - 1) {
                    System.out.print(", ");
                }
            }
            if (matrix[i].length > maxCols) {
                System.out.print(", ...");
            }
            System.out.println("]");
        }
        
        if (matrix.length > maxRows) {
            System.out.println("  ...");
        }
    }
}

