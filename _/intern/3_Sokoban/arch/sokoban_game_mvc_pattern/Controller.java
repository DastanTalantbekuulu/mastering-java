import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, MouseListener {
    private Model model;
    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }
    public Model getModel() {
        return model;
    }

    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        String direction = "";
        switch (keyCode) {
            case 37:
                direction = "left";
                break;
            case 38:
                direction = "up";
                break;
            case 39:
                direction = "right";
                break;
            case 40:
                direction = "down";
                break;
            default:
                return;
        }
        model.move(direction);
    }

    public void keyTyped(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

    public void mousePressed(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        model.doClick(x, y);
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }
}
