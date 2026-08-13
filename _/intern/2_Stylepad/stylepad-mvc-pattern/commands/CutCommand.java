package commands;

import viewer.Viewer;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class CutCommand implements Command {
    private Viewer viewer;

    public CutCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        JTextPane textPane = viewer.getTextPane();
        if (textPane.getSelectedText() != null) {
            textPane.cut();
        } else {
            JOptionPane.showMessageDialog(null,
                    "No text selected to cut.", "Cut Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}