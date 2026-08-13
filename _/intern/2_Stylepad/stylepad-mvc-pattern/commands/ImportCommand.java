package commands;

import file.ImportDocumentModel;
import viewer.Viewer;

import java.io.File;

public class ImportCommand implements Command {

    private Viewer viewer;
    private ImportDocumentModel importDocumentModel;

    public ImportCommand(Viewer viewer) {
        this.viewer = viewer;
        importDocumentModel = new ImportDocumentModel();
    }

    public void execute() {
        File file = viewer.showFileDialog("OpenDocument");
        if (file != null) {
            String contents = importDocumentModel.importFile(file);
            viewer.update(contents);
            viewer.setFile(file);
        } else {
            viewer.showNotFoundFile();
        }
    }
}
