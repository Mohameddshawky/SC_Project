package src.main.java.ann.evaluation;

/**
 * Confusion matrix for binary and multi-class classification.
 * Provides metrics like precision, recall, and F1-score.
 */
public class ConfusionMatrix {
    
    private int[][] matrix;
    private int numClasses;
    
    /**
     * Creates a confusion matrix for the given number of classes.
     * 
     * @param numClasses number of classes
     */
    public ConfusionMatrix(int numClasses) {
        this.numClasses = numClasses;
        this.matrix = new int[numClasses][numClasses];
    }
    
    /**
     * Updates the confusion matrix with a prediction.
     * 
     * @param predictedClass predicted class index
     * @param actualClass actual class index
     */
    public void add(int predictedClass, int actualClass) {
        if (predictedClass < 0 || predictedClass >= numClasses) {
            throw new IllegalArgumentException("Invalid predicted class: " + predictedClass);
        }
        if (actualClass < 0 || actualClass >= numClasses) {
            throw new IllegalArgumentException("Invalid actual class: " + actualClass);
        }
        
        matrix[actualClass][predictedClass]++;
    }
    
    /**
     * Builds a confusion matrix from predictions and targets.
     * 
     * @param predictions predicted class indices
     * @param targets actual class indices
     * @param numClasses number of classes
     * @return confusion matrix
     */
    public static ConfusionMatrix build(int[] predictions, int[] targets, int numClasses) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets must have same length");
        }
        
        ConfusionMatrix cm = new ConfusionMatrix(numClasses);
        
        for (int i = 0; i < predictions.length; i++) {
            cm.add(predictions[i], targets[i]);
        }
        
        return cm;
    }
    
    /**
     * Gets the confusion matrix.
     * 
     * @return confusion matrix [actual][predicted]
     */
    public int[][] getMatrix() {
        return matrix;
    }
    
    /**
     * Computes overall accuracy.
     * 
     * @return accuracy as a value in [0, 1]
     */
    public double getAccuracy() {
        int correct = 0;
        int total = 0;
        
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numClasses; j++) {
                if (i == j) {
                    correct += matrix[i][j];
                }
                total += matrix[i][j];
            }
        }
        
        return total == 0 ? 0.0 : (double) correct / total;
    }
    
    /**
     * Computes precision for a specific class.
     * Precision = TP / (TP + FP)
     * 
     * @param classIndex class index
     * @return precision value
     */
    public double getPrecision(int classIndex) {
        int tp = matrix[classIndex][classIndex];
        int predicted = 0;
        
        for (int i = 0; i < numClasses; i++) {
            predicted += matrix[i][classIndex];
        }
        
        return predicted == 0 ? 0.0 : (double) tp / predicted;
    }
    
    /**
     * Computes recall for a specific class.
     * Recall = TP / (TP + FN)
     * 
     * @param classIndex class index
     * @return recall value
     */
    public double getRecall(int classIndex) {
        int tp = matrix[classIndex][classIndex];
        int actual = 0;
        
        for (int j = 0; j < numClasses; j++) {
            actual += matrix[classIndex][j];
        }
        
        return actual == 0 ? 0.0 : (double) tp / actual;
    }
    
    /**
     * Computes F1-score for a specific class.
     * F1 = 2 × (precision × recall) / (precision + recall)
     * 
     * @param classIndex class index
     * @return F1-score value
     */
    public double getF1Score(int classIndex) {
        double precision = getPrecision(classIndex);
        double recall = getRecall(classIndex);
        
        if (precision + recall == 0) {
            return 0.0;
        }
        
        return 2.0 * (precision * recall) / (precision + recall);
    }
    
    /**
     * Computes macro-averaged precision.
     * 
     * @return macro precision
     */
    public double getMacroPrecision() {
        double sum = 0.0;
        for (int i = 0; i < numClasses; i++) {
            sum += getPrecision(i);
        }
        return sum / numClasses;
    }
    
    /**
     * Computes macro-averaged recall.
     * 
     * @return macro recall
     */
    public double getMacroRecall() {
        double sum = 0.0;
        for (int i = 0; i < numClasses; i++) {
            sum += getRecall(i);
        }
        return sum / numClasses;
    }
    
    /**
     * Computes macro-averaged F1-score.
     * 
     * @return macro F1-score
     */
    public double getMacroF1Score() {
        double sum = 0.0;
        for (int i = 0; i < numClasses; i++) {
            sum += getF1Score(i);
        }
        return sum / numClasses;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Confusion Matrix:\n");
        
        // Header
        sb.append("       ");
        for (int i = 0; i < numClasses; i++) {
            sb.append(String.format("P%-6d ", i));
        }
        sb.append("\n");
        
        // Matrix
        for (int i = 0; i < numClasses; i++) {
            sb.append(String.format("A%-5d ", i));
            for (int j = 0; j < numClasses; j++) {
                sb.append(String.format("%-7d ", matrix[i][j]));
            }
            sb.append("\n");
        }
        
        // Metrics
        sb.append(String.format("\nAccuracy: %.4f\n", getAccuracy()));
        sb.append(String.format("Macro Precision: %.4f\n", getMacroPrecision()));
        sb.append(String.format("Macro Recall: %.4f\n", getMacroRecall()));
        sb.append(String.format("Macro F1-Score: %.4f\n", getMacroF1Score()));
        
        return sb.toString();
    }
}

