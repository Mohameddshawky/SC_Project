package src.main.java.ann.core;

import src.main.java.ann.training.TrainingHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Training monitor for real-time feedback during training.
 * Tracks loss, provides callbacks, and detects training issues.
 */
public class TrainingMonitor {
    
    private List<Double> recentLosses;
    private int windowSize;
    
    private boolean detectVanishingGradient;
    private boolean detectExplodingGradient;
    
    private double vanishingThreshold;
    private double explodingThreshold;
    
    /**
     * Creates a training monitor with default settings.
     */
    public TrainingMonitor() {
        this(10);
    }
    
    /**
     * Creates a training monitor with specified window size.
     * 
     * @param windowSize number of recent losses to track
     */
    public TrainingMonitor(int windowSize) {
        this.windowSize = windowSize;
        this.recentLosses = new ArrayList<>();
        this.detectVanishingGradient = true;
        this.detectExplodingGradient = true;
        this.vanishingThreshold = 1e-7;
        this.explodingThreshold = 1e7;
    }
    
    /**
     * Records a loss value.
     * 
     * @param loss loss value
     */
    public void recordLoss(double loss) {
        recentLosses.add(loss);
        
        // Keep only recent losses
        if (recentLosses.size() > windowSize) {
            recentLosses.remove(0);
        }
    }
    
    /**
     * Checks if training is converging.
     * 
     * @return true if loss is decreasing
     */
    public boolean isConverging() {
        if (recentLosses.size() < 2) {
            return false;
        }
        
        double firstLoss = recentLosses.get(0);
        double lastLoss = recentLosses.get(recentLosses.size() - 1);
        
        return lastLoss < firstLoss;
    }
    
    /**
     * Checks if training has stalled (no improvement).
     * 
     * @param threshold minimum change to consider improvement
     * @return true if training has stalled
     */
    public boolean hasStalled(double threshold) {
        if (recentLosses.size() < windowSize) {
            return false;
        }
        
        double firstLoss = recentLosses.get(0);
        double lastLoss = recentLosses.get(recentLosses.size() - 1);
        
        return Math.abs(firstLoss - lastLoss) < threshold;
    }
    
    /**
     * Checks for potential vanishing gradient.
     * 
     * @return true if gradient might be vanishing
     */
    public boolean hasVanishingGradient() {
        if (!detectVanishingGradient || recentLosses.isEmpty()) {
            return false;
        }
        
        double lastLoss = recentLosses.get(recentLosses.size() - 1);
        return lastLoss < vanishingThreshold;
    }
    
    /**
     * Checks for potential exploding gradient.
     * 
     * @return true if gradient might be exploding
     */
    public boolean hasExplodingGradient() {
        if (!detectExplodingGradient || recentLosses.isEmpty()) {
            return false;
        }
        
        double lastLoss = recentLosses.get(recentLosses.size() - 1);
        return lastLoss > explodingThreshold || Double.isNaN(lastLoss) || Double.isInfinite(lastLoss);
    }
    
    /**
     * Gets the average loss over the recent window.
     * 
     * @return average loss
     */
    public double getAverageLoss() {
        if (recentLosses.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (double loss : recentLosses) {
            sum += loss;
        }
        
        return sum / recentLosses.size();
    }
    
    /**
     * Gets the loss trend (positive = increasing, negative = decreasing).
     * 
     * @return loss trend
     */
    public double getLossTrend() {
        if (recentLosses.size() < 2) {
            return 0.0;
        }
        
        double firstLoss = recentLosses.get(0);
        double lastLoss = recentLosses.get(recentLosses.size() - 1);
        
        return lastLoss - firstLoss;
    }
    
    /**
     * Prints a progress bar for training.
     * 
     * @param currentEpoch current epoch
     * @param totalEpochs total epochs
     * @param loss current loss
     */
    public void printProgress(int currentEpoch, int totalEpochs, double loss) {
        int barLength = 50;
        int progress = (int) ((double) currentEpoch / totalEpochs * barLength);
        
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < progress) {
                bar.append("=");
            } else if (i == progress) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");
        
        System.out.printf("\rEpoch %d/%d %s Loss: %.6f", 
                          currentEpoch, totalEpochs, bar.toString(), loss);
        
        if (currentEpoch == totalEpochs) {
            System.out.println();
        }
    }
    
    /**
     * Generates a training summary.
     * 
     * @param history training history
     * @return summary string
     */
    public String generateSummary(TrainingHistory history) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Training Summary ===\n");
        sb.append(String.format("Total Epochs: %d\n", history.getTotalEpochs()));
        sb.append(String.format("Final Training Loss: %.6f\n", history.getFinalTrainingLoss()));
        
        if (!history.getValidationLosses().isEmpty()) {
            sb.append(String.format("Final Validation Loss: %.6f\n", history.getFinalValidationLoss()));
            sb.append(String.format("Best Validation Loss: %.6f\n", history.getBestValidationLoss()));
        }
        
        if (history.isStoppedEarly()) {
            sb.append(String.format("Stopped Early: Yes (at epoch %d)\n", history.getStoppedAtEpoch()));
        } else {
            sb.append("Stopped Early: No\n");
        }
        
        // Add warnings
        if (hasVanishingGradient()) {
            sb.append("\n⚠ Warning: Potential vanishing gradient detected\n");
        }
        
        if (hasExplodingGradient()) {
            sb.append("\n⚠ Warning: Potential exploding gradient detected\n");
        }
        
        if (hasStalled(0.001)) {
            sb.append("\n⚠ Warning: Training appears to have stalled\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Clears the recorded losses.
     */
    public void reset() {
        recentLosses.clear();
    }
    
    // Getters and setters
    
    public void setVanishingThreshold(double threshold) {
        this.vanishingThreshold = threshold;
    }
    
    public void setExplodingThreshold(double threshold) {
        this.explodingThreshold = threshold;
    }
    
    public void setDetectVanishingGradient(boolean detect) {
        this.detectVanishingGradient = detect;
    }
    
    public void setDetectExplodingGradient(boolean detect) {
        this.detectExplodingGradient = detect;
    }
}

