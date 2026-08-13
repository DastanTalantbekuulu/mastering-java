package sokoban;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Canvas extends JPanel {
    private Model model;

    public Canvas(Model model) {
        this.model = model;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        model.draw(graphics);
    }
}
