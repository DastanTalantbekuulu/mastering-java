public class Coordinates {
    private int tx;
    private int ty;

    public Coordinates() {
        tx = 0;
        ty = 0;
    }

    public Coordinates(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public int getDirection(int x, int y) {
        // isTarget
        if (tx == x && ty == y) {
            return 0;
            // toDown Вниз
        } else if (tx == x && ty > y) {
            return 3;
            // toUp Вверх
        } else if (tx == x && ty < y) {
            return 1;
            // toRight Вправо
        } else if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
            return 2;
            // toLeft Влево
        } else if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
            return 4;
            // else
        } else {
            return -1;
        }
    }

    public void setCoordinates(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public int getX() {
        return tx;
    }

    public int getY() {
        return ty;
    }
}
