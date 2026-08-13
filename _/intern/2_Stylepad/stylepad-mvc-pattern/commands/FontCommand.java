package commands;

import font.dialog.FontChooser;
import viewer.Viewer;

public class FontCommand implements Command {
    private Viewer viewer;
    private FontChooser fontChooser;

    public FontCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    public void execute() {
        if(fontChooser == null) {
            fontChooser = FontChooser.getInstance();
            fontChooser.setComponent(viewer);
        }
        fontChooser.setVisible(true);
    }
}
