package src.main.java.ann.training;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores training history including loss and metrics per epoch.
 */
public class TrainingHistory {
    
    private List<Double> trainingLoss;
    private List<Double> validationLoss;
    private List<Double> trainingAccuracy;
    private List<Double> validationAccuracy;
    
    private int totalEpochs;
    private boolean stoppedEarly;
    private int stoppedAtEpoch;
    
    /**
     * Creates an empty training history.
     */
    public TrainingHistory() {
        this.trainingLoss = new ArrayList<>();
        this.validationLoss = new ArrayList<>();
        this.trainingAccuracy = new ArrayList<>();
        this.validationAccuracy = new ArrayList<>();
        this.totalEpochs = 0;
        this.stoppedEarly = false;
        this.stoppedAtEpoch = -1;
    }
    
    /**
     * Records loss for an epoch.
     * 
     * @param trainLoss training loss
     * @param valLoss validation loss (use -1 if no validation)
     */
    public void recordLoss(double trainLoss, double valLoss) {
        trainingLoss.add(trainLoss);
        if (valLoss >= 0) {
            validationLoss.add(valLoss);
        }
        totalEpochs++;
    }
    
    /**
     * Records accuracy for an epoch.
     * 
     * @param trainAcc training accuracy
     * @param valAcc validation accuracy (use -1 if no validation)
     */
    public void recordAccuracy(double trainAcc, double valAcc) {
        trainingAccuracy.add(trainAcc);
        if (valAcc >= 0) {
            validationAccuracy.add(valAcc);
        }
    }
    
    /**
     * Marks that training stopped early.
     * 
     * @param epochNumber epoch at which training stopped
     */
    public void markEarlyStopping(int epochNumber) {
        this.stoppedEarly = true;
        this.stoppedAtEpoch = epochNumber;
    }
    
    /**
     * Gets the training loss for a specific epoch.
     * 
     * @param epoch epoch number (0-based)
     * @return training loss
     */
    public double getTrainingLoss(int epoch) {
        return trainingLoss.get(epoch);
    }
    
    /**
     * Gets the validation loss for a specific epoch.
     * 
     * @param epoch epoch number (0-based)
     * @return validation loss
     */
    public double getValidationLoss(int epoch) {
        return validationLoss.get(epoch);
    }
    
    /**
     * Gets the final training loss.
     * 
     * @return final training loss
     */
    public double getFinalTrainingLoss() {
        return trainingLoss.isEmpty() ? 0.0 : trainingLoss.get(trainingLoss.size() - 1);
    }
    
    /**
     * Gets the final validation loss.
     * 
     * @return final validation loss
     */
    public double getFinalValidationLoss() {
        return validationLoss.isEmpty() ? 0.0 : validationLoss.get(validationLoss.size() - 1);
    }
    
    /**
     * Gets the best (minimum) training loss.
     * 
     * @return best training loss
     */
    public double getBestTrainingLoss() {
        return trainingLoss.stream().min(Double::compare).orElse(0.0);
    }
    
    /**
     * Gets the best (minimum) validation loss.
     * 
     * @return best validation loss
     */
    public double getBestValidationLoss() {
        return validationLoss.stream().min(Double::compare).orElse(0.0);
    }
    
    /**
     * Gets all training losses.
     * 
     * @return list of training losses
     */
    public List<Double> getTrainingLosses() {
        return new ArrayList<>(trainingLoss);
    }
    
    /**
     * Gets all validation losses.
     * 
     * @return list of validation losses
     */
    public List<Double> getValidationLosses() {
        return new ArrayList<>(validationLoss);
    }
    
    /**
     * Gets all training accuracies.
     * 
     * @return list of training accuracies
     */
    public List<Double> getTrainingAccuracies() {
        return new ArrayList<>(trainingAccuracy);
    }
    
    /**
     * Gets all validation accuracies.
     * 
     * @return list of validation accuracies
     */
    public List<Double> getValidationAccuracies() {
        return new ArrayList<>(validationAccuracy);
    }
    
    /**
     * Gets the total number of epochs completed.
     * 
     * @return total epochs
     */
    public int getTotalEpochs() {
        return totalEpochs;
    }
    
    /**
     * Checks if training stopped early.
     * 
     * @return true if stopped early
     */
    public boolean isStoppedEarly() {
        return stoppedEarly;
    }
    
    /**
     * Gets the epoch at which training stopped early.
     * 
     * @return epoch number, or -1 if didn't stop early
     */
    public int getStoppedAtEpoch() {
        return stoppedAtEpoch;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TrainingHistory[\n");
        sb.append(String.format("  Total Epochs: %d\n", totalEpochs));
        sb.append(String.format("  Final Training Loss: %.6f\n", getFinalTrainingLoss()));
        if (!validationLoss.isEmpty()) {
            sb.append(String.format("  Final Validation Loss: %.6f\n", getFinalValidationLoss()));
        }
        if (stoppedEarly) {
            sb.append(String.format("  Stopped Early at Epoch: %d\n", stoppedAtEpoch));
        }
        sb.append("]");
        return sb.toString();
    }
}

