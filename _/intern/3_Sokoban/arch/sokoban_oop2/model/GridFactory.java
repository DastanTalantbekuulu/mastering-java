package model;

import model.cell.Area;
import model.cell.Target;
import model.cell.Wall;
import model.mobile.Player;

class GridFactory {
    private CellFactory cellFactory;

    public GridFactory() {
        cellFactory = new CellFactory();
    }
    public Grid createGrid(char[][] map) {
        return new Grid(50, 50);
    }
    public Grid loadGrid(Grid grid, char[][] map, GridContainer container) {
        if(grid == null) {
            grid = new Grid(50, 50);
        }
        grid.setSize(map.length, map[0].length);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '#') {
                    container.setWall(i, j);
                } else if (map[i][j] == 'X' || map[i][j] == 'I' || map[i][j] == 'Z') {
                    container.setTarget(i, j);
                } else {
                    container.setArea(i, j);
                }
                if (map[i][j] == 'P' || map[i][j] == 'I') {
                    container.setPlayer(i, j);
                }
                if (map[i][j] == 'B' || map[i][j] == 'Z') {
                    container.setBox(i, j);
                }
            }
        }
        return grid;
    }

    public Player createPlayer() {
        return (Player) cellFactory.createCell('P');
    }

    public Boxes createBoxes() {
        return new Boxes(cellFactory);
    }

    public Wall createWall() {
        return (Wall) cellFactory.createCell('#');
    }

    public Area createArea() {
        return (Area) cellFactory.createCell(' ');
    }

    public Target createTarget() {
        return (Target) cellFactory.createCell('X');
    }


}
