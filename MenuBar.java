import javax.swing.*;
import java.awt.*;

public class MenuBar {
    private JMenuBar menuBar;

    public MenuBar(Game game) {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new SaveGame(game));
        fileMenu.add(saveGame);
        
        JMenu restartMenu = new JMenu("Restart");
        JMenuItem restartRandom = new JMenuItem("Restart Random");
        restartRandom.addActionListener(e -> {
            game.frame.dispose();
            new Game(game.gridSize);
        });

        JMenuItem changeGridSize = new JMenuItem("Change Grid Size");
        changeGridSize.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter new grid size");
            if (input != null) {
                try {
                    int newSize = Integer.parseInt(input);
                    game.frame.dispose();
                    new Game(newSize);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(game.frame, "Invalid grid size!");
                }
            }
        });

        restartMenu.add(restartRandom);
        restartMenu.add(changeGridSize);

        JMenu appearanceMenu = new JMenu("Customize Appearance");
        JMenuItem changeGameColor = new JMenuItem("Change Game Color");

        changeGameColor.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(game.frame, "Choose a Color", Color.WHITE);
            if (newColor != null) {
                for (int i = 0; i < game.gridSize; i++) {
                    for (int j = 0; j < game.gridSize; j++) {
                        if (!game.buttons[i][j].getText().equals("Start") && !game.buttons[i][j].getText().equals("End")) {
                            game.buttons[i][j].setBackground(newColor);
                        }

                    }
                }
            }
        });

        JMenuItem changePathColor = new JMenuItem("Change Path Color");
        changePathColor.addActionListener(e -> {
            Color newPathColor = JColorChooser.showDialog(game.frame, "Choose Path Color", game.pathColor);
            if (newPathColor != null) {
                game.setPathColor(newPathColor);
            }
        });

        appearanceMenu.add(changeGameColor);
        appearanceMenu.add(changePathColor);

        menuBar.add(fileMenu);
        menuBar.add(restartMenu);
        menuBar.add(appearanceMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}

