package model.mobile;

import model.cell.Area;

import javax.swing.ImageIcon;

public class Player extends Mobile {
    public Player(ImageIcon imageIcon, ImageIcon onTarget) {
        super(imageIcon, onTarget);
    }

    public Player(ImageIcon imageIcon, ImageIcon onTarget, int x, int y, Area area) {
        super(imageIcon, onTarget, x, y, area);
    }

    public char getSymbol() {
        return 'P';
    }
}