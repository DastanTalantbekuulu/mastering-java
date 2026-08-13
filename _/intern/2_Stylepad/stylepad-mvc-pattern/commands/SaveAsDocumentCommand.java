package commands;

import viewer.Viewer;
import file.SaveDocumentModel;

import java.io.File;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

public class SaveAsDocumentCommand implements Command {
    private Viewer viewer;
    private SaveDocumentModel saveDocumentModel;

    public SaveAsDocumentCommand(Viewer viewer) {
        this.viewer = viewer;
        saveDocumentModel = new SaveDocumentModel();
    }

    public void execute() {
        File file = viewer.showFileDialog("SaveAs");
        if (file != null) {
            DefaultStyledDocument contentDocument = viewer.getDocument();
            if (contentDocument != null) {
                boolean result = saveDocumentModel.saveToFile(file, contentDocument);
                viewer.showResultSaveDocumentIntoModel(result);
            }
        }
    }
}
