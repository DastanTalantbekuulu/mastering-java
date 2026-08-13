import java.awt.Graphics;

public abstract class Mobile implements Type {
    protected Node node;

    public abstract void draw(Graphics graphics);

    public boolean move(Coordinates coordinates) {
        return true;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
