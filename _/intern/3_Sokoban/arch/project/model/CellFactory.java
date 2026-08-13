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

    public CellFactory(String baseName) {
        try {
            resource = ResourceBundle.getBundle(baseName);
        } catch (MissingResourceException mre) {
            System.err.println(baseName + " not found");
            System.exit(0);
        }
    }

    public Cell createCell(char symbol) {
        switch (symbol) {
            case '#':
                return new Wall(getImage("wallImage"));
            case 'X':
                return new Target(getImage("targetImage"));
            case '$':
                return new Box(getImage("boxImage"), getImage("boxOnTargetImage"));
            case 'P':
                return new Player(getImage("playerImage"),getImage("playerOnTargetImage"));
            default:
                return new Area(getImage("areaImage"));
        }
    }

    public ImageIcon getImage(String key) {
        if (resource.containsKey(key)) {
            return new ImageIcon(resource.getString(key));
        }
        return null;
    }
}
