import java.awt.Graphics;

public abstract class Mobile implements Type {
    protected Node node;

    public abstract void draw(Graphics graphics);

    public abstract void ring();

    public boolean move(Coordinates coordinates) {
        if (node != null) {
            return node.move(coordinates, this);
        }
        return false;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        removeNode();
        this.node = node;
        node.setMobile(this);
    }

    public void removeNode() {
        if (node != null) {
            node.removeMobile();
            node = null;
        }
    }

    public String toString() {
        return "Mobile [" + getClass() + "]";
    }
}
