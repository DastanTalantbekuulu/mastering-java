package model;

import model.cell.Area;
import model.cell.Cell;
import model.mobile.Mobile;

public class Grid {
    private Cell[][] cells;
    private int width;
    private int height;
    private int indexW;
    private int indexH;

    public Grid(int width, int height) {
        cells = new Cell[width][height];
        this.width = width;
        this.height = height;
    }

    public boolean setCell(int x, int y, Cell cell) {
        if (checkBounds(x, y)) {
            cells[x][y] = cell;
            return true;
        }
        return false;
    }

    public boolean setMobile(int x, int y, Mobile mobile) {
        if (checkBounds(x, y) && cells[x][y].isWalkable()) {
            mobile.setArea((Area) cells[x][y]);
            cells[x][y] = mobile;
            return true;
        }
        return false;
    }

    public void setMobile(Mobile mobile) {
        cells[mobile.getX()][mobile.getY()] = mobile;
    }

    public Cell getCell(int x, int y) {
        return checkBounds(x, y) ? cells[x][y] : null;
    }

    public Cell[][] setSize(int w, int h) {
        if (checkBounds(w, h)) {
            width = w;
            height = h;
        } else if (w >= cells.length || h >= cells[0].length) {
            cells = new Cell[w][h];
            width = w;
            height = h;
        }
        return cells;
    }

    public boolean checkBounds(int x, int y) {
        return (x > -1 && y > -1 && x < width && y < height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellCount() {
        return width * height;
    }

    public void reset() {
        width = 0;
        height = 0;
    }

    public boolean hasNext() {
        return indexW < width && indexH < height;
    }

    public Cell next() {
        if (indexW - 1 < width) {
            return cells[indexW++][indexH];
        } else if (indexW == width && indexH - 1 < height) {
            indexW = 0;
            return cells[indexW][indexH++];
        }
        return null;
    }
}
