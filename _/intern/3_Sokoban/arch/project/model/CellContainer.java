package model;

import model.cell.Area;
import model.cell.Target;
import model.cell.Wall;
import model.mobile.Box;
import model.mobile.Player;

import javax.swing.ImageIcon;
import java.util.List;

public class CellContainer {
    private Player player;
    private Wall wall;
    private Area area;
    private Target target;
    private List<Box> boxes;
    private int boxIndex;
}
