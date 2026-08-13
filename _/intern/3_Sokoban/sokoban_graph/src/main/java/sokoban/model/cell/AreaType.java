package sokoban.model.cell;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AreaType implements Type {

    private static AreaType INSTANCE;
    private BufferedImage image;

    public static AreaType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AreaType();
        }
        return INSTANCE;
    }

    private AreaType() {
        String imageCode = """
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                """;
        image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Color color0 = new Color(20, 40, 20);
        Color color1 = new Color(40, 80, 40);
        Color color2 = new Color(80, 160, 80);
        Color color3 = new Color(120, 200, 120);
        Color color4 = new Color(160, 240, 160);
        Color color5 = new Color(170, 215, 81);
        int x = 0;
        int y = 0;
        int i = 0;
        int rgb;
        while (++i < imageCode.length()) {
            char c = imageCode.charAt(i);
            if (c == '\n') {
                x = 0;
                y = y + 1;
                continue;
            }
            if ('0' <= c && c <= '9') {
                int code = c - (int) '0';
                switch (code) {
                    case 0:
                        rgb = color0.getRGB();
                        break;
                    case 1:
                        rgb = color1.getRGB();
                        break;
                    case 2:
                        rgb = color2.getRGB();
                        break;
                    case 3:
                        rgb = color3.getRGB();
                        break;
                    case 4:
                        rgb = color4.getRGB();
                        break;
                    default:
                        rgb = color5.getRGB();
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
        System.out.println("RING_AREA");
    }
}
