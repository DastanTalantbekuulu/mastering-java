import java.awt.Image;

public class Player extends Mobile {

    public Player(Image image, int x, int y, Area area) {
        super(image, x, y, area);
    }

    @Override
    public boolean move(Direction direction, Grid grid) {
        int newX = x + direction.getX();
        int newY = y + direction.getY();
        if (grid.getCell(newX, newY).isWalkable() && super.move(direction, grid)) {
            return true;
        } else if(grid.getCell(newX, newY) instanceof Box box && box.move(direction, grid)){
            return true;
        }
        return false;
    }


    @Override
    public char getSymbol() {
        return 'P';
    }
}