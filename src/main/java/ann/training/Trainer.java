package src.main.java.ann.training;

import src.main.java.ann.network.NeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Trainer class for neural networks.
 * Handles the complete training loop with batch processing, shuffling, and early stopping.
 */
public class Trainer {
    
    private NeuralNetwork network;
    private TrainingConfig config;
    private Random random;
    
    /**
     * Creates a trainer for a neural network.
     * 
     * @param network the neural network to train
     * @param config training configuration
     */
    public Trainer(NeuralNetwork network, TrainingConfig config) {
        this.network = network;
        this.config = config;
        this.random = new Random();
    }
    
    /**
     * Creates a trainer with a random seed for reproducibility.
     * 
     * @param network the neural network to train
     * @param config training configuration
     * @param seed random seed
     */
    public Trainer(NeuralNetwork network, TrainingConfig config, long seed) {
        this.network = network;
        this.config = config;
        this.random = new Random(seed);
    }
    
    /**
     * Trains the network on the provided data.
     * 
     * @param trainInputs training input data
     * @param trainTargets training target data
     * @return training history
     */
    public TrainingHistory train(double[][] trainInputs, double[][] trainTargets) {
        return train(trainInputs, trainTargets, null, null);
    }
    
    /**
     * Trains the network with validation data for early stopping.
     * 
     * @param trainInputs training input data
     * @param trainTargets training target data
     * @param valInputs validation input data (can be null)
     * @param valTargets validation target data (can be null)
     * @return training history
     */
    public TrainingHistory train(double[][] trainInputs, double[][] trainTargets,
                                 double[][] valInputs, double[][] valTargets) {
        
        TrainingHistory history = new TrainingHistory();
        
        // Validate inputs
        if (trainInputs.length != trainTargets.length) {
            throw new IllegalArgumentException("Training inputs and targets must have same length");
        }
        
        if (valInputs != null && valTargets != null && valInputs.length != valTargets.length) {
            throw new IllegalArgumentException("Validation inputs and targets must have same length");
        }
        
        int dataSize = trainInputs.length;
        int batchSize = Math.min(config.getBatchSize(), dataSize);
        
        // Early stopping variables
        double bestValLoss = Double.MAX_VALUE;
        int patienceCounter = 0;
        
        // Training loop
        for (int epoch = 0; epoch < config.getEpochs(); epoch++) {
            
            // Create index list for shuffling
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < dataSize; i++) {
                indices.add(i);
            }
            
            // Shuffle data if enabled
            if (config.isShuffle()) {
                Collections.shuffle(indices, random);
            }
            
            // Train on batches
            double epochLoss = 0.0;
            int numBatches = (int) Math.ceil((double) dataSize / batchSize);
            
            for (int batch = 0; batch < numBatches; batch++) {
                int batchStart = batch * batchSize;
                int batchEnd = Math.min(batchStart + batchSize, dataSize);
                int currentBatchSize = batchEnd - batchStart;
                
                // Prepare batch data
                double[][] batchInputs = new double[currentBatchSize][];
                double[][] batchTargets = new double[currentBatchSize][];
                
                for (int i = 0; i < currentBatchSize; i++) {
                    int idx = indices.get(batchStart + i);
                    batchInputs[i] = trainInputs[idx];
                    batchTargets[i] = trainTargets[idx];
                }
                
                // Train on batch
                double batchLoss = network.trainOnBatch(batchInputs, batchTargets);
                epochLoss += batchLoss * currentBatchSize;
            }
            
            epochLoss /= dataSize;
            
            // Validation
            double valLoss = -1.0;
            if (valInputs != null && valTargets != null) {
                valLoss = network.evaluate(valInputs, valTargets);
            }
            
            // Record history
            history.recordLoss(epochLoss, valLoss);
            network.addTrainingLoss(epochLoss);
            if (valLoss >= 0) {
                network.addValidationLoss(valLoss);
            }
            
            // Print progress
            if (config.isVerbose() && (epoch + 1) % config.getPrintEveryNEpochs() == 0) {
                if (valLoss >= 0) {
                    System.out.printf("Epoch %d/%d - Loss: %.6f - Val Loss: %.6f\n",
                                      epoch + 1, config.getEpochs(), epochLoss, valLoss);
                } else {
                    System.out.printf("Epoch %d/%d - Loss: %.6f\n",
                                      epoch + 1, config.getEpochs(), epochLoss);
                }
            }
            
            // Early stopping check
            if (config.isUseEarlyStopping() && valLoss >= 0) {
                if (valLoss < bestValLoss - config.getMinDelta()) {
                    bestValLoss = valLoss;
                    patienceCounter = 0;
                } else {
                    patienceCounter++;
                    
                    if (patienceCounter >= config.getPatience()) {
                        if (config.isVerbose()) {
                            System.out.printf("Early stopping at epoch %d\n", epoch + 1);
                        }
                        history.markEarlyStopping(epoch + 1);
                        break;
                    }
                }
            }
        }
        
        return history;
    }
    
    /**
     * Gets the network being trained.
     * 
     * @return neural network
     */
    public NeuralNetwork getNetwork() {
        return network;
    }
    
    /**
     * Gets the training configuration.
     * 
     * @return training configuration
     */
    public TrainingConfig getConfig() {
        return config;
    }
    
    /**
     * Sets a new training configuration.
     * 
     * @param config new training configuration
     */
    public void setConfig(TrainingConfig config) {
        this.config = config;
    }
}

