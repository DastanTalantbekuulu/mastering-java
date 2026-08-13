import java.awt.Image;

public class Target extends Area {
    public Target(Image image){
        super(image);
    }
    @Override
    public char getSymbol() {
        return 'X';
    }
}
