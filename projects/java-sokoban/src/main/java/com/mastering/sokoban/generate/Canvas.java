package com.mastering.sokoban.generate;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    private Model model;

    public Canvas(Model model) {
        this.model = model;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        model.draw(graphics);
    }
}
