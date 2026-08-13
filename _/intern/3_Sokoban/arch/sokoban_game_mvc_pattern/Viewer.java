import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

public class Viewer {
    private Canvas canvas;

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

//        JWindow window = new JWindow();
//        window.setSize(800, 800);
//        window.setLocation(300, 100);
//        window.add("Center", canvas);
//        window.setVisible(true);
//        window.addKeyListener(controller);
    }

    public void move() {

    }

    public void update() {
        canvas.repaint();
    }

    public void showWonDialog() {
        JOptionPane.showMessageDialog(null, "You won!");
    }
}
