package commands;

import viewer.Viewer;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class CopyCommand implements Command {
    private Viewer viewer;

    public CopyCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        JTextPane textPane = viewer.getTextPane();
        if (textPane.getSelectedText() != null) {
            textPane.copy();
        } else {
            JOptionPane.showMessageDialog(null,
                    "No text selected to copy.", "Copy Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}