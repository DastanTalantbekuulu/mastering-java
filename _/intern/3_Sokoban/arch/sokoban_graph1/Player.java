import java.awt.Graphics;

public class Player extends Mobile {

    public boolean move(Coordinates coordinates) {
        return true;
    }

    public void draw(Graphics graphics) {
        System.out.println("PLAYER");
    }
    public String toString() {
        return "Player [" + node + "]";
    }
}
