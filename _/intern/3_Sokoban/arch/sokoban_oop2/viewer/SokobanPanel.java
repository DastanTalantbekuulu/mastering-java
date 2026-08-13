package viewer;

import model.Grid;

import javax.swing.JPanel;
import java.awt.Graphics;

public class SokobanPanel extends JPanel {
    private Grid grid;

    public SokobanPanel(Grid grid) {
        setLayout(null);
        setBounds(0, 0, 600, 600);
        this.grid = grid;
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int w = grid.getWidth();
        int h = grid.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                graphics.drawImage(grid.getCell(x, y).getImageIcon().getImage(), x * 32, y * 32, null);
            }
        }
    }
}
