package sokoban.model.cell;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TargetType implements Type {
    private static TargetType INSTANCE;
    private BufferedImage image;

    public static TargetType getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TargetType();
        }
        return INSTANCE;
    }

    private TargetType() {
        String imageCode = """
                22200000000022222222000000000222
                22000000000022222222000000000022
                20001111111122222222111111110002
                00011111111122222222111111111000
                00111222222222222222222222211100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                22222222222222222222222222222222
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00112222222222222222222222221100
                00111222222222222222222222211100
                00011111111122222222111111111000
                20001111111122222222111111110002
                22000000000022222222000000000022
                22200000000022222222000000000222
                """;
        image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Color color1 = new Color(166, 187, 189);
        Color color2 = new Color(80, 160, 80);
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
                        rgb = Color.WHITE.getRGB();
                        break;
                    case 1:
                        rgb = color1.getRGB();
                        break;
                    default:
                        rgb = color2.getRGB();
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
        System.out.println("RING_TARGET");
    }
}
