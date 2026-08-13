package model;

import model.mobile.Mobile;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getX(Mobile mobile) {
        return mobile.getX() + dx;
    }

    public int getY(Mobile mobile) {
        return mobile.getY() + dy;
    }

    public void setLocation(Mobile mobile) {
        mobile.setLocation(mobile.getX() + dx, mobile.getY() + dy);
    }
}
