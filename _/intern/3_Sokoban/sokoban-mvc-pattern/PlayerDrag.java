public class PlayerDrag {
    private int pressedX;
    private int pressedY;
    private int releasedX;
    private int releasedY;

    public PlayerDrag() {
    }

    public void setPressedX(int x) {
        pressedX = x;
    }
    public int getPressedX() {
        return pressedX;
    }

    public void setPressedY(int y) {
        pressedY = y;
    }
    public int getPressedY() {
        return pressedY;
    }

    public void setReleasedX(int x) {
        releasedX = x;
    }

    public int getReleasedX() {
        return releasedX;
    }

    public void setReleasedY(int y) {
        releasedY = y;
    }

    public int getReleasedY() {
        return releasedY;
    }
}
