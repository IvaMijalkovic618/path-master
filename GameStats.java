import javax.swing.*;
import java.awt.*;

public class GameStats {
    private JPanel statsPanel;
    private JLabel scoreLabel;
    private JLabel pathLengthLabel;
    private JLabel sumLabel;
    private JLabel timerLabel;

    public GameStats() {
        statsPanel = new JPanel(new GridLayout(1, 4));

        scoreLabel = new JLabel("Score: 0");
        pathLengthLabel = new JLabel("Path: 0");
        sumLabel = new JLabel("Sum: 0");
        timerLabel = new JLabel("Timer: 0");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        scoreLabel.setFont(labelFont);
        pathLengthLabel.setFont(labelFont);
        sumLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        statsPanel.add(scoreLabel);
        statsPanel.add(pathLengthLabel);
        statsPanel.add(sumLabel);
        statsPanel.add(timerLabel);
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void updateScore(double score) {
        scoreLabel.setText(String.format("Score: %.2f", score));
    }

    public void updatePathLength(int pathLength) {
        pathLengthLabel.setText("Path: " + pathLength);
    }

    public void updateSum(int sum) {
        sumLabel.setText("Sum: " + sum);
    }

    public void updateTimer(int elapsedTime) {
        timerLabel.setText("Timer: " + elapsedTime);
    }
}

