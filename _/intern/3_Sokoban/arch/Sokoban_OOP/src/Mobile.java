import java.awt.Image;

public abstract class Mobile extends Cell {
    protected int x;
    protected int y;
    protected Area area;

    public Mobile(Image image, int x, int y, Area area) {
        super(image);
        this.x = x;
        this.y = y;
        this.area = area;
    }
    @Override
    public boolean isWalkable() {
        return false;
    }

    public boolean move(Direction direction, Grid grid){
        if (grid.move(this, direction)) {
            x = x + direction.getX();
            y = y + direction.getY();
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Area replaceArea(Area area) {
        Area oldArea = this.area;
        this.area = area;
        return oldArea;
    }
    public void setArea(Area area) {
        this.area = area;
    }

    public boolean onTarget() {
        return null != area && area instanceof Target;
    }
}
