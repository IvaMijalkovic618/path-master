import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game extends JFrame {
    JFrame frame;
    JPanel panel;
    JButton[][] buttons;
    int gridSize;
    int currentRow;
    int currentCol;
    int totalFieldValue;
    int pathLength;
    Timer timer;
    int elapsedTime;
    Color pathColor = Color.PINK;
    GameStats gameStats;

    public Game(int gridSize) {
        this.gridSize = gridSize;

        buttons = new JButton[gridSize][gridSize];
        frame = new JFrame("Gameplay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        gameStats = new GameStats();

        setupGrid();

        MenuBar menu = new MenuBar(this);
        frame.setJMenuBar(menu.getMenuBar());

        frame.add(gameStats.getStatsPanel(), BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        createButtons();
        updateButtonPosition();
        startTimer();
    }

    private void setupGrid() {
        panel = new JPanel(new GridLayout(gridSize, gridSize));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                panel.add(buttons[i][j]);

                int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (buttons[row][col].getText().equals("Start")) {
            currentRow = row;
            currentCol = col;
            buttons[row][col].setEnabled(false);
            buttons[row][col].setBackground(pathColor);
        } else {
            int rowDiff = Math.abs(row - currentRow);
            int colDiff = Math.abs(col - currentCol);

            if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
                currentRow = row;
                currentCol = col;

                buttons[row][col].setEnabled(false);
                buttons[row][col].setBackground(pathColor);

                if (!buttons[row][col].getText().equals("End") && !buttons[row][col].getText().equals("Start")) {
                    totalFieldValue += Integer.parseInt(buttons[row][col].getText());
                }

                pathLength++;
                updateStats();

                if (buttons[row][col].getText().equals("End")) {
                    JOptionPane.showMessageDialog(frame, "You reached the End!");
                    disableAllButtons();
                } else {
                    updateButtonPosition();
                }
            }
        }
    }

    private void createButtons() {
        Random random = new Random();
        int startRow = random.nextInt(gridSize);
        int startCol = random.nextInt(gridSize);
        int endRow, endCol;

        do {
            endRow = random.nextInt(gridSize);
            endCol = random.nextInt(gridSize);
        } while (endRow == startRow && endCol == startCol);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (i == startRow && j == startCol) {
                    buttons[i][j].setText("Start");
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setBackground(pathColor);
                    currentRow = i;
                    currentCol = j;
                } else if (i == endRow && j == endCol) {
                    buttons[i][j].setText("End");
                } else {
                    buttons[i][j].setText(String.valueOf(random.nextInt(10)));
                }
            }
        }
    }

    private void updateButtonPosition() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (Math.abs(i - currentRow) + Math.abs(j - currentCol) == 1) {
                    buttons[i][j].setEnabled(true);
                } else {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    private void updateStats() {
        double score = pathLength == 0 ? 0 : (double) totalFieldValue / pathLength;
        gameStats.updateScore(score);
        gameStats.updatePathLength(pathLength);
        gameStats.updateSum(totalFieldValue);
    }

    private void disableAllButtons() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void startTimer() {
        elapsedTime = 0;
        timer = new Timer(1000, e -> {
            elapsedTime++;
            gameStats.updateTimer(elapsedTime);
        });
        timer.start();
    }

    public void setPathColor(Color newColor) {
        this.pathColor = newColor;
    }

}

