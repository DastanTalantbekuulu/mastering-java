package com.mastering.sokoban.generate;

public class SokobanRandom {
    private BoxLink box;

    // public static void main(String[] args) {
    // SokobanRandom sokobanRandom = new SokobanRandom();
    // Box box = sokobanRandom.getBox();
    // box.println();
    // }

    public BoxLink getBox() {
        int countBox = getRandom(5, 10);
        box = BoxLink.init(countBox);
        int x = getRandom(15);
        int y = getRandom(15);
        box.set(x, y);

        int dir = getRandom(4);
        int step = getRandom(5);
        x = getX(dir, step, x);
        y = getY(dir, step, y);

        while (true) {
            box.set(x, y);
            dir = getRandom(4);
            step = getRandom(5);
            x = getX(dir, step, x);
            y = getY(dir, step, y);
            if (box.isAllSet()) {
                break;
            }
        }
        int maxX = box.maxX(Integer.MIN_VALUE);
        int maxY = box.maxY(Integer.MIN_VALUE);
        int minX = box.minX(Integer.MAX_VALUE);
        int minY = box.minY(Integer.MAX_VALUE);
        if (minX < 0) {
            minX = -minX;
            maxX = maxX + minX;
            box.addX(minX);
        }
        if (minY < 0) {
            minY = -minY;
            maxY = maxY + minY;
            box.addY(minY);
        }

        if (minX < 10) {
            maxX = maxX + minX;
            box.addX(minX);
        }
        if (minY < 10) {
            maxY = maxY + minY;
            box.addY(minY);
        }
        int[][] grid = new int[maxX + 1][maxY + 1];
        return box;
    }

    public int getX(int dir, int step, int x) {
        if (dir == 2) {
            x = x + step;
        } else if (dir == 4) {
            x = x - step;
        }
        return x;
    }

    public int getY(int dir, int step, int y) {
        if (dir == 1) {
            y = y - step;
        } else if (dir == 3) {
            y = y + step;
        }
        return y;
    }

    public int getRandom(int max) {
        return getRandom(1, max);
    }

    public int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
