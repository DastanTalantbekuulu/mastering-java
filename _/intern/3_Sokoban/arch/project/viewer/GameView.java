package viewer;

import controller.GameController;
import model.GameModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

public class GameView {
    private GameModel model;
    private JPanel panel;
    private Map<String, Image> images;

    public GameView() {
        model = new GameModel();
        panel = new JPanel();

        JFrame frame = new JFrame("Sokoban");
        GameController controller = new GameController(model, this);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(controller);
        frame.setVisible(true);
    }

    public void repaint() {
        panel.repaint();
    }

    private void drawGrid(Graphics g) {

    }
}

