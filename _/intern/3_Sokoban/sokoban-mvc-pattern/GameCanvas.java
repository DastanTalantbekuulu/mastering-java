import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.Serializable;

public class GameCanvas extends Canvas implements Serializable {

    private static final long serialVersionUID = 1L;
    private Model model;
    private Image playerUp;
    private Image playerDown;
    private Image playerLeft;
    private Image playerRight;
    private Image boxImage;
    private Image boxOnTargetImage;
    private Image goalImage;
    private Image wallImage;
    private Image backgroundImage;
    private Image floorImage;
    private Image imageError;
    private boolean stateGame;
    private int startX;
    private int startY;

    public GameCanvas(Model model) {
        this.model = model;

        try {
            playerDown = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/player/playerDown.png"));
            playerUp = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/player/playerUp.png"));
            playerLeft = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/player/playerLeft.png"));
            playerRight = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/player/playerRight.png"));
            boxImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/box.png"));
            boxOnTargetImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/boxOnTarget.png"));
            goalImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/goal.png"));
            wallImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/wall.png"));
            floorImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/floor.png"));
            backgroundImage = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/background.png"));
            imageError = ImageIO.read(ResourceLoaderUtil.getInputStreamFromFile("resources/assets/error.png"));
        } catch (IOException ioe) {
            System.out.println("Error while loading the images: " + ioe);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        stateGame = model.getState();
        if (stateGame) {
            drawDesktop(g);
        } else {
            drawError(g);
        }
    }

    private void drawDesktop(Graphics g) {
        int tileSize = model.getTileSize();

        startX =  (getWidth() - tileSize * model.getLevelWidth()) / 2;
        startY =  (getHeight() - tileSize * model.getLevelHeight()) / 2;
        int x = startX;
        int y = startY;

        int[][] desktop = model.getDesktop();

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] != 2 && desktop[i][j] != -1) {
                    g.drawImage(floorImage, x, y, tileSize, tileSize, null);
                }
                // 1, 5, 6, 7 are player's direction
                if (desktop[i][j] == 1 || desktop[i][j] == 5 || desktop[i][j] == 6 || desktop[i][j] == 7) {
                    int player = desktop[i][j];
                    boolean playerOnTarget = model.isPlayerOnTarget(player);
                    if (playerOnTarget) {
                      g.drawImage(goalImage, x, y, tileSize, tileSize, null);
                    }

                    switch(player) {
                        case 1:
                        g.drawImage(playerDown, x, y, tileSize, tileSize, null);
                        break;
                        case 5:
                        g.drawImage(playerUp, x, y, tileSize, tileSize, null);
                        break;
                        case 6:
                        g.drawImage(playerLeft, x, y, tileSize, tileSize, null);
                        break;
                        case 7:
                        g.drawImage(playerRight, x, y, tileSize, tileSize, null);
                    }
                } else if (desktop[i][j] == 2) {
                    // 2 is wall
                    g.drawImage(wallImage, x, y, tileSize, tileSize, null);
                } else if (desktop[i][j] == 3) {
                    // 3 is box
                    g.drawImage(boxImage, x, y, tileSize, tileSize, null);
                } else if (desktop[i][j] == 8) {
                    // 8 is box in the target
                    g.drawImage(boxOnTargetImage, x, y, tileSize, tileSize, null);
                } else if (desktop[i][j] == 4) {
                    // 4 is target
                    g.drawImage(goalImage, x, y, tileSize, tileSize, null);
                }
                x = x + tileSize;
            }
            x = startX;
            y = y + tileSize;
        }
    }

    private void drawError(Graphics g) {
        g.drawImage(imageError, 50, 50, null);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

}
