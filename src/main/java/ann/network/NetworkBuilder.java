package src.main.java.ann.network;

import src.main.java.ann.activation.ActivationFunction;
import src.main.java.ann.activation.SigmoidActivation;
import src.main.java.ann.initialization.WeightInitializer;
import src.main.java.ann.initialization.XavierInitializer;
import src.main.java.ann.layer.DenseLayer;
import src.main.java.ann.loss.LossFunction;
import src.main.java.ann.loss.MSELoss;

/**
 * Builder class for constructing neural networks with a fluent API.
 * Provides convenient methods for adding layers and configuring the network.
 * 
 * Example usage:
 * <pre>
 * NeuralNetwork network = new NetworkBuilder()
 *     .addInputLayer(784)
 *     .addDenseLayer(128, new ReLUActivation())
 *     .addDenseLayer(64, new ReLUActivation())
 *     .addOutputLayer(10, new SigmoidActivation())
 *     .setLearningRate(0.01)
 *     .setEpochs(100)
 *     .build();
 * </pre>
 */
public class NetworkBuilder {
    
    private NeuralNetwork network;
    private NetworkConfig config;
    private WeightInitializer defaultInitializer;
    
    private int lastLayerSize;
    
    /**
     * Creates a new network builder with default configuration.
     */
    public NetworkBuilder() {
        this.config = new NetworkConfig();
        this.network = new NeuralNetwork(config);
        this.defaultInitializer = new XavierInitializer();
        this.lastLayerSize = -1;
    }
    
    /**
     * Sets the input layer size.
     * Must be called before adding any dense layers.
     * 
     * @param size input size
     * @return this builder
     */
    public NetworkBuilder addInputLayer(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Input size must be positive");
        }
        
        this.lastLayerSize = size;
        return this;
    }
    
    /**
     * Adds a dense (fully connected) layer with default weight initialization.
     * 
     * @param size number of neurons in the layer
     * @param activation activation function for the layer
     * @return this builder
     */
    public NetworkBuilder addDenseLayer(int size, ActivationFunction activation) {
        return addDenseLayer(size, activation, defaultInitializer);
    }
    
    /**
     * Adds a dense layer with custom weight initialization.
     * 
     * @param size number of neurons in the layer
     * @param activation activation function
     * @param initializer weight initializer
     * @return this builder
     */
    public NetworkBuilder addDenseLayer(int size, ActivationFunction activation, 
                                       WeightInitializer initializer) {
        if (lastLayerSize == -1) {
            throw new IllegalStateException("Must call addInputLayer() before adding dense layers");
        }
        
        if (size <= 0) {
            throw new IllegalArgumentException("Layer size must be positive");
        }
        
        DenseLayer layer = new DenseLayer(lastLayerSize, size, activation, initializer);
        network.addLayer(layer);
        
        lastLayerSize = size;
        return this;
    }
    
    /**
     * Adds an output layer with default weight initialization.
     * Typically the final layer of the network.
     * 
     * @param size number of output neurons
     * @param activation activation function (e.g., Sigmoid for classification)
     * @return this builder
     */
    public NetworkBuilder addOutputLayer(int size, ActivationFunction activation) {
        return addDenseLayer(size, activation);
    }
    
    /**
     * Adds an output layer with custom weight initialization.
     * 
     * @param size number of output neurons
     * @param activation activation function
     * @param initializer weight initializer
     * @return this builder
     */
    public NetworkBuilder addOutputLayer(int size, ActivationFunction activation,
                                        WeightInitializer initializer) {
        return addDenseLayer(size, activation, initializer);
    }
    
    /**
     * Sets the learning rate for training.
     * 
     * @param learningRate learning rate (must be positive)
     * @return this builder
     */
    public NetworkBuilder setLearningRate(double learningRate) {
        config.setLearningRate(learningRate);
        return this;
    }
    
    /**
     * Sets the number of training epochs.
     * 
     * @param epochs number of epochs (must be positive)
     * @return this builder
     */
    public NetworkBuilder setEpochs(int epochs) {
        config.setEpochs(epochs);
        return this;
    }
    
    /**
     * Sets the batch size for mini-batch gradient descent.
     * 
     * @param batchSize batch size (must be positive)
     * @return this builder
     */
    public NetworkBuilder setBatchSize(int batchSize) {
        config.setBatchSize(batchSize);
        return this;
    }
    
    /**
     * Sets the loss function.
     * 
     * @param lossFunction loss function to use
     * @return this builder
     */
    public NetworkBuilder setLossFunction(LossFunction lossFunction) {
        config.setLossFunction(lossFunction);
        return this;
    }
    
    /**
     * Sets the default weight initializer for all layers.
     * Must be called before adding layers.
     * 
     * @param initializer weight initializer
     * @return this builder
     */
    public NetworkBuilder setWeightInitializer(WeightInitializer initializer) {
        this.defaultInitializer = initializer;
        return this;
    }
    
    /**
     * Enables or disables early stopping.
     * 
     * @param enable true to enable early stopping
     * @return this builder
     */
    public NetworkBuilder setEarlyStopping(boolean enable) {
        config.setUseEarlyStopping(enable);
        return this;
    }
    
    /**
     * Sets early stopping parameters.
     * 
     * @param patience number of epochs to wait before stopping
     * @param minDelta minimum change in loss to be considered improvement
     * @return this builder
     */
    public NetworkBuilder setEarlyStoppingParams(int patience, double minDelta) {
        config.setUseEarlyStopping(true);
        config.setEarlyStoppingPatience(patience);
        config.setEarlyStoppingMinDelta(minDelta);
        return this;
    }
    
    /**
     * Sets the validation split ratio.
     * 
     * @param validationSplit fraction of data to use for validation (0 to 1)
     * @return this builder
     */
    public NetworkBuilder setValidationSplit(double validationSplit) {
        config.setValidationSplit(validationSplit);
        return this;
    }
    
    /**
     * Enables or disables verbose output during training.
     * 
     * @param verbose true to enable verbose output
     * @return this builder
     */
    public NetworkBuilder setVerbose(boolean verbose) {
        config.setVerbose(verbose);
        return this;
    }
    
    /**
     * Builds and returns the configured neural network.
     * 
     * @return the constructed neural network
     */
    public NeuralNetwork build() {
        if (network.getLayerCount() == 0) {
            throw new IllegalStateException("Network must have at least one layer");
        }
        
        return network;
    }
    
    /**
     * Creates a simple feedforward network with default settings.
     * 
     * @param inputSize input layer size
     * @param hiddenSize hidden layer size
     * @param outputSize output layer size
     * @return configured neural network
     */
    public static NeuralNetwork createSimpleNetwork(int inputSize, int hiddenSize, int outputSize) {
        return new NetworkBuilder()
            .addInputLayer(inputSize)
            .addDenseLayer(hiddenSize, new SigmoidActivation())
            .addOutputLayer(outputSize, new SigmoidActivation())
            .setLearningRate(0.01)
            .setEpochs(100)
            .setBatchSize(32)
            .setLossFunction(new MSELoss())
            .build();
    }
    
    /**
     * Creates a deep network with multiple hidden layers.
     * 
     * @param inputSize input layer size
     * @param hiddenSizes array of hidden layer sizes
     * @param outputSize output layer size
     * @param activation activation function for all layers
     * @return configured neural network
     */
    public static NeuralNetwork createDeepNetwork(int inputSize, int[] hiddenSizes, 
                                                  int outputSize, ActivationFunction activation) {
        NetworkBuilder builder = new NetworkBuilder()
            .addInputLayer(inputSize);
        
        for (int hiddenSize : hiddenSizes) {
            builder.addDenseLayer(hiddenSize, activation);
        }
        
        builder.addOutputLayer(outputSize, activation);
        
        return builder.build();
    }
}

