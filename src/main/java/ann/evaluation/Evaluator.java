package src.main.java.ann.evaluation;

import src.main.java.ann.network.NeuralNetwork;

/**
 * Evaluator class for computing performance metrics.
 * Provides various metrics for classification and regression tasks.
 */
public class Evaluator {
    
    /**
     * Computes Mean Squared Error (MSE).
     * Used for regression tasks.
     * 
     * @param predictions predicted values
     * @param targets actual target values
     * @return MSE value
     */
    public static double computeMSE(double[][] predictions, double[][] targets) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets must have same length");
        }
        
        double sum = 0.0;
        int count = 0;
        
        for (int i = 0; i < predictions.length; i++) {
            for (int j = 0; j < predictions[i].length; j++) {
                double error = predictions[i][j] - targets[i][j];
                sum += error * error;
                count++;
            }
        }
        
        return sum / count;
    }
    
    /**
     * Computes Root Mean Squared Error (RMSE).
     * Used for regression tasks.
     * 
     * @param predictions predicted values
     * @param targets actual target values
     * @return RMSE value
     */
    public static double computeRMSE(double[][] predictions, double[][] targets) {
        return Math.sqrt(computeMSE(predictions, targets));
    }
    
    /**
     * Computes Mean Absolute Error (MAE).
     * Used for regression tasks.
     * 
     * @param predictions predicted values
     * @param targets actual target values
     * @return MAE value
     */
    public static double computeMAE(double[][] predictions, double[][] targets) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets must have same length");
        }
        
        double sum = 0.0;
        int count = 0;
        
        for (int i = 0; i < predictions.length; i++) {
            for (int j = 0; j < predictions[i].length; j++) {
                sum += Math.abs(predictions[i][j] - targets[i][j]);
                count++;
            }
        }
        
        return sum / count;
    }
    
    /**
     * Computes classification accuracy.
     * For multi-output, considers a sample correct if all outputs match.
     * 
     * @param predictions predicted values
     * @param targets actual target values
     * @param threshold threshold for binary classification (default 0.5)
     * @return accuracy as a value in [0, 1]
     */
    public static double computeAccuracy(double[][] predictions, double[][] targets, 
                                         double threshold) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets must have same length");
        }
        
        int correct = 0;
        
        for (int i = 0; i < predictions.length; i++) {
            boolean sampleCorrect = true;
            
            for (int j = 0; j < predictions[i].length; j++) {
                // Convert to binary predictions
                int predictedClass = predictions[i][j] >= threshold ? 1 : 0;
                int targetClass = targets[i][j] >= threshold ? 1 : 0;
                
                if (predictedClass != targetClass) {
                    sampleCorrect = false;
                    break;
                }
            }
            
            if (sampleCorrect) {
                correct++;
            }
        }
        
        return (double) correct / predictions.length;
    }
    
    /**
     * Computes classification accuracy with default threshold of 0.5.
     * 
     * @param predictions predicted values
     * @param targets actual target values
     * @return accuracy as a value in [0, 1]
     */
    public static double computeAccuracy(double[][] predictions, double[][] targets) {
        return computeAccuracy(predictions, targets, 0.5);
    }
    
    /**
     * Computes binary classification accuracy for single-output problems.
     * 
     * @param predictions predicted values [numSamples][1]
     * @param targets actual target values [numSamples][1]
     * @param threshold threshold for binary classification
     * @return accuracy as a value in [0, 1]
     */
    public static double computeBinaryAccuracy(double[] predictions, double[] targets, 
                                               double threshold) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets must have same length");
        }
        
        int correct = 0;
        
        for (int i = 0; i < predictions.length; i++) {
            int predictedClass = predictions[i] >= threshold ? 1 : 0;
            int targetClass = targets[i] >= threshold ? 1 : 0;
            
            if (predictedClass == targetClass) {
                correct++;
            }
        }
        
        return (double) correct / predictions.length;
    }
    
    /**
     * Evaluates a network on test data and returns a summary of metrics.
     * 
     * @param network the neural network to evaluate
     * @param testFeatures test input features
     * @param testLabels test target labels
     * @return evaluation summary string
     */
    public static String evaluate(NeuralNetwork network, 
                                  double[][] testFeatures, 
                                  double[][] testLabels) {
        
        // Make predictions
        double[][] predictions = network.predict(testFeatures);
        
        // Compute metrics
        double mse = computeMSE(predictions, testLabels);
        double rmse = computeRMSE(predictions, testLabels);
        double mae = computeMAE(predictions, testLabels);
        double accuracy = computeAccuracy(predictions, testLabels);
        
        // Build summary
        StringBuilder sb = new StringBuilder();
        sb.append("Evaluation Results:\n");
        sb.append(String.format("  MSE:      %.6f\n", mse));
        sb.append(String.format("  RMSE:     %.6f\n", rmse));
        sb.append(String.format("  MAE:      %.6f\n", mae));
        sb.append(String.format("  Accuracy: %.4f (%.2f%%)\n", accuracy, accuracy * 100));
        
        return sb.toString();
    }
}

