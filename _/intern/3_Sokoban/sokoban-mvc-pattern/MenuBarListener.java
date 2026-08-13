import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuBarListener implements MouseMotionListener, MouseListener {

    private PanelCanvas menuBar;

    public MenuBarListener(PanelCanvas menuBar) {
        this.menuBar = menuBar;
    }

    public void mouseClicked(MouseEvent event) {
        menuBar.mouseClicked(event.getButton(), event.getX(), event.getY());
    }

    public void mousePressed(MouseEvent event) {
        menuBar.mousePressed(event.getButton(), event.getX(), event.getY());
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mouseDragged(MouseEvent event) {
    }

    public void mouseMoved(MouseEvent event) {
        menuBar.mouseMoved(event.getX(), event.getY());
    }
}
