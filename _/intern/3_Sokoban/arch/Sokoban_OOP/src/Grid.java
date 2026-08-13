import java.util.List;

public class Grid {
    private Cell[][] cells;
    private List<Box> boxes;
    private List<Target> targets;
    private Area area;

    public boolean move(Mobile mobile, Direction direction) {
        int x = mobile.getX() + direction.getX();
        int y = mobile.getY() + direction.getY();

        if (getCell(x, y) instanceof Area area) {
            setCell(mobile.getX(), mobile.getY(), mobile.replaceArea(area));
            return true;
        }
        return false;
    }

    public void setCell(int x, int y, Cell cell) {
        cells[x][y] = cell;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
