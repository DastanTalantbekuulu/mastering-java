public class Node {
    private Type type;
    private Node top;
    private Node bottom;
    private Node right;
    private Node left;
    private int x;
    private int y;
    private boolean visited;

    private Node(Point point) {
        this(point, false);
    }

    private Node(Point point, boolean init) {
        x = point.getLocationX();
        y = point.getLocationY();
        if (init) {
            addBottom(point.getDirectionDown(), this);
            if (point.moveToRight()) {
                right = Node.create(point);
                right.left = this;
            }
        }
    }

    private static Node create(Point point) {
        Node node = new Node(point, true);
        node.setLinkLeftRight();
        return node;
    }

    public static Node create(int width, int height) {
        return create(new Point(width, height));
    }

    private void addBottom(Point point, Node top) {
        if (point.isTarget()) {
            return;
        } else if (point.moveToDown()) {
            bottom = new Node(point);
            bottom.top = top;
            top.bottom = bottom;
            bottom.addBottom(point, bottom);
        }
    }

    private void setLinkLeftRight() {
        resetVisited();
        setLinkLeftRightInternal();
    }

    private void setLinkLeftRightInternal() {
        if (visited) {
            return;
        }
        visited = true;

        Node left = get(x - 1, y);
        Node right = get(x + 1, y);
        if (left != null) {
            this.left = left;
            left.right = this;
        }
        if (right != null) {
            this.right = right;
            right.left = this;
        }
        if (top != null) {
            top.setLinkLeftRightInternal();
        }
        if (bottom != null) {
            bottom.setLinkLeftRightInternal();
        }
        if (left != null) {
            left.setLinkLeftRightInternal();
        }
        if (right != null) {
            right.setLinkLeftRightInternal();
        }
    }

    public Node get(int x, int y) {
        resetVisited();
        return getInternal(x, y);
    }

    private Node getInternal(int x, int y) {
        if (visited) {
            return null;
        }

        visited = true;

        if (this.x == x && this.y == y) {
            return this;
        }

        Node node = null;
        if (left != null) {
            node = left.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }
        if (right != null) {
            node = right.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }
        if (top != null) {
            node = top.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }
        if (bottom != null) {
            node = bottom.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }

        return null;
    }

    private void resetVisited() {
        visited = false;
        if (left != null && left.visited) {
            left.resetVisited();
        }
        if (right != null && right.visited) {
            right.resetVisited();
        }
        if (top != null && top.visited) {
            top.resetVisited();
        }
        if (bottom != null && bottom.visited) {
            bottom.resetVisited();
        }
    }

    public void setType(Type type, Coordinates direction) {
        resetVisited();
        setTypeInternal(type, direction);
    }

    private void setTypeInternal(Type type, Coordinates direction) {
        if (visited) {
            return;
        }

        visited = true;

        int dir = direction.getDirection(x, y);
        if (dir == 0) {
            this.type = type;
            return;
        }

        if (dir == 2 && right != null) {
            right.setTypeInternal(type, direction);
        }
        if (dir == 3 && bottom != null) {
            bottom.setTypeInternal(type, direction);
        }
        if (dir == 4 && left != null) {
            left.setTypeInternal(type, direction);
        }
        if (dir == 1 && top != null) {
            top.setTypeInternal(type, direction);
        }
    }

    public void log() {
        System.out.println(this);
        if (top != null) {
            System.out.println("   Top:   " + top);
        }
        if (left != null) {
            System.out.println("   Left:  " + left);
        }
        if (right != null) {
            System.out.println("  Right:  " + right);
        }
        if (bottom != null) {
            System.out.println("  Bottom: " + bottom);
        }
    }

    public String toString() {
        return "Node [type=" + type + ", (" + x + ", " + y + ")]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static class Point {
        private int dx;
        private int dy;
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            dx = x;
            dy = y;
        }

        private Point(int x, int y, int dx) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            dy = y;
        }

        public Point getDirectionDown() {
            return new Point(getLocationX(), y, 1);
        }

        public boolean isTarget() {
            return dx == 1 && dy == 1;
        }

        public boolean moveToRight() {
            if (dx > 1) {
                dx = dx - 1;
                return true;
            }
            return false;
        }

        public boolean moveToDown() {
            if (dx == 1 && dy > 1) {
                dy = dy - 1;
                return true;
            }
            return false;
        }

        public int getLocationX() {
            return x - dx + 1;
        }

        public int getLocationY() {
            return y - dy + 1;
        }
    }
}