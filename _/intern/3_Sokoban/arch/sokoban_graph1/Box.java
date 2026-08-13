import java.awt.Graphics;

public class Box extends Mobile {

    public static int count;

    private Box next;
    private int number;

    private Box() {
        System.out.print("\n " + count);
        count = count + 1;
        number = count;
        System.out.print(" " + count + " ");
    }

    private Box(Node node) {
        System.out.print("\n " + count);
        count = count + 1;
        number = count;
        this.node = node;
        System.out.print(" " + count + " ");
    }

    public static Box create() {
        return new Box();
    }

    public boolean move(Coordinates coordinates) {
        return true;
    }

    public void draw(Graphics graphics) {
        System.out.println("BOX");
    }

    public void setNode(Node node) {
        if (this.node == null) {
            this.node = node;
            count = count + 1;
            return;
        }
        if (next == null) {
            next = new Box(node);
        } else {
            next.setNode(node);
        }
    }

    public void reset() {
        count = 1;
        node = null;
        if (number > 1 && next != null) {
            next.reset();
        }
    }

    public void log() {
        System.out.println(this);
        if (next != null) {
            next.log();
        }
    }

    public String toString() {
        return "Box [number=" + number + ", " + node + "]";
    }

}
