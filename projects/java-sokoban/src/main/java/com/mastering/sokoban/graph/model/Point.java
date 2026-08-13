package com.mastering.sokoban.graph.model;

public class Point {
    private int dx;
    private int dy;
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        dx = x;
        dy = y;
    }

    private Point(int x, int y, int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        dy = y;
    }

    public Point getDirectionDown() {
        return new Point(getLocationX(), y, 1);
    }

    public boolean isTarget() {
        return dx == 1 && dy == 1;
    }

    public boolean moveToRight() {
        if (dx > 1) {
            dx = dx - 1;
            return true;
        }
        return false;
    }

    public boolean moveToDown() {
        if (dx == 1 && dy > 1) {
            dy = dy - 1;
            return true;
        }
        return false;
    }

    public int getLocationX() {
        return x - dx + 1;
    }

    public int getLocationY() {
        return y - dy + 1;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }
}
