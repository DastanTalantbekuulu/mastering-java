package com.mastering.sokoban.generate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Listener extends KeyAdapter {
    private Model model;

    public Listener(Model model) {
        this.model = model;
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.move(1);
                break;
            case KeyEvent.VK_RIGHT:
                model.move(2);
                break;
            case KeyEvent.VK_DOWN:
                model.move(3);
                break;
            case KeyEvent.VK_LEFT:
                model.move(4);
                break;
        }
    }
}
