import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Node {
    private static Stack<Node> stack = new Stack<>();
    private static Set<Node> set = new HashSet<>();
    private Type type;
    private Node top;
    private Node bottom;
    private Node right;
    private Node left;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visited;
    private boolean visitedGet;
    private Mobile mobile;

    private Node(Point point) {
        this(point, false);
    }

    private Node(Point point, boolean init) {
        x = point.getLocationX();
        y = point.getLocationY();
        width = point.getWidth();
        height = point.getHeight();
        type = AreaType.getInstance();
        if (init) {
            Point downPoint = point.getDirectionDown();
            addBottom(downPoint, this);
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
        while (!point.isTarget() && point.moveToDown()) {
            Node newNode = new Node(point);
            newNode.top = top;
            top.bottom = newNode;
            top = newNode;
        }
    }

    private void setLinkLeftRight() {
        visited = true;
        resetVisited();
        setLinkLeftRightInternal();
        resetVisited();
    }

    private void setLinkLeftRightInternal() {
        if (visited) {
            return;
        }
        visited = true;
        Node right = get(x + 1, y);
        if (right != null) {
            this.right = right;
            right.left = this;
        }
        Node left = get(x - 1, y);
        if (left != null) {
            this.left = left;
            left.right = this;
        }
        if (top != null) {
            top.setLinkLeftRightInternal();
        }
        if (right != null) {
            right.setLinkLeftRightInternal();
        }
        if (bottom != null) {
            bottom.setLinkLeftRightInternal();
        }
        if (left != null) {
            left.setLinkLeftRightInternal();
        }
    }

    private Node get(int x, int y) {
        visitedGet = true;
        resetVisitedGet();
        // return getInternal(x, y);
        Node node = getInternal(x, y);
        resetVisitedGet();
        return node;
    }

    private Node getInternal(int x, int y) {
        if (visitedGet) {
            return null;
        }
        visitedGet = true;
        if (this.x == x && this.y == y) {
            return this;
        }
        Node node = null;
        if (top != null) {
            node = top.getInternal(x, y);
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
        if (bottom != null) {
            node = bottom.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }
        if (left != null) {
            node = left.getInternal(x, y);
            if (node != null) {
                return node;
            }
        }
        return node;
    }

    private Node getInternalStack(int x, int y) {
        stack.push(this);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.x == x && current.y == y) {
                return current;
            }
            if (current.top != null)
                stack.push(current.top);
            if (current.right != null)
                stack.push(current.right);
            if (current.bottom != null)
                stack.push(current.bottom);
            if (current.left != null)
                stack.push(current.left);
        }
        return null;
    }

    private Node getInternalSet(int x, int y) {
        if (set.contains(this)) {
            return null;
        }
        set.add(this);

        if (this.x == x && this.y == y) {
            return this;
        }

        Node node = null;
        if (top != null) {
            node = top.getInternalSet(x, y);
        }
        if (node == null && right != null) {
            node = right.getInternalSet(x, y);
        }
        if (node == null && bottom != null) {
            node = bottom.getInternalSet(x, y);
        }
        if (node == null && left != null) {
            node = left.getInternalSet(x, y);
        }
        return node;
    }

    public Node get(Coordinates coordinates) {
        visitedGet = true;
        resetVisitedGet();
        Node node = getInternal(coordinates);
        resetVisitedGet();
        return node;
    }

    private Node getInternal(Coordinates coordinates) {
        if (visitedGet) {
            return null;
        }
        visitedGet = true;
        return switch (coordinates.getDirection(x, y)) {
            case 0 -> this;
            case 1 -> top != null ? top.getInternal(coordinates) : null;
            case 2 -> right != null ? right.getInternal(coordinates) : null;
            case 3 -> bottom != null ? bottom.getInternal(coordinates) : null;
            case 4 -> left != null ? left.getInternal(coordinates) : null;
            default -> null;
        };
    }

    public Node get(int direction) {
        return (direction == 1 && top != null) ? top
                : (direction == 2 && right != null) ? right
                        : (direction == 3 && bottom != null) ? bottom : (direction == 4 && left != null) ? left : null;
    }

    public void setType(Type type, Coordinates coordinates) {
        visited = true;
        resetVisited();
        setTypeInternal(type, coordinates);
        resetVisited();
    }

    private void setTypeInternal(Type type, Coordinates coordinates) {
        if (visited) {
            return;
        }
        visited = true;
        int dir = coordinates.getDirection(x, y);
        if (dir == 0) {
            this.type = type;
            return;
        }
        if (dir == 1 && top != null) {
            top.setTypeInternal(type, coordinates);
        } else if (dir == 2 && right != null) {
            right.setTypeInternal(type, coordinates);
        } else if (dir == 3 && bottom != null) {
            bottom.setTypeInternal(type, coordinates);
        } else if (dir == 4 && left != null) {
            left.setTypeInternal(type, coordinates);
        }
    }

    public Node pathFind(Coordinates coordinates) {
        resetVisited();
        Node node = pathFindInternal(coordinates);
        resetVisited();
        return node;
    }

    private Node pathFindInternal(Coordinates coordinates) {
        if (visited) {
            return null;
        }
        visited = true;
        if (coordinates.getDirection(x, y) == 0 && isWalkable()) {
            return this;
        }

        Node node = null;
        if (top != null && top.isWalkable() && !top.visited) {
            node = top.pathFindInternal(coordinates);
            if (node != null) {
                return node;
            }
        }
        if (right != null && right.isWalkable() && !right.visited) {
            node = right.pathFindInternal(coordinates);
            if (node != null) {
                return node;
            }
        }
        if (bottom != null && bottom.isWalkable() && !bottom.visited) {
            node = bottom.pathFindInternal(coordinates);
            if (node != null) {
                return node;
            }
        }
        if (left != null && left.isWalkable() && !left.visited) {
            node = left.pathFindInternal(coordinates);
            if (node != null) {
                return node;
            }
        }
        return node;
    }

    public boolean move(Coordinates coordinates, Mobile mobile) {
        if (this.mobile != null && this.mobile == mobile) {
            int dir = coordinates.getDirection();
            if (dir == 1 && top != null && top.isWalkable()) {
                mobile.setNode(top);
                return true;
            } else if (dir == 2 && right != null && right.isWalkable()) {
                mobile.setNode(right);
                return true;
            } else if (dir == 3 && bottom != null && bottom.isWalkable()) {
                mobile.setNode(bottom);
                return true;
            } else if (dir == 4 && left != null && left.isWalkable()) {
                mobile.setNode(left);
                return true;
            }
        }
        return false;
    }

    public void resetType() {
        visited = true;
        resetVisited();
        resetTypeInternal();
        resetVisited();
    }

    private void resetTypeInternal() {
        if (visited) {
            return;
        }
        visited = true;
        if (mobile != null) {
            if (mobile instanceof Box) {
                ((Box) mobile).reset();
            } else {
                mobile.removeNode();
                mobile = null;
            }
        }
        if (!(type instanceof AreaType)) {
            type = AreaType.getInstance();
        }
        if (top != null) {
            top.resetTypeInternal();
        }
        if (right != null) {
            right.resetTypeInternal();
        }
        if (bottom != null) {
            bottom.resetTypeInternal();
        }
        if (left != null) {
            left.resetTypeInternal();
        }
    }

    private void resetVisited() {
        if (!visited) {
            return;
        }
        visited = false;
        if (left != null) {
            left.resetVisited();
        }
        if (right != null) {
            right.resetVisited();
        }
        if (top != null) {
            top.resetVisited();
        }
        if (bottom != null) {
            bottom.resetVisited();
        }
    }

    private void resetVisitedGet() {
        if (!visitedGet) {
            return;
        }
        visitedGet = false;
        if (left != null) {
            left.resetVisitedGet();
        }
        if (right != null) {
            right.resetVisitedGet();
        }
        if (top != null) {
            top.resetVisitedGet();
        }
        if (bottom != null) {
            bottom.resetVisitedGet();
        }
    }

    public void print(int x, int y) {
        resetVisited();
        printInternal(x, y);
        resetVisited();
    }

    private void printInternal(int x, int y) {
        if (visited) {
            return;
        }
        visited = true;
        if (x == this.x && y == this.y && visitedGet) {
            System.out.print(" <<" + x + " " + y + " " + visitedGet + ">> ");
        }

        if (top != null) {
            top.printInternal(x, y);
        }
        if (left != null) {
            left.printInternal(x, y);
        }
        if (right != null) {
            right.printInternal(x, y);
        }
        if (bottom != null) {
            bottom.printInternal(x, y);
        }
    }

    public void println() {
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public boolean setMobile(Mobile mobile) {
        if (this.mobile == null) {
            this.mobile = mobile;
            return true;
        }
        return false;
    }

    public void removeMobile() {
        mobile = null;
    }

    public boolean isTarget() {
        return type instanceof TargetType;
    }

    public boolean isWall() {
        return type instanceof WallType;
    }

    public boolean isMobileBox() {
        return mobile instanceof Box;
    }

    public boolean isWalkable() {
        return !(isWall() || haveMobile());
    }

    public boolean haveMobile() {
        return mobile != null;
    }

    public Type getType() {
        return type;
    }

    public Node getTop() {
        return top;
    }

    public Node getRight() {
        return right;
    }

    public Node getBottom() {
        return bottom;
    }

    public Node getLeft() {
        return left;
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

        public int getWidth() {
            return x;
        }

        public int getHeight() {
            return y;
        }
    }
}