import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame implements ActionListener {

    Game parent;

    public SaveGame(Game parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Save Game Files", "csv"));

        int option = fileChooser.showSaveDialog(parent.frame);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Grid Size: " + parent.gridSize + "x" + parent.gridSize + "\n");

                writer.write("Start Position: (" + parent.currentRow + "," + parent.currentCol + ")\n");
                int endRow = -1, endCol = -1;
                for (int i = 0; i < parent.gridSize; i++) {
                    for (int j = 0; j < parent.gridSize; j++) {
                        if (parent.buttons[i][j].getText().equals("End")) {
                            endRow = i;
                            endCol = j;
                            break;
                        }
                    }
                }
                writer.write("End Position: (" + endRow + "," + endCol + ")\n");

                writer.write("Grid:\n");
                for (int i = 0; i < parent.gridSize; i++) {
                    for (int j = 0; j < parent.gridSize; j++) {
                        writer.write(parent.buttons[i][j].getText() + (j < parent.gridSize - 1 ? "," : ""));
                    }
                    writer.write("\n");
                }

                writer.write("Visited:\n");
                for (int i = 0; i < parent.gridSize; i++) {
                    for (int j = 0; j < parent.gridSize; j++) {
                        boolean visited = !parent.buttons[i][j].isEnabled();
                        writer.write((visited ? "1" : "0") + (j < parent.gridSize - 1 ? "," : ""));
                    }
                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(parent.frame, "Game saved successfully as CSV!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent.frame, "Error saving game: " + ex.getMessage());
            }
        }
    }
}
