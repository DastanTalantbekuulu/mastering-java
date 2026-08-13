package com.mastering.sokoban.generate;

public class BoxLink {
    private int number;
    private int x;
    private int y;
    private boolean set;
    private BoxLink next;

    private BoxLink(int x, int y) {
        this.x = x;
        this.y = y;
        next = null;
        set = true;
    }

    private BoxLink() {
        x = 0;
        y = 0;
        next = null;
    }

    private BoxLink(int number) {
        this.number = number;
        x = 0;
        y = 0;
        next = null;
    }

    public static BoxLink init(int count) {
        BoxLink box = new BoxLink(1);
        for (int i = 1; i < count; i++) {
            box.add(new BoxLink(i + 1));
        }
        return box;
    }

    public int maxX(int x) {
        x = Math.max(x, this.x);
        if (next == null) {
            return x;
        }
        return next.maxX(x);
    }

    public int maxY(int y) {
        y = Math.max(y, this.y);
        if (next == null) {
            return y;
        }
        return next.maxY(y);
    }

    public int minY(int y) {
        y = Math.min(y, this.y);
        if (next == null) {
            return y;
        }
        return next.minY(y);
    }

    public int minX(int x) {
        x = Math.min(x, this.x);
        if (next == null) {
            return x;
        }
        return next.minX(x);
    }

    public void addX(int x) {
        this.x = this.x + x;
        if (next != null) {
            next.addX(x);
        }
    }

    public void addY(int y) {
        this.y = this.y + y;
        if (next != null) {
            next.addY(y);
        }
    }

    public BoxLink get(int x, int y) {
        if (this.x == x && this.y == y) {
            return this;
        }
        if (next == null) {
            return null;
        }
        return next.get(x, y);
    }

    public void add(BoxLink box) {
        box.next = next;
        next = box;
    }

    public void add(int x, int y) {
        if (next == null) {
            next = new BoxLink(x, y);
        } else {
            next.add(x, y);
        }
    }

    public boolean set(int x, int y) {
        if (next == null && set || this.x == x && this.y == y) {
            return false;
        } else if (!set) {
            this.x = x;
            this.y = y;
            set = true;
            return true;
        }
        return next.set(x, y);
    }

    public boolean isAllSet() {
        if (!set) {
            return false;
        }
        if (next == null && set) {
            return true;
        }
        return next.isAllSet();
    }

    public boolean constains(int x, int y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        if (next == null) {
            return false;
        }
        return next.constains(x, y);
    }

    public void print() {
        System.out.print(this);
        if (next == null) {
            return;
        }
        next.print();
    }

    public void println() {
        System.out.println(this);
        if (next == null) {
            return;
        }
        next.println();
    }

    public String toString() {
        return "Box" + number + " (" + x + ", " + y + ") ";
    }
}
