package com.mastering.sokoban.generate.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 0 Color.WHITE
 * 1 Color.GRAY
 * 2 Color.YELLOW
 * 3 Color.RED
 * 4 Color.BLUE
 * 5 new Color(208, 169, 110)
 * 6 new Color(133, 64, 0)
 * 7 new Color(168, 175, 79)
 * 8
 * 9 Color.BLACK
 */
public class Player extends Mobile {

    private final BufferedImage playerImage;
    private final BufferedImage playerOnTargetImage;
    private final Color color0;
    private final Color color1;
    private final Color color2;
    private final Color color3;
    private final Color color4;
    private final Color color5;
    private final Color color6;
    private final Color color7;
    private final Color color8;
    private final Color color9;

    public Player() {
        String playerImageCode = """
                77777777777777777777777777777777
                77777777777779999997777777777777
                77777777779999999999997777777777
                77777777999333333333399977777777
                77777779333333000033333397777777
                77777793333300000000333339777777
                77777933339999999999993333977777
                77777933999999999999999933977777
                77777939999999999999999993977777
                77777939955000555500055993977777
                77777995555099555599055559977777
                77779595555099555599055559597777
                77779599555555555555555599597777
                77777995599555555555599559977777
                77777795599999999999999559777777
                77777779559999999999995597777777
                77777777995555555555559977777777
                77779999939999999999993999997777
                77793333334443333334443333339777
                77939993332223333332223339993977
                77790009442224444442224490009777
                77900000942224444442224900000977
                77900000944444444444444900000977
                77900009444444444444444490000977
                77799994444444444444444449999777
                77777794444449999994444449777777
                77777794444449777794444449777777
                77777794444449777794444449777777
                77777966666669777796666666977777
                77779666666669777796666666697777
                77779999999999777799999999997777
                77777777777777777777777777777777
                """;
        String playerOnTargetImageCode = """
                77700000000077777777000000000777
                77000000000079999997000000000077
                70001111119999999999991111110007
                00011111999333333333399911111000
                00111779333333000033333397711100
                00117793333300000000333339771100
                00117933339999999999993333971100
                00117933999999999999999933971100
                00117939999999999999999993971100
                00117939955000555500055993971100
                00117995555099555599055559971100
                00119595555099555599055559591100
                77779599555555555555555599597777
                77777995599555555555599559977777
                77777795599999999999999559777777
                77777779559999999999995597777777
                77777777995555555555559977777777
                77779999939999999999993999997777
                77793333334443333334443333339777
                00939993332223333332223339993900
                00190009442224444442224490009100
                00900000942224444442224900000900
                00900000944444444444444900000900
                00900009444444444444444490000900
                00199994444444444444444449999100
                00117794444449999994444449771100
                00117794444449777794444449771100
                00111794444449777794444449711100
                00011966666669777796666666911000
                70009666666669777796666666690007
                77009999999999777799999999990077
                77700000000077777777000000000777
                """;
        playerImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        playerOnTargetImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        color0 = Color.WHITE;
        color1 = new Color(166, 187, 189);
        color2 = Color.YELLOW;
        color3 = Color.RED;
        color4 = Color.BLUE;
        color5 = new Color(252, 212, 153);
        color6 = new Color(133, 64, 0);
        color7 = new Color(80, 160, 80);
        color8 = Color.CYAN;
        color9 = Color.BLACK;

        int x = 0;
        int y = 0;
        int i = 0;
        while (++i < playerImageCode.length()) {
            char c = playerImageCode.charAt(i);
            if (c == '\n') {
                x = 0;
                y = y + 1;
                continue;
            }
            playerImage.setRGB(x, y, getRGB(c));
            c = playerOnTargetImageCode.charAt(i);
            playerOnTargetImage.setRGB(x, y, getRGB(c));
            x = x + 1;
        }
    }

    private int getRGB(char c) {
        int rgb = color0.getRGB();
        if ('0' <= c && c <= '9') {
            int code = c - '0';
            switch (code) {
                case 1 -> rgb = color1.getRGB();
                case 2 -> rgb = color2.getRGB();
                case 3 -> rgb = color3.getRGB();
                case 4 -> rgb = color4.getRGB();
                case 5 -> rgb = color5.getRGB();
                case 6 -> rgb = color6.getRGB();
                case 7 -> rgb = color7.getRGB();
                case 9 -> rgb = color9.getRGB();
                default -> rgb = color0.getRGB();
            }
        }
        return rgb;
    }

    public boolean move(Coordinates coordinates) {
        if (super.move(coordinates)) {
            return true;
        }
        Vertex tempVertex = vertex.get(coordinates.getDirection());
        if (tempVertex != null && tempVertex.isMobileBox() && tempVertex.getMobile().move(coordinates)) {
            return super.move(coordinates);
        }
        return false;
    }

    public boolean teleportation(Coordinates coordinates) {
        if (vertex != null) {
            Vertex target = vertex.pathFind(coordinates);
            if (target != null) {
                vertex.removeMobile();
                vertex = target;
                target.setMobile(this);
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics graphics, int x, int y) {
        if (vertex != null) {
            if (vertex.isTarget()) {
                graphics.drawImage(playerOnTargetImage, x, y, null);
            } else {
                graphics.drawImage(playerImage, x, y, null);
            }
        }
    }

    public void ring() {
        System.out.println("RING_PLAYER");
    }

    public void println() {
        System.out.println(this);
    }

    public String toString() {
        return "Player [" + vertex + "]";
    }
}
