package commands;

import file.SaveDocumentModel;
import viewer.Viewer;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.io.File;

public class SaveDocumentCommand implements Command {
    private Viewer viewer;
    private SaveAsDocumentCommand saveAsDocumentCommand;
    private SaveDocumentModel saveDocumentModel;

    public SaveDocumentCommand(Viewer viewer) {
        this.viewer = viewer;
        saveAsDocumentCommand = new SaveAsDocumentCommand(viewer);
        saveDocumentModel = new SaveDocumentModel();
    }

    public void execute() {
        File file = viewer.getFile();
        if (file == null || file.getName().isEmpty()) {
            saveAsDocumentCommand.execute();
        } else {
            DefaultStyledDocument contentDocument = viewer.getDocument();
            if (contentDocument != null) {
                boolean result = saveDocumentModel.saveToFile(file, contentDocument);
                viewer.showResultSaveDocumentIntoModel(result);
            }
        }
    }
}