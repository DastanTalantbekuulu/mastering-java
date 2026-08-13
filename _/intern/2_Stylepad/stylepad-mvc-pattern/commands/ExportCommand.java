package commands;

import file.ExportDocumentModel;
import viewer.Viewer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.File;

public class ExportCommand implements Command {

    private Viewer viewer;
    private ExportDocumentModel exportDocumentModel;
    private File file;

    public ExportCommand(Viewer viewer) {
        this.viewer = viewer;
        exportDocumentModel = new ExportDocumentModel();
    }

    public void execute() {
        if (file == null) {
            file = viewer.showFileDialog("SaveAs");
        }
        if (file != null) {
            Document contentDocument = viewer.getDocument();
            if (contentDocument != null) {
                try {
                boolean result = exportDocumentModel.saveAsToFile(file,
                        contentDocument.getText(0, contentDocument.getLength()));
                viewer.showResultSaveDocumentIntoModel(result);
            System.out.println("SaveAs");
                viewer.setFile(file);
                } catch (BadLocationException ble){
                    System.out.println(ble);
                }
            }
        }
    }
}
