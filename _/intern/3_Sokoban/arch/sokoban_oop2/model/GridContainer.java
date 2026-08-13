package model;

import model.cell.Area;
import model.cell.Target;
import model.cell.Wall;
import model.mobile.Box;
import model.mobile.Mobile;
import model.mobile.Player;

import java.util.List;

public class GridContainer {
    private GridFactory factory;
    private Grid grid;
    private char[][] map;
    private Player player;
    private Wall wall;
    private Area area;
    private Target target;
    private Boxes boxes;

    public GridContainer(char[][] map) {
        this.map = map;
        factory = new GridFactory();
        player = factory.createPlayer();
        wall = factory.createWall();
        area = factory.createArea();
        target = factory.createTarget();
        boxes = factory.createBoxes();
    }

    public Grid getGrid() {
        if (grid == null) {
            grid = factory.createGrid(map);
            factory.loadGrid(grid, map, this);
        }
        return grid;
    }

    public Grid setLevel(char[][] map) {
        this.map = map;
        reset();
        return factory.loadGrid(grid, map, this);
    }

    public boolean move(Direction direction) {
        if (grid.getCell(direction.getX(player), direction.getY(player)) instanceof Area area) {
            grid.setCell(player.getX(), player.getY(), player.replaceArea(area));
            direction.setLocation(player);
            grid.setMobile(player);
            return true;
        } else if (grid.getCell(direction.getX(player), direction.getY(player)) instanceof Box box &&
                move(box, direction)) {
            return move(player, direction);
        }
        return false;
    }

    public boolean move(Mobile mobile, Direction direction) {
        if (grid.getCell(direction.getX(mobile), direction.getY(mobile)) instanceof Area area) {
            grid.setCell(mobile.getX(), mobile.getY(), mobile.replaceArea(area));
            direction.setLocation(mobile);
            grid.setMobile(mobile);
            return true;
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(int x, int y) {
        player.setLocation(x, y);
        grid.setMobile(x, y, player);
    }

    public List<Box> getBoxes() {
        return boxes.getBoxList();
    }

    public void setBox(int x, int y) {
        grid.setMobile(x, y, boxes.setLocation(x, y));
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(int x, int y) {
        grid.setCell(x, y, wall);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(int x, int y) {
        grid.setCell(x, y, area);
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(int x, int y) {
        grid.setCell(x, y, target);
    }

    public void reset() {
        player.reset();
        boxes.reset();
        grid.reset();
    }

    public boolean isAllOnTarget() {
        return boxes.isAllOnTarget();
    }

}
