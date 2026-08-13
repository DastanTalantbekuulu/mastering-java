package model;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(1, 0),
    RIGHT(-1, 0);

    private int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getX(int x) {
        x = x + dx;
        return x;
    }

    public int getY(int y) {
        y = y + dy;
        return y;
    }
}
