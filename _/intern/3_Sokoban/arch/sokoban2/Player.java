import java.awt.Graphics;

public class Player extends Mobile {
    public boolean move(Coordinates coordinates) {
        if(node != null) {
            if (super.move(coordinates)) {
                return true;
            } else {
                int direction = coordinates.getDirection();
                Node tempNode = node.get(direction);
                if (tempNode != null && tempNode.isMobileBox() && tempNode.getMobile().move(coordinates)) {
                    return super.move(coordinates);
                }
            }
        }
        return false;
    }

    public boolean teleportation(Coordinates coordinates) {
        if (node != null) {
            Node target = node.pathFind(coordinates);
            if (target != null) {
                node.removeMobile();
                node = target;
                target.setMobile(this);
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics graphics) {
        System.out.println("DRAW_PLAYER");
    }

    public void ring() {
        System.out.println("RING_PLAYER");
    }

    public void println() {
        System.out.println(this);
    }

    public String toString() {
        return "Player [" + node + "]";
    }
}
