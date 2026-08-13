import java.awt.Graphics;

public class WallType implements Type {
    private static WallType INSTANCE;

    public static WallType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WallType();
        }
        return INSTANCE;
    }

    private WallType() {
    }

    public void draw(Graphics graphics) {
        System.out.println("DRAW_WALL");
    }

    public void ring() {
        System.out.println("RING_WALL");
    }
}
