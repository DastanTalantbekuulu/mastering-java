package model;

import model.cell.Area;
import model.cell.Cell;
import model.cell.Target;
import model.cell.Wall;
import model.mobile.Box;
import model.mobile.Player;

import javax.swing.ImageIcon;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CellFactory {
    private ResourceBundle resource;
    private ImageIcon player;
    private ImageIcon playerOnTarget;
    private ImageIcon box;
    private ImageIcon boxOnTarget;
    private ImageIcon wall;
    private ImageIcon area;
    private ImageIcon target;

    public CellFactory() {
        try {
            resource = ResourceBundle.getBundle("resources.Sokoban");
        } catch (MissingResourceException mre) {
            System.err.println("resources.cell not found");
            System.exit(0);
        }
        player = getImage("playerImage");
        playerOnTarget = getImage("playerOnTargetImage");
        box = getImage("boxImage");
        boxOnTarget = getImage("boxOnTargetImage");
        wall = getImage("wallImage");
        area = getImage("areaImage");
        target = getImage("targetImage");
    }

    public Cell createCell(char symbol) {
        return switch (symbol) {
            case '#' -> new Wall(wall);
            case 'X' -> new Target(target);
            case 'B' -> new Box(box, boxOnTarget);
            case 'P' -> new Player(player, playerOnTarget);
            default -> new Area(area);
        };
    }

    public ImageIcon getImage(String key) {
        if (resource.containsKey(key)) {
            return new ImageIcon(resource.getString(key));
        }
        return null;
    }
}
