package model;

import model.cell.Area;
import model.mobile.Box;
import model.mobile.Mobile;

public  class GridMovement {
    public static boolean move(Mobile mobile, Direction direction, Grid grid) {
        int x = direction.getX(mobile.getX());
        int y = direction.getY(mobile.getY());
        return moveMobile(mobile, direction, grid) ||
                (grid.getCell(x, y) instanceof Box box && moveMobile(box, direction, grid));
    }

    private static boolean moveMobile(Mobile mobile, Direction direction, Grid grid) {
        int x = direction.getX(mobile.getX());
        int y = direction.getY(mobile.getY());
       return  (grid.getCell(x, y) instanceof Area area &&
                grid.setCell(mobile.getX(), mobile.getY(), mobile.replaceArea(area)));
    }
}
