package model;

import model.mobile.Box;

import java.util.ArrayList;
import java.util.List;

public class Boxes {
    private CellFactory factory;
    private List<Box> boxList;
    private int top;

    public Boxes(CellFactory factory) {
        this.factory = factory;
        boxList = new ArrayList<>();
        top = -1;
    }

    public List<Box> getBoxList() {
        if (top == -1) {
            return null;
        }
        return boxList.subList(0, top + 1);
    }

    public Box setLocation(int x, int y) {
        if (top < boxList.size()) {
            boxList.add((Box) factory.createCell('B'));
            top++;
        }
        Box box = (Box) boxList.get(top).setLocation(x, y);
//        top++;
        return box;
    }

    public void reset() {
        top = -1;
        for (Box box : boxList) {
            box.reset();
        }
    }

    public boolean isAllOnTarget() {
        for (int i = 0; i <= top; i++) {
            if (!boxList.get(i).isOnTarget()) {
                return false;
            }
        }
        return true;
    }

}
