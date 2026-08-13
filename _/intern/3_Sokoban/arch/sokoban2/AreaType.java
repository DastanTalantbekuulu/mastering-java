import java.awt.Graphics;

public class AreaType implements Type {

    private static AreaType INSTANCE;

    public static AreaType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AreaType();
        }
        return INSTANCE;
    }

    private AreaType() {
    }

    public void draw(Graphics graphics) {
        System.out.println("DRAW_AREA");
    }

    public void ring() {
        System.out.println("RING_AREA");
    }

}
