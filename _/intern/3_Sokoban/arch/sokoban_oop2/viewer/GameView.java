package viewer;

import controller.GameController;
import model.GameModel;

import javax.swing.JFrame;
import java.awt.Image;
import java.util.Map;

public class GameView {
    private GameModel model;
    private SokobanPanel sokobanPanel;
    private Map<String, Image> images;

    public GameView() {
        model = new GameModel(this);
        sokobanPanel = new SokobanPanel(model.getGrid());

        JFrame frame = new JFrame("Sokoban");
        GameController controller = new GameController(model);

        frame.add(sokobanPanel);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(controller);
        frame.setBounds(100, 100, 800, 600);
        frame.setVisible(true);
    }

    public void repaint() {
        sokobanPanel.repaint();
    }
}

