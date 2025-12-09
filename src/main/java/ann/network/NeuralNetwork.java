package src.main.java.ann.network;

import src.main.java.ann.layer.Layer;
import src.main.java.ann.loss.LossFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Main neural network class.
 * Manages layers and coordinates forward/backward passes.
 * 
 * Architecture:
 * - Input layer (implicit, defined by first layer's input size)
 * - Hidden layers (zero or more)
 * - Output layer
 */
public class NeuralNetwork {
    
    private List<Layer> layers;
    private NetworkConfig config;
    
    // Training history
    private List<Double> trainingLossHistory;
    private List<Double> validationLossHistory;
    
    /**
     * Creates a neural network with default configuration.
     */
    public NeuralNetwork() {
        this(new NetworkConfig());
    }
    
    /**
     * Creates a neural network with custom configuration.
     * 
     * @param config network configuration
     */
    public NeuralNetwork(NetworkConfig config) {
        this.layers = new ArrayList<>();
        this.config = config;
        this.trainingLossHistory = new ArrayList<>();
        this.validationLossHistory = new ArrayList<>();
    }
    
    /**
     * Adds a layer to the network.
     * 
     * @param layer layer to add
     */
    public void addLayer(Layer layer) {
        if (!layers.isEmpty()) {
            Layer lastLayer = layers.get(layers.size() - 1);
            if (lastLayer.getOutputSize() != layer.getInputSize()) {
                throw new IllegalArgumentException(
                    String.format("Layer dimension mismatch: previous output=%d, current input=%d",
                                  lastLayer.getOutputSize(), layer.getInputSize()));
            }
        }
        layers.add(layer);
    }
    
    /**
     * Forward pass: computes network output for given input.
     * 
     * @param input input vector
     * @return output vector
     */
    public double[] forward(double[] input) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Network has no layers");
        }
        
        double[] activation = input;
        
        // Pass through each layer sequentially
        for (Layer layer : layers) {
            activation = layer.forward(activation);
        }
        
        return activation;
    }
    
    /**
     * Backward pass: computes gradients and updates weights.
     * 
     * @param lossGradient gradient of loss with respect to output
     */
    public void backward(double[] lossGradient) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Network has no layers");
        }
        
        double[] gradient = lossGradient;
        
        // Backpropagate through layers in reverse order
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer layer = layers.get(i);
            gradient = layer.backward(gradient, config.getLearningRate());
        }
    }
    
    /**
     * Trains the network on a single example (online learning).
     * 
     * @param input input vector
     * @param target target output vector
     * @return loss value
     */
    public double trainOnExample(double[] input, double[] target) {
        // Forward pass
        double[] predicted = forward(input);
        
        // Compute loss
        LossFunction lossFunction = config.getLossFunction();
        double loss = lossFunction.compute(predicted, target);
        
        // Compute loss gradient
        double[] lossGradient = lossFunction.gradient(predicted, target);
        
        // Backward pass
        backward(lossGradient);
        
        return loss;
    }
    
    /**
     * Trains the network on a batch of examples.
     * 
     * @param inputs array of input vectors
     * @param targets array of target vectors
     * @return average loss for the batch
     */
    public double trainOnBatch(double[][] inputs, double[][] targets) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException(
                String.format("Input/target count mismatch: inputs=%d, targets=%d",
                              inputs.length, targets.length));
        }
        
        double totalLoss = 0.0;
        
        for (int i = 0; i < inputs.length; i++) {
            totalLoss += trainOnExample(inputs[i], targets[i]);
        }
        
        return totalLoss / inputs.length;
    }
    
    /**
     * Makes a prediction for a single input.
     * 
     * @param input input vector
     * @return predicted output vector
     */
    public double[] predict(double[] input) {
        return forward(input);
    }
    
    /**
     * Makes predictions for multiple inputs.
     * 
     * @param inputs array of input vectors
     * @return array of predicted output vectors
     */
    public double[][] predict(double[][] inputs) {
        double[][] predictions = new double[inputs.length][];
        
        for (int i = 0; i < inputs.length; i++) {
            predictions[i] = predict(inputs[i]);
        }
        
        return predictions;
    }
    
    /**
     * Evaluates the network on test data.
     * 
     * @param inputs test input vectors
     * @param targets test target vectors
     * @return average loss
     */
    public double evaluate(double[][] inputs, double[][] targets) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException(
                String.format("Input/target count mismatch: inputs=%d, targets=%d",
                              inputs.length, targets.length));
        }
        
        double totalLoss = 0.0;
        LossFunction lossFunction = config.getLossFunction();
        
        for (int i = 0; i < inputs.length; i++) {
            double[] predicted = predict(inputs[i]);
            totalLoss += lossFunction.compute(predicted, targets[i]);
        }
        
        return totalLoss / inputs.length;
    }
    
    /**
     * Gets the number of layers in the network.
     * 
     * @return number of layers
     */
    public int getLayerCount() {
        return layers.size();
    }
    
    /**
     * Gets a specific layer by index.
     * 
     * @param index layer index (0-based)
     * @return the layer at the specified index
     */
    public Layer getLayer(int index) {
        return layers.get(index);
    }
    
    /**
     * Gets all layers in the network.
     * 
     * @return list of layers
     */
    public List<Layer> getLayers() {
        return new ArrayList<>(layers);
    }
    
    /**
     * Gets the network configuration.
     * 
     * @return network configuration
     */
    public NetworkConfig getConfig() {
        return config;
    }
    
    /**
     * Sets the network configuration.
     * 
     * @param config new configuration
     */
    public void setConfig(NetworkConfig config) {
        this.config = config;
    }
    
    /**
     * Gets the training loss history.
     * 
     * @return list of training losses per epoch
     */
    public List<Double> getTrainingLossHistory() {
        return new ArrayList<>(trainingLossHistory);
    }
    
    /**
     * Gets the validation loss history.
     * 
     * @return list of validation losses per epoch
     */
    public List<Double> getValidationLossHistory() {
        return new ArrayList<>(validationLossHistory);
    }
    
    /**
     * Adds a training loss value to history.
     * 
     * @param loss training loss
     */
    public void addTrainingLoss(double loss) {
        trainingLossHistory.add(loss);
    }
    
    /**
     * Adds a validation loss value to history.
     * 
     * @param loss validation loss
     */
    public void addValidationLoss(double loss) {
        validationLossHistory.add(loss);
    }
    
    /**
     * Clears the training history.
     */
    public void clearHistory() {
        trainingLossHistory.clear();
        validationLossHistory.clear();
    }
    
    /**
     * Gets the input size of the network (first layer's input size).
     * 
     * @return input size
     */
    public int getInputSize() {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Network has no layers");
        }
        return layers.get(0).getInputSize();
    }
    
    /**
     * Gets the output size of the network (last layer's output size).
     * 
     * @return output size
     */
    public int getOutputSize() {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Network has no layers");
        }
        return layers.get(layers.size() - 1).getOutputSize();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NeuralNetwork[\n");
        sb.append(String.format("  Config: %s\n", config));
        sb.append(String.format("  Layers: %d\n", layers.size()));
        for (int i = 0; i < layers.size(); i++) {
            sb.append(String.format("    Layer %d: %s\n", i, layers.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}

