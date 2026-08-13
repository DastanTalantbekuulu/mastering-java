import java.awt.Graphics;

public class TargetType implements Type {
    private static TargetType INSTANCE;

    public static TargetType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TargetType();
        }
        return INSTANCE;
    }

    private TargetType() {
    }

    public void draw(Graphics graphics) {
        System.out.println("DRAW_TARGET");
    }

    public void ring() {
        System.out.println("RING_TARGET");
    }
}
