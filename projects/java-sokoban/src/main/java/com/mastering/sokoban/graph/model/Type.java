package com.mastering.sokoban.graph.model;

import java.awt.Graphics;

public interface Type {
    void draw(Graphics graphics, int x, int y);

    void ring();
}
