import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Canvas extends JPanel {
    private Model model;
    private Image imageGamer;
    private Image imageWall;
    private Image imageBox;
    private Font fontStart;

    public Canvas(Model model) {
        this.model = model;
        File fileGamer = new File("images/gamer.png");
        File fileWall = new File("images/wall.png");
        File fileBox = new File("images/box.png");
        try {
            imageGamer = ImageIO.read(fileGamer);
            imageWall = ImageIO.read(fileWall);
            imageBox = ImageIO.read(fileBox);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        fontStart = new Font("Arial", Font.BOLD, 30);
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        boolean statGame = model.getState();
        if(statGame) {
            drawDesktop(graphics);
            drawStartGameButton(graphics);
        } else {
            drawError(graphics);
        }

    }
    private void drawError(Graphics graphics){
        graphics.setColor(Color.RED);
        graphics.setFont(graphics.getFont().deriveFont(40f));
        graphics.drawString("Level Error", 150, 300);
    }
    private void drawDesktop(Graphics graphics) {
        int start = 50;
        int x = start;
        int y = start;
        int width = 50;
        int height = 50;
        int offset = 0;
        int[][] desktop = model.getDesktop();
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    graphics.drawImage(imageGamer, x, y, width, height, null);
                } else if (desktop[i][j] == 2) {
                    graphics.drawImage(imageWall, x, y, width, height, null);
                } else if (desktop[i][j] == 3) {
                    graphics.drawImage(imageBox, x, y, width, height, null);
                } else if (desktop[i][j] == 4) {
                    graphics.setColor(Color.YELLOW);
                    graphics.fillRect(x, y, width, height);
                }
                x = x + offset + width;
            }
            x = start;
            y = y + height + offset;
        }
    }
    public void drawStartGameButton(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillRect(600, 50, 100, 50);
        graphics.setColor(Color.BLACK);
        graphics.setFont(fontStart);
        graphics.drawString("Start", 610, 80);
    }
}
