package com.mastering.sokoban.generate.model;

public class Coordinates {
    private int tx;
    private int ty;

    public Coordinates() {
        tx = 0;
        ty = 0;
    }

    public Coordinates(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public int getDirection(int x, int y) {
        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
            return -1;
        }
        // OnTarget
        if (tx == x && ty == y) {
            return 0;
        } else if (tx < x) {
            // toLeft Влево
            return 4;
        } else if (ty < y) {
            // toUp Вверх
            return 1;
        } else if (ty > y) {
            // toDown Вниз
            return 3;
        } else
//            if (tx > x)
        {
            // toRight Вправо
            return 2;
        }
    }
    public int getDirection(Vertex vertex) {
        int x = vertex.getX();
        int y = vertex.getY();
        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
            return -1;
        }
        if (tx == x && ty == y) {
            return 0;
        }
        // Диагональные перемещения
//        if (tx > x && ty > y) return 5; // Вниз-вправо
//        if (tx > x && ty < y) return 6; // Вверх-вправо
//        if (tx < x && ty > y) return 7; // Вниз-влево
//        if (tx < x && ty < y) return 8; // Вверх-влево

        if (tx < x) return 4;  // Влево
        if (ty > y) return 3;  // Вниз
        if (ty < y) return 1;  // Вверх
        if (tx > x) return 2;  // Вправо

        return -1;
    }
    public int getDirection() {
        // OnTarget
        if (tx == 0 && ty == -1) {
            // toUp Вверх
            return 1;
        } else if (tx == 1 && ty == 0) {
            // toRight Вправо
            return 2;
        } else if (tx == 0 && ty == 1) {
            // toDown Вниз
            return 3;
        } else if (tx == -1 && ty == 0) {
            // toLeft Влево
            return 4;
        } else {
            return -1;
        }
    }

    public void setDirection(int direction) {
        switch (direction) {
            // toUp Вверх
            case 1:
                tx = 0;
                ty = -1;
                break;
            // toRight Вправо
            case 2:
                tx = 1;
                ty = 0;
                break;
            // toDown Вниз
            case 3:
                tx = 0;
                ty = 1;
                break;
            // toLeft Влево
            case 4:
                tx = -1;
                ty = 0;
                break;
            default:
                tx = 0;
                ty = 0;
        }
    }

    public Coordinates set(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        return this;
    }

    public int getX() {
        return tx;
    }

    public int getY() {
        return ty;
    }

    public void print() {
        System.out.println(this);
    }

    public String toString() {
        return "Coordinates [x=" + tx + ", y=" + ty + "]";
    }
}
