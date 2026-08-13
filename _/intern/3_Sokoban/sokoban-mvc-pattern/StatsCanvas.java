import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.io.Serializable;

public class StatsCanvas extends Canvas implements Serializable {

    private static final long serialVersionUID = 1L;
    private int moveCount;
    private int elapsedTime;
    private Font pixelFont;
    private Image backgroundImage;
    private int currentLevel;
    private HashMap<Integer, Image> imageMap;
    private Image currentImage;
    private int currentStep;

    public StatsCanvas(Model model) {
        super(model);
        try {
           pixelFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoaderUtil.getInputStreamFromFile("resources/assets/minecraft.ttf")).deriveFont(24f);
           backgroundImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/Stats.png"));
       } catch (IOException | FontFormatException e) {
            System.out.println("Error while loading the resources: " + e.getMessage());
        }
        moveCount = 0;
        currentLevel = 1;
        imageMap = new HashMap<>();
        currentStep = 1;
        loadImages();
    }

    public void loadImages() {
        try {
        imageMap.put(1, ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/heart/heart1-1.png")));
        imageMap.put(2, ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/heart/heart1-2.png")));
        imageMap.put(3, ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/heart/heart1-3.png")));
        imageMap.put(4, ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/heart/heart1-4.png")));
    } catch (IOException ioe) {
            System.out.println("Error while loading the resources: " + ioe.getMessage());
        }
        currentImage = imageMap.get(1);
    }

    public void showImage(int step) {
      currentStep = step;
      currentImage = imageMap.get(step);
      repaint();
  }

    public void updateMoveCount(int count) {
        this.moveCount = count;
        repaint();
    }

    public void updateTime(int seconds) {
        this.elapsedTime = seconds;
        repaint();
    }

    public void updateLevel(int level) {
        this.currentLevel = level;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g.setFont(pixelFont);
        g.setColor(Color.WHITE);
        g.drawString("Moves: " + moveCount, 30, 60);

        int minutes = getMinutes();
        int seconds = getSeconds();
        g.drawString(String.format("Time: %02d:%02d", minutes, seconds), 220, 60);

        g.drawString("Level: " + currentLevel, 440, 60);

        g.drawImage(currentImage, 600, 15, 192, 64, null);
    }

    public int getMinutes() {
        return elapsedTime / 60;
    }

    public int getSeconds() {
        return elapsedTime % 60;
    }
}
