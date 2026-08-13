package commands;

import viewer.Viewer;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class PasteCommand implements Command {
    private Viewer viewer;

    public PasteCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        JTextPane textPane = viewer.getTextPane();
        try {
            textPane.paste();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Paste operation failed.", "Paste Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}