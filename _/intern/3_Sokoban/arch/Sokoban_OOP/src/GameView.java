import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
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
        frame.setVisible(true);
        frame.addKeyListener(controller);

    }

    public void repaint() {
        panel.repaint();
    }

    private void drawGrid(Graphics g) {

    }
}

