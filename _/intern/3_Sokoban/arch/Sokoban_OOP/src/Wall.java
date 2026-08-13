import java.awt.Image;

public class Wall extends Cell {
    public Wall(Image image){
        super(image);
    }
    @Override
    public boolean isWalkable() {
        return false;
    }
    @Override
    public char getSymbol() {
        return '#';
    }
}
