package com.mastering.sokoban.graph.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class WallType implements Type {
    private static WallType INSTANCE;
    private BufferedImage image;

    public static WallType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WallType();
        }
        return INSTANCE;
    }

    private WallType() {
        String imageCode = """
                55432222235555555555432222223455
                55543333335554334555543333333455
                55554444445554222225555444444555
                55555555555554211125555555555555
                55445555555555222122534554445555
                54422222222555222212543542222255
                54321111122555222222555522111225
                55422222222255222222555521222225
                55432222222255422224555421222245
                55543333333455542245555222222455
                55555544444555554455554222224555
                55555555555555555555542222224555
                45555553455544445555542222345554
                34455554355422222255554224455543
                22222255555521111222555445555455
                22111225555522222122225555543211
                21222222555552222211123455422212
                22222234555552222222222555522122
                22224455534552222222224555542222
                22345555543555422223444555543222
                44555555555555544445555555555444
                55555543335555555555555544455555
                55554222224555555555554222224555
                55542211114555532555554221122555
                55422122224555522555542212212455
                54221222223455522554322122222455
                54221222223455422554221222222455
                54122222222455222554212222222245
                52222222234555222554212222222245
                54222222245542225542222222222245
                55422222345522245554222222222455
                55432222235555555555432222223455
                """;
        image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Color gray1 = new Color(192, 205, 221);
        Color gray2 = new Color(140, 156, 181);
        Color gray3 = new Color(88, 105, 135);
        Color gray4 = new Color(58, 68, 103);
        Color gray5 = new Color(37, 43, 65);
        int x = 0;
        int y = 0;
        int i = 0;
        while (++i < imageCode.length()) {
            char c = imageCode.charAt(i);
            if (c == '\n') {
                x = 0;
                y = y + 1;
                continue;
            }
            if ('0' <= c && c <= '9') {
                int code = c - (int) '0';
                int rgb;
                switch (code) {
                    case 1:
                        rgb = gray1.getRGB();
                        break;
                    case 2:
                        rgb = gray2.getRGB();
                        break;
                    case 3:
                        rgb = gray3.getRGB();
                        break;
                    case 4:
                        rgb = gray4.getRGB();
                        break;
                    default:
                        rgb = gray5.getRGB();
                }
                image.setRGB(x, y, rgb);
                x = x + 1;
            }
        }
    }

    public void draw(Graphics graphics, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    public void ring() {
        System.out.println("RING_WALL");
    }
}
