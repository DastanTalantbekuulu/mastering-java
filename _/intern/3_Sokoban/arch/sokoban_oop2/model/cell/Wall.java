package model.cell;

import javax.swing.ImageIcon;

public class Wall extends Cell {
    public Wall(ImageIcon imageIcon){
        super(imageIcon);
    }
    public boolean isWalkable() {
        return false;
    }
    public char getSymbol() {
        return '#';
    }
}
