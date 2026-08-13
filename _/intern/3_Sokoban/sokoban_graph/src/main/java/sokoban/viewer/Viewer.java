package sokoban.viewer;
import sokoban.Canvas;
import sokoban.controller.Controller;
import sokoban.Model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Viewer {
    private final Canvas canvas;

    public Viewer() {
        Controller controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);

        JFrame frame = new JFrame("Sokoban Game MVC Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocation(300, 100);
        frame.add("Center", canvas);
        frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.addMouseListener(controller);
    }

    public void move() {

    }

    public void update() {
        canvas.repaint();
    }

    public void showWonDialog() {
    }
}
