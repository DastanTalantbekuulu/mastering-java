package commands;

import viewer.Viewer;
import javax.swing.text.Document;

public class ClearCommand implements Command {
    private Viewer viewer;

    public ClearCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        Document contentDocument = viewer.getDocument();
        viewer.getTextPane().setText("");
    }
}
