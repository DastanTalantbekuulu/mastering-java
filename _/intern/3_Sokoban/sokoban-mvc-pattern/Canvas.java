import javax.swing.JPanel;
import java.awt.Color;
import java.io.Serializable;

public class Canvas extends JPanel implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Model model;
    public Canvas(){}
    public Canvas(Model model) {
        this.model = model;
        setBackground(Color.BLACK);
    }


    public Canvas(Model model, Color c) {
        this.model = model;
        this.setBackground(c);


    }

    public void initButtonCoordinates() {
    }


}
