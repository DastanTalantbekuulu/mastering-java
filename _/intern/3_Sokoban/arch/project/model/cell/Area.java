package model.cell;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Area extends Cell {
    public Area(ImageIcon imageIcon) {
        super(imageIcon);
    }
    public boolean isWalkable() {
        return true;
    }
    public char getSymbol() {
        return ' ';
    }
}
