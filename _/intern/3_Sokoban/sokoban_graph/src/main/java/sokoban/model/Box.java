package sokoban.model;

import sokoban.model.board.Vertex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Box extends Mobile {

    public static int count;
    private static String boxImageCode;
    private static String boxOnTargetImageCode;
    private static BufferedImage boxImage;
    private static BufferedImage boxOnTargetImage;
    private static Color color0;
    private static Color color1;
    private static Color color2;

    private static Color color6;
    private static Color color7;
    private static Color color8;
    private static Color color9;

    static {
        boxImageCode = """
                99999999999999999999999999999999
                99666666666666666666666666666699
                96966666666666666666666666666969
                96696666666666666666666666669669
                96669666666666666666666666696669
                96666999999999999999999999966669
                96666988896669666696669777966669
                96666988889669666696697777966669
                96666988888969666696977777966669
                96666998888899666699777779966669
                96666969888889666697777796966669
                96666966988888966977777966966669
                96666966998888899777779966966669
                96666966969888889777796966966669
                96666966966988888977966966966669
                96666966966698888899666966966669
                96666966966699888889666966966669
                96666966966977988888966966966669
                96666966969777798888896966966669
                96666966997777799888889966966669
                96666966977777966988888966966669
                96666969777779666698888896966669
                96666997777799666699888889966669
                96666977777969666696988888966669
                96666977779669666696698888966669
                96666977796669666696669888966669
                96666999999999999999999999966669
                96669666666666666666666666696669
                96696666666666666666666666669669
                96966666666666666666666666666969
                99666666666666666666666666666699
                99999999999999999999999999999999
                """;
        boxOnTargetImageCode = """
                22200000000022222222000000000222
                22000000000022222222000000000022
                20001111111122222222111111110002
                00011111111122222222111111111000
                00111222222222222222222222211100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112229999999999999999992221100
                00112229666666666666666692221100
                00112229666666666666666692221100
                00112229669999999999996692221100
                00112229669788888888796692221100
                22222229669878888887896692222222
                22222229669887888878896692222222
                22222229669888788788896692222222
                22222229669888877888896692222222
                22222229669888877888896692222222
                22222229669888877888896692222222
                22222229669888788788896692222222
                00112229669887888878896692221100
                00112229669878888887896692221100
                00112229669788888888796692221100
                00112229669999999999996692221100
                00112229666666666666666692221100
                00112229666666666666666692221100
                00112229999999999999999992221100
                00112222222222222222222222221100
                00111222222222222222222222211100
                00011111111122222222111111111000
                20001111111122222222111111110002
                22000000000022222222000000000022
                22200000000022222222000000000222
                """;
        boxImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        boxOnTargetImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        color0 = Color.WHITE;
        color1 = new Color(166, 187, 189);
        color2 = new Color(80, 160, 80);

        color6 = new Color(193, 119, 82);
        color7 = new Color(164, 94, 60);
        color8 = new Color(137, 78, 48);
        color9 = new Color(114, 66, 43);
        int x = 0;
        int y = 0;
        int i = 0;
        while (++i < boxImageCode.length()) {
            char c = boxImageCode.charAt(i);
            if (c == '\n') {
                x = 0;
                y = y + 1;
                continue;
            }
            boxImage.setRGB(x, y, getRGB(c));
            c = boxOnTargetImageCode.charAt(i);
            boxOnTargetImage.setRGB(x, y, getRGB(c));
            x = x + 1;
        }
    }

    private static int getRGB(char c) {
        int rgb = Color.WHITE.getRGB();
        if ('0' <= c && c <= '9') {
            int code = c - '0';
            switch (code) {
                case 1 -> rgb = color1.getRGB();
                case 2 -> rgb = color2.getRGB();
                case 6 -> rgb = color6.getRGB();
                case 7 -> rgb = color7.getRGB();
                case 8 -> rgb = color8.getRGB();
                case 9 -> rgb = color9.getRGB();
                default -> rgb = color0.getRGB();
            }
        }
        return rgb;
    }

    private Box top;
    private Box next;
    private int number;
    private boolean visited;

    private Box() {
        count = count + 1;
        number = count;
    }

    private Box(Vertex vertex) {
        count = count + 1;
        number = count;
        this.vertex = vertex;
    }

    public static Box getTop() {
        return new Box();
    }

    public void draw(Graphics graphics, int x, int y) {
        if (vertex != null) {
            if (vertex.isTarget()) {
                graphics.drawImage(boxOnTargetImage, x, y, null);
            } else {
                graphics.drawImage(boxImage, x, y, null);
            }
        }
    }

    public void ring() {
        System.out.println("RING_BOX");
    }

    public boolean addVertex(Vertex vertex) {
        if (this.vertex == vertex) {
            return false;
        }
        if (this.vertex == null) {
            this.vertex = vertex;
            count = number;
            vertex.setMobile(this);
            return true;
        } else {
            if (next == null) {
                next = new Box();
                next.top = this;
            }
            return next.addVertex(vertex);
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
        super.removeVertex();
        if (next != null && !next.visited) {
            next.resetInternal();
        }
        if (top != null && !top.visited) {
            top.resetInternal();
        }
    }

    private void resetVisited() {
        visited = false;
        if (next != null && next.visited) {
            next.resetVisited();
        }
        if (top != null && top.visited) {
            top.resetVisited();
        }
    }

    public boolean isOnTarget() {
        if (vertex != null && vertex.isTarget() && number <= count) {
            if (next != null) {
                return next.isOnTarget();
            }
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println(this);
        if (next != null) {
            next.print();
        }
    }

    public void print(int number) {
        if (this.number == number) {
            System.out.println(this);
        } else if (next != null) {
            next.print(number);
        }
    }

    public String toString() {
        return "Box [count=" + count + ", number=" + number + ", " + vertex + "]";
    }
}
