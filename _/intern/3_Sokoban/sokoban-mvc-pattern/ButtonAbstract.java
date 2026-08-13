import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.FontFormatException;

public abstract class ButtonAbstract implements Button {
    protected String name;
    protected JPanel parent;
    protected Mediator mediator;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    private Font font;
    private int textOffsetY;
    private int textOffsetX;

    private boolean hover;
    private Image background;
    private Image backgroundOver;
    private final Color hoverColor;
    private final Color borderColor;
    private final Color textColor;

    public ButtonAbstract(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
        try {
           font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoaderUtil.getInputStreamFromFile("resources/assets/minecraft.ttf")).deriveFont(14f);
           background = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/button.png"));
           backgroundOver = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/buttonOver.png"));
       } catch (IOException | FontFormatException e) {
            System.out.println("Error while loading the resources: " + e.getMessage());
        }
        hoverColor = new Color(137, 208, 240);
        borderColor = new Color(51, 51, 51);
        textColor = new Color(51, 51, 51);
    }

    public void draw(Graphics graphics) {
        if (hover) {
            graphics.drawImage(backgroundOver, x, y, width, height, null);
        } else {
            graphics.drawImage(background, x, y, width, height, null);
        }
        graphics.setColor(borderColor);
        graphics.drawRect(x, y, width, height);
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        graphics.drawString(name, textOffsetX, textOffsetY);
    }

    public void setParent(JPanel parent) {
        this.parent = parent;
        textOffsetY = y + height / 2 + parent.getFontMetrics(font).getAscent() / 2;
        textOffsetX = x + width / 2 - parent.getFontMetrics(font).stringWidth(name) / 2;
    }

    protected boolean focusOnButton(int x, int y) {
        return this.x < x && x < this.x + width && this.y < y && y < this.y + height;
    }

    // MouseMotionListener
    public void mouseDragged(int x, int y) {
    }

    // MouseMotionListener
    public void mouseMoved(int x, int y) {
        hover = focusOnButton(x, y);
        parent.repaint();
    }

    // MouseListener
    public abstract void mouseClicked(int button, int x, int y);

    // MouseListener
    public void mousePressed(int button, int x, int y) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        textOffsetX = x + width / 2 - parent.getFontMetrics(font).stringWidth(name) / 2;
    }
    // MouseListener
    public void mouseReleased(int button, int x, int y) {
    }

    // MouseListener
    public void mouseEntered(int x, int y) {
    }

    // MouseListener
    public void mouseExited(int x, int y) {
    }

    // MouseWheelListener
    public void mouseWheelMoved(int wheelRotation, int x, int y) {
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
