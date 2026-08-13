package model.cell;

import javax.swing.ImageIcon;

public abstract class Cell {
    private ImageIcon imageIcon;
    public Cell(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    public abstract boolean isWalkable();
    public abstract char getSymbol();
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}

