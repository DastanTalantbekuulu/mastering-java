package com.mastering.sokoban.graph.model;

import java.awt.Graphics;

public class Vertex {

    private static Vertex INSTANCE;

    public static Vertex getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Vertex(5, 5);
        }
        return INSTANCE;
    }

    static {
        width = 32;
        row = new StringBuilder();
        map = new StringBuilder();
    }

    public static int width;
    private static int maxX;
    private static int maxY;
    private static StringBuilder row;
    private static StringBuilder map;
    private boolean visited;
    private Vertex top;
    private Vertex bottom;
    private Vertex right;
    private Vertex left;
    private int x;
    private int y;
    private Mobile mobile;
    private Type type;

    private Vertex(Vertex parent, int dir, Coordinates coordinates, Type type) {
        if (dir == 1) {
            x = parent.x;
            y = parent.y - 1;
            bottom = parent;
        } else if (dir == 2) {
            x = parent.x + 1;
            y = parent.y;
            left = parent;
        } else if (dir == 3) {
            x = parent.x;
            y = parent.y + 1;
            top = parent;
        } else if (dir == 4) {
            x = parent.x - 1;
            y = parent.y;
            right = parent;
        }
        if (top == null && y > 0) {
            top = parent.get(x, y - 1);
            if (top != null) {
                top.bottom = this;
            }
        }
        if (right == null) {
            right = parent.get(x + 1, y);
            if (right != null) {
                right.left = this;
            }
        }
        if (bottom == null) {
            bottom = parent.get(x, y + 1);
            if (bottom != null) {
                bottom.top = this;
            }
        }
        if (left == null && x > 0) {
            left = parent.get(x - 1, y);
            if (left != null) {
                left.right = this;
            }
        }
        dir = coordinates.getDirection(x, y);
        if (dir == 0) {
            this.type = type;
        }
    }

    private Vertex(Coordinates coordinates) {
        x = coordinates.getX();
        y = coordinates.getY();
    }

    private Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 0 area
     * 1 player
     * 2 player on target
     * 3 box
     * 4 box on target
     * 5 target
     * 9 wall
     */
    public static Vertex load(Player player, Box boxes, Coordinates coordinates, String map) {
        Vertex vertex = getInstance();
        vertex.reset();
        boxes.reset();
        int i = -1;
        int x = 0;
        int y = 1;
        char c;
        while (++i < map.length()) {
            c = map.charAt(i);
            x = x + 1;
            if (c == ' ') {
                continue;
            }
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            if (c == Grid.NEWLINE) {
                x = 0;
                y = y + 1;
            } else if (c == Grid.AREA || c == Grid.PLAYER || c == Grid.BOX) {
                Vertex.set(coordinates.set(x, y), AreaType.getInstance());
            } else if (c == Grid.WALL) {
                Vertex.set(coordinates.set(x, y), WallType.getInstance());
            } else if (c == Grid.TARGET || c == Grid.PLAYER_ON_TARGET || c == Grid.BOX_ON_TARGET) {
                Vertex.set(coordinates.set(x, y), TargetType.getInstance());
            }
            if (c == Grid.PLAYER || c == Grid.PLAYER_ON_TARGET) {
                player.setVertex(Vertex.getInstance().get(x, y));
            } else if (c == Grid.BOX || c == Grid.BOX_ON_TARGET) {
                boxes.addVertex(Vertex.getInstance().get(x, y));
            }
        }
        return getInstance();
    }

    private static Vertex set(Coordinates coordinates, Type type) {
        if (INSTANCE == null) {
            INSTANCE = new Vertex(coordinates);
            INSTANCE.type = type;
            return INSTANCE;
        }
        Vertex vertex = INSTANCE.get(coordinates.getX(), coordinates.getY());
        if (vertex == null) {
            INSTANCE = INSTANCE.setInternal(coordinates, type);
            return INSTANCE;
        }
        vertex.type = type;
        INSTANCE = vertex;
        return vertex;
    }

    private Vertex setInternal(Coordinates coordinates, Type type) {
        int dir = coordinates.getDirection(x, y);
        if (dir == 0) {
            this.type = type;
            INSTANCE = this;
            return this;
        } else if (dir == 1) {
            if (top == null) {
                top = new Vertex(this, dir, coordinates, type);
            }
            return top.setInternal(coordinates, type);
        } else if (dir == 2) {
            if (right == null) {
                right = new Vertex(this, dir, coordinates, type);
            }
            return right.setInternal(coordinates, type);
        } else if (dir == 3) {
            if (bottom == null) {
                bottom = new Vertex(this, dir, coordinates, type);
            }
            return bottom.setInternal(coordinates, type);
        } else if (dir == 4) {
            if (left == null) {
                left = new Vertex(this, dir, coordinates, type);
            }
            return left.setInternal(coordinates, type);
        }
        return null;
    }

    public Vertex get(int x, int y) {
        resetVisited();
        Vertex vertex = getInternal(x, y);
        resetVisited();
        return vertex;
    }

    private Vertex getInternal(int x, int y) {
        if (visited) {
            return null;
        }
        visited = true;
        if (this.x == x && this.y == y) {
            return this;
        }
        Vertex vertex = null;
        if (top != null) {
            vertex = top.getInternal(x, y);
            if (vertex != null) {
                return vertex;
            }
        }
        if (right != null) {
            vertex = right.getInternal(x, y);
            if (vertex != null) {
                return vertex;
            }
        }
        if (bottom != null) {
            vertex = bottom.getInternal(x, y);
            if (vertex != null) {
                return vertex;
            }
        }
        if (left != null) {
            vertex = left.getInternal(x, y);
            if (vertex != null) {
                return vertex;
            }
        }
        return vertex;
    }

    public Vertex get(Coordinates coordinates) {
        visited = true;
        resetVisited();
        Vertex vertex = getInternal(coordinates);
        resetVisited();
        return vertex;
    }

    private Vertex getInternal(Coordinates coordinates) {
        if (visited) {
            return null;
        }
        visited = true;
        return switch (coordinates.getDirection(x, y)) {
            case 0 -> this;
            case 1 -> top != null ? top.getInternal(coordinates) : null;
            case 2 -> right != null ? right.getInternal(coordinates) : null;
            case 3 -> bottom != null ? bottom.getInternal(coordinates) : null;
            case 4 -> left != null ? left.getInternal(coordinates) : null;
            default -> null;
        };
    }

    public Vertex get(int direction) {
        return (direction == 1 && top != null) ? top
                : (direction == 2 && right != null) ? right
                : (direction == 3 && bottom != null) ? bottom : (direction == 4 && left != null) ? left : null;
    }

    public Vertex pathFind(Coordinates coordinates) {
        resetVisited();
        Vertex vertex = pathFindInternal(coordinates);
        resetVisited();
        return vertex;
    }

    private Vertex pathFindInternal(Coordinates coordinates) {
        if (visited) {
            return null;
        }
        visited = true;
        if (coordinates.getDirection(x, y) == 0 && isWalkable()) {
            return this;
        }

        Vertex vertex = null;
        if (top != null && top.isWalkable() && !top.visited) {
            vertex = top.pathFindInternal(coordinates);
            if (vertex != null) {
                return vertex;
            }
        }
        if (right != null && right.isWalkable() && !right.visited) {
            vertex = right.pathFindInternal(coordinates);
            if (vertex != null) {
                return vertex;
            }
        }
        if (bottom != null && bottom.isWalkable() && !bottom.visited) {
            vertex = bottom.pathFindInternal(coordinates);
            if (vertex != null) {
                return vertex;
            }
        }
        if (left != null && left.isWalkable() && !left.visited) {
            vertex = left.pathFindInternal(coordinates);
            if (vertex != null) {
                return vertex;
            }
        }
        return vertex;
    }

    private void reset() {
        resetVisited();
        maxX = 0;
        maxY = 0;
        resetInternal();
        resetVisited();
    }

    private void resetInternal() {
        if (visited) {
            return;
        }
        visited = true;
        type = null;
        if (mobile != null) {
            if (mobile instanceof Box) {
                ((Box) mobile).reset();
            }
            mobile.removeVertex();
            mobile = null;
        }
        if (top != null) {
            top.resetInternal();
        }
        if (right != null) {
            right.resetInternal();
        }
        if (bottom != null) {
            bottom.resetInternal();
        }
        if (left != null) {
            left.resetInternal();
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

    public void printAll() {
        resetVisited();
        printAllInternal();
        resetVisited();
    }

    private void printAllInternal() {
        if (visited) {
            return;
        }
        visited = true;

        System.out.print("Node [(" + x + " " + y + ") type " + type + " mobile " + mobile);
        if (top != null) {
            System.out.print(" top(" + top.x + " " + top.y + ")");
        }
        if (left != null) {
            System.out.print(" left(" + left.x + " " + left.y + ")");
        }
        if (right != null) {
            System.out.print(" right(" + right.x + " " + right.y + ")");
        }
        if (bottom != null) {
            System.out.print(" bottom(" + bottom.x + " " + bottom.y + ")");
        }
        System.out.print("]\n");

        if (top != null) {
            top.printAllInternal();
        }
        if (left != null) {
            left.printAllInternal();
        }
        if (right != null) {
            right.printAllInternal();
        }
        if (bottom != null) {
            bottom.printAllInternal();
        }
    }

    public void print() {
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

    public void draw(Graphics graphics) {
        resetVisited();
        drawInternal(graphics);
        resetVisited();
    }

    public void drawInternal(Graphics graphics) {
        if (visited) {
            return;
        }
        visited = true;

        if (top != null) {
            top.drawInternal(graphics);
        }
        if (right != null) {
            right.drawInternal(graphics);
        }
        if (bottom != null) {
            bottom.drawInternal(graphics);
        }
        if (left != null) {
            left.drawInternal(graphics);
        }

        if (type != null) {
            type.draw(graphics, x * width, y * width);
        }
        if (mobile != null) {
            mobile.draw(graphics, x * width, y * width);
        }
    }

    public String printMap() {
        map.delete(0, map.length());
        row.delete(0, row.length());
        Vertex vertex;
        for (int y = 1; y <= maxY; y++) {
            for (int x = 1; x <= maxX; x++) {
                vertex = get(x, y);
                if (vertex == null) {
                    row.append(" ");
                } else {
                    if (vertex.type instanceof WallType) {
                        row.append("9");
                    } else if (vertex.type instanceof TargetType) {
                        row.append("5");
                    } else if (vertex.type instanceof AreaType) {
                        row.append("0");
                    } else {
                        row.append(" ");
                    }
                    if (vertex.mobile != null) {
                        if (vertex.mobile instanceof Player) {
                            if (vertex.type instanceof TargetType) {
                                row.setCharAt(row.length() - 1, '2');
                            } else if (vertex.type instanceof AreaType) {
                                row.setCharAt(row.length() - 1, '1');
                            }
                        } else if (vertex.mobile instanceof Box) {
                            if (vertex.type instanceof TargetType) {
                                row.setCharAt(row.length() - 1, '4');
                            } else if (vertex.type instanceof AreaType) {
                                row.setCharAt(row.length() - 1, '3');
                            }
                        }
                    }
                }
            }
            if (y > 1) {
                map.append("\n");
            }
            map.append(row.toString().stripTrailing());
            row.delete(0, row.length());
        }
        return map.toString();
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

    public boolean isTarget() {
        return type instanceof TargetType;
    }

    public void removeMobile() {
        mobile = null;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    public Vertex getTop() {
        return top;
    }

    public Vertex getRight() {
        return right;
    }

    public Vertex getBottom() {
        return bottom;
    }

    public Vertex getLeft() {
        return left;
    }
}