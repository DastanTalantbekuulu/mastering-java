package model.mobile;

import model.cell.Area;
import model.cell.Cell;
import model.cell.Target;

import javax.swing.ImageIcon;

public abstract class Mobile extends Cell {
    protected int x;
    protected int y;
    protected Area area;
    protected ImageIcon onTarget;

    public Mobile(ImageIcon imageIcon, ImageIcon onTarget) {
        super(imageIcon);
        this.onTarget = onTarget;
        x = -1;
        y = -1;
    }

    public Mobile(ImageIcon image, ImageIcon onTarget, int x, int y, Area area) {
        super(image);
        this.onTarget = onTarget;
        this.x = x;
        this.y = y;
        this.area = area;
    }

    public boolean isWalkable() {
        return false;
    }

    public ImageIcon getImageIcon() {
        if (isOnTarget()) {
            return onTarget;
        }
        return super.getImageIcon();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Area replaceArea(Area area) {
        Area oldArea = this.area;
        this.area = area;
        return oldArea;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public boolean isOnTarget() {
        return null != area && area instanceof Target;
    }
}
