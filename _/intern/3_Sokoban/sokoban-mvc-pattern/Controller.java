import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Controller implements KeyListener, MouseListener, MouseWheelListener {
    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void keyPressed(KeyEvent event) {
        int code = event.getKeyCode();
        switch (code) {
            // Up
            case 38:
                model.move(1);
                break;
            // Right
            case 39:
                model.move(2);
                break;
            // Down
            case 40:
                model.move(3);
                break;
            // Left
            case 37:
                model.move(4);
        }
    }

    public void keyReleased(KeyEvent event) {

    }
    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.println("\n mouseClicked handleMouseClick method called\n");

        System.out.println(event.getX() + " : " + event.getY());
        int x = event.getX();
        int y = event.getY();

        model.handleMouseClick(x, y);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        model.getPlayerDrag().setPressedX(x);
        model.getPlayerDrag().setPressedY(y);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        model.getPlayerDrag().setReleasedX(x);
        model.getPlayerDrag().setReleasedY(y);

        model.dragPlayer();
    }

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int wheelRotation = e.getWheelRotation();
        model.handleMouseWheel(wheelRotation);
    }
}
