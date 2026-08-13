package commands;

import viewer.Viewer;
import file.SaveDocumentModel;
import javax.swing.JOptionPane;
import java.io.File;

public class ExitCommand implements Command {
    private Viewer viewer;
    private SaveDocumentCommand saveDocumentCommand;

    public ExitCommand(Viewer viewer) {
        this.viewer = viewer;
        this.saveDocumentCommand = new SaveDocumentCommand(viewer);
    }

    public void execute() {
        if (hasChanges()) {
            int choice = JOptionPane.showConfirmDialog(
                    viewer.getFrame(),
                    "Unsaved changes. Do you want to save the file?",
                    "Save file?",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                saveDocumentCommand.execute();
            } else if (choice == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private boolean hasChanges() {
        return viewer.getDocument() != null && viewer.getTextPane().getDocument().getLength() > 0;
    }
}