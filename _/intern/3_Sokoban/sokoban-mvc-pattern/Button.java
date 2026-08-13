import java.awt.Font;
import java.awt.Graphics;

public interface Button {

    void draw(Graphics graphics);

    void mouseDragged(int x, int y);

    void mouseMoved(int x, int y);

    void mouseClicked(int button, int x, int y);

    void mousePressed(int button, int x, int y);

    void mouseReleased(int button, int x, int y);

    void mouseEntered(int x, int y);

    void mouseExited(int x, int y);

    void mouseWheelMoved(int wheelRotation, int x, int y);

    void setBounds(int x, int y, int width, int height);

    void setFont(Font font);

}
