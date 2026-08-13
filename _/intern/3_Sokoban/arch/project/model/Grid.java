package model;

import model.cell.Cell;

public class Grid {
    private Cell[][] cells;
    private int width;
    private int height;
    private CellContainer container;

    public Grid(int width, int height) {
        cells = new Cell[width][height];
        this.width = width;
        this.height = height;
    }

    public boolean setCell(int x, int y, Cell cell) {
        if (x < -1 || y < -1 || x <= width || y <= height) {
            cells[x][y] = cell;
            return true;
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
