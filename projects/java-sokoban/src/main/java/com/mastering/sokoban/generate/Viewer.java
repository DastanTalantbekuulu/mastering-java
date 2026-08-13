package com.mastering.sokoban.generate;

import javax.swing.JFrame;

public class Viewer {
    private Canvas canvas;
    private Model model;

    public Viewer() {
        model = new Model(this);
        canvas = new Canvas(model);

        Listener listener = new Listener(model);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 0, 900, 900);
        frame.add(canvas);
        frame.addKeyListener(listener);
        frame.setVisible(true);
    }

    public void update() {
        canvas.repaint();
    }
}
