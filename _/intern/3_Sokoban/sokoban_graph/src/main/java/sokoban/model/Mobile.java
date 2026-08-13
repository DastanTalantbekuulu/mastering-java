package sokoban.model;

import sokoban.model.board.Coordinates;
import sokoban.model.board.Vertex;
import sokoban.model.cell.Type;

import java.awt.Graphics;

public abstract class Mobile implements Type {
    protected Vertex vertex;

    public abstract void draw(Graphics graphics, int x, int y);

    public abstract void ring();

    public boolean move(Coordinates coordinates) {
        if (vertex != null) {
            Vertex ver = vertex.get(coordinates.getDirection());
            if(ver!=null && ver.isWalkable()){
                vertex.removeMobile();
                vertex = ver;
                return vertex.setMobile(this);
            }
        }
        return false;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        removeVertex();
        this.vertex = vertex;
        vertex.setMobile(this);
    }

    public void removeVertex() {
        if (vertex != null) {
            vertex.removeMobile();
            vertex = null;
        }
    }

    public String toString() {
        return "Mobile [" + getClass() + "]";
    }
}
