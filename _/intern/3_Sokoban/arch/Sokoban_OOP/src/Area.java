import java.awt.Image;

public class Area extends Cell {

    public Area(Image image) {
        super(image);
    }
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public char getSymbol() {
        return ' ';
    }
}
