package model.cell;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Target extends Area {
    public Target(ImageIcon imageIcon){
        super(imageIcon);
    }
    public char getSymbol() {
        return 'X';
    }
}
