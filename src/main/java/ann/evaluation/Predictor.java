package src.main.java.ann.evaluation;

import src.main.java.ann.network.NeuralNetwork;

/**
 * Predictor class for making predictions with trained networks.
 * Provides convenient methods for various prediction tasks.
 */
public class Predictor {
    
    private NeuralNetwork network;
    
    /**
     * Creates a predictor for a neural network.
     * 
     * @param network the trained neural network
     */
    public Predictor(NeuralNetwork network) {
        this.network = network;
    }
    
    /**
     * Makes a prediction for a single input.
     * 
     * @param input input vector
     * @return predicted output vector
     */
    public double[] predict(double[] input) {
        return network.predict(input);
    }
    
    /**
     * Makes predictions for multiple inputs.
     * 
     * @param inputs array of input vectors
     * @return array of predicted output vectors
     */
    public double[][] predict(double[][] inputs) {
        return network.predict(inputs);
    }
    
    /**
     * Predicts class labels for binary classification.
     * 
     * @param input input vector
     * @param threshold classification threshold (default 0.5)
     * @return predicted class (0 or 1)
     */
    public int predictClass(double[] input, double threshold) {
        double[] output = network.predict(input);
        
        // For single output
        if (output.length == 1) {
            return output[0] >= threshold ? 1 : 0;
        }
        
        // For multi-output, return index of maximum
        return getMaxIndex(output);
    }
    
    /**
     * Predicts class labels with default threshold of 0.5.
     * 
     * @param input input vector
     * @return predicted class
     */
    public int predictClass(double[] input) {
        return predictClass(input, 0.5);
    }
    
    /**
     * Predicts class labels for multiple inputs.
     * 
     * @param inputs array of input vectors
     * @param threshold classification threshold
     * @return array of predicted class labels
     */
    public int[] predictClasses(double[][] inputs, double threshold) {
        int[] classes = new int[inputs.length];
        
        for (int i = 0; i < inputs.length; i++) {
            classes[i] = predictClass(inputs[i], threshold);
        }
        
        return classes;
    }
    
    /**
     * Predicts class labels for multiple inputs with default threshold.
     * 
     * @param inputs array of input vectors
     * @return array of predicted class labels
     */
    public int[] predictClasses(double[][] inputs) {
        return predictClasses(inputs, 0.5);
    }
    
    /**
     * Predicts class probabilities (same as raw output for sigmoid/softmax).
     * 
     * @param input input vector
     * @return predicted probabilities
     */
    public double[] predictProbabilities(double[] input) {
        return network.predict(input);
    }
    
    /**
     * Predicts class probabilities for multiple inputs.
     * 
     * @param inputs array of input vectors
     * @return array of predicted probability vectors
     */
    public double[][] predictProbabilities(double[][] inputs) {
        return network.predict(inputs);
    }
    
    /**
     * Gets the index of the maximum value in an array.
     * 
     * @param array input array
     * @return index of maximum value
     */
    private int getMaxIndex(double[] array) {
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
    
    /**
     * Gets the neural network being used for prediction.
     * 
     * @return neural network
     */
    public NeuralNetwork getNetwork() {
        return network;
    }
}

