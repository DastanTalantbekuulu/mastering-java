import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageUtil extends JPanel {
    private static List<String> mario;
    private static String marioString;
    private static BufferedImage image;

    public static void main(String[] args) throws IOException {
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(0, Color.WHITE);
        colors.put(1, Color.GRAY);
        colors.put(2, Color.YELLOW);
        colors.put(3, Color.RED);
        colors.put(4, Color.BLUE);
        colors.put(5, new Color(208, 169, 110));
        colors.put(6, new Color(133, 64, 0));
        colors.put(7, new Color(168, 175, 79));
        colors.put(9, Color.BLACK);

        // marioString = Files.readString(Path.of("mario.txt"));
        marioString = """
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
        image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        int x = 0;
        int y = 0;
        int i = 0;
        while (++i < marioString.length()) {
            char c = marioString.charAt(i);
            if (c == '\n') {
                x = 0;
                y = y + 1;
                continue;
            }
            if ('0' <= c && c <= '9') {
                int k = c - (int) '0';
                int rgb = colors.get(k).getRGB();
                image.setRGB(x, y, rgb);
                x = x + 1;
            }
        }
        // ImageIO.write(image, "jpg", new File("mario.jpg"));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 400);
        frame.add(new ImageUtil());
        frame.setVisible(true);
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawImage(image, ALLBITS, ABORT, getFocusCycleRootAncestor());
    }

    public static Color[][] loadPixelsFromFile(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        Color[][] colors = new Color[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colors[x][y] = new Color(image.getRGB(x, y));
            }
        }
        return colors;
    }

    public static void printMatrix(int[][] matrix) throws IOException {
        Color[][] colors = loadPixelsFromFile(new File("player32.jpg"));
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[x].length; y++) {
                System.out.print(colors[x][y].getRGB() + " ");
            }
            System.out.println();
        }
    }
}