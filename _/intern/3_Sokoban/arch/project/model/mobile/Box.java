package model.mobile;

import model.cell.Area;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Box extends Mobile {
    public Box(ImageIcon imageIcon, ImageIcon onTarget) {
        super(imageIcon, onTarget);
    }

    public Box(ImageIcon imageIcon, ImageIcon onTarget, int x, int y, Area area) {
        super(imageIcon,onTarget, x, y, area);
    }

    public char getSymbol() {
        return '$';
    }
}
