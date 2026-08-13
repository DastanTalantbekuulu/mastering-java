package commands;

import viewer.Viewer;
import javax.swing.JOptionPane;

public class NewDocumentCommand implements Command{

    private Viewer viewer;
    private SaveDocumentCommand saveDocumentCommand;

    public NewDocumentCommand(Viewer viewer) {
        this.viewer = viewer;
        saveDocumentCommand = new SaveDocumentCommand(viewer);
    }

    public void execute() {

        boolean hasChanges = viewer.getDocument() != null && viewer.getTextPane().getDocument().getLength() > 0;

        if(hasChanges){
            int choice = JOptionPane.showConfirmDialog(
                    viewer.getFrame(),
                    "Unsaved changes. Do you want to save the file?",
                    "Save file?",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                saveDocumentCommand.execute();
                viewer.defaultDocument();
            }else if(choice == JOptionPane.NO_OPTION){
                viewer.defaultDocument();
            }
        }else {
            viewer.defaultDocument();
        }
    }
}

