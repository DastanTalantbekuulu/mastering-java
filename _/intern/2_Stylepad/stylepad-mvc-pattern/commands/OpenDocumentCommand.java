package commands;

import model.StylepadDocument;
import viewer.Viewer;
import file.OpenDocumentModel;

import java.io.File;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

public class OpenDocumentCommand implements Command {
    private Viewer viewer;
    private OpenDocumentModel openDocumentModel;

    public OpenDocumentCommand(Viewer viewer) {
        this.viewer = viewer;
        openDocumentModel = new OpenDocumentModel();
    }

    public void execute() {
        File file = viewer.showFileDialog("OpenDocument");
        if (file != null) {
            StylepadDocument dataFromFile = (StylepadDocument) openDocumentModel.openFile(file);
            if (dataFromFile != null) {
                viewer.update(dataFromFile);
                viewer.setFile(file);
            }
        } else {
            viewer.showNotFoundFile();
        }
    }
}
