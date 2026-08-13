import java.awt.Image;

public class Box extends Mobile {
    public Box(Image image, int x, int y, Area area) {
        super(image, x, y, area);
    }

    @Override
    public char getSymbol() {
        return '$';
    }
}
