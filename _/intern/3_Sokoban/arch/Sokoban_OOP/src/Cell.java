import java.awt.Image;

public abstract class Cell {

    private Image image;

    public Cell(Image image) {
        this.image = image;
    }
    public abstract boolean isWalkable();
    public abstract char getSymbol();

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

