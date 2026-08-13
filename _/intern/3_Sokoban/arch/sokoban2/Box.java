import java.awt.Graphics;

public class Box extends Mobile {

    public static int count;

    private Box prev;
    private Box next;
    private int number;
    private boolean visited;

    private Box() {
        count = count + 1;
        number = count;
    }

    private Box(Node node) {
        count = count + 1;
        number = count;
        this.node = node;
    }

    public static Box getTop() {
        return new Box();
    }

    public void draw(Graphics graphics) {
        System.out.println("DRAW_BOX");
    }

    public void ring() {
        System.out.println("RING_BOX");
    }

    public boolean addNode(Node node) {
        if (this.node == node) {
            return false;
        }
        if (this.node == null) {
            this.node = node;
            count = number;
            node.setMobile(this);
            return true;
        } else {
            if (next == null) {
                next = new Box();
                next.prev = this;
            }
            return next.addNode(node);
        }
    }

    public void reset() {
        resetVisited();
        count = 0;
        resetInternal();
    }

    private void resetInternal() {
        if (visited) {
            return;
        }
        visited = true;
        super.removeNode();
        if (next != null && !next.visited) {
            next.resetInternal();
        }
        if (prev != null && !prev.visited) {
            prev.resetInternal();
        }
    }

    private void resetVisited() {
        visited = false;
        if (next != null && next.visited) {
            next.resetVisited();
        }
        if (prev != null && prev.visited) {
            prev.resetVisited();
        }
    }

    public boolean isOnTarget() {
        if (node != null && node.isTarget() && number <= count) {
            if (next != null) {
                return next.isOnTarget();
            }
            return true;
        }
        return false;
    }

    public void println() {
        System.out.println(this);
        if (next != null) {
            next.println();
        }
    }

    public void println(int number) {
        if (this.number == number) {
            System.out.println(this);
        } else if (next != null) {
            next.println(number);
        }
    }

    public String toString() {
        return "Box [count=" + count + ", number=" + number + ", " + node + "]";
    }
}
