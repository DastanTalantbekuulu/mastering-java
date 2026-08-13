import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.Serializable;

public class PanelCanvas extends Canvas implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Button> buttons;
    private Image backgroundImage;

    public PanelCanvas(Model model) {
        this.model = model;

        try {
           backgroundImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/PanelCanvas.png"));
       } catch (IOException e) {
            System.out.println("Error while loading the resources: " + e.getMessage());
        }

        buttons = new ArrayList<>();
        MenuBarListener menuBarListener = new MenuBarListener(this);
        addMouseListener(menuBarListener);
        addMouseMotionListener(menuBarListener);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        boolean stateGame = model.getState();
        if (stateGame) {
            drawButtons(g);
        }
    }

    private void drawButtons(Graphics g) {
        buttons.forEach(button -> button.draw(g));
    }

    public void addButton(Button button) {
        buttons.add(button);
        ((ButtonAbstract) button).setParent(this);
    }

    public void mouseMoved(int x, int y) {
        buttons.forEach(button -> button.mouseMoved(x, y));
    }

    public void mouseClicked(int button, int x, int y) {
        buttons.forEach(but -> but.mouseClicked(button, x, y));
    }

    public void mousePressed(int button, int x, int y) {
        buttons.forEach(but -> but.mousePressed(button, x, y));
    }
}
