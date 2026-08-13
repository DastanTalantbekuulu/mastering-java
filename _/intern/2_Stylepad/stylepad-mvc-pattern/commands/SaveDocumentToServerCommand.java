package commands;

import client.Service;
import viewer.Viewer;

import javax.swing.text.Document;

public class SaveDocumentToServerCommand implements Command {
  private Viewer viewer;
  private Service service;

  public SaveDocumentToServerCommand(Viewer viewer) {
    this.viewer = viewer;
    service = new Service();
  }

  public void execute() {
    Document contentDocument = viewer.getDocument();
    if(contentDocument != null) {
        boolean result = service.sendDocument(contentDocument);
        viewer.showResultSaveDocumentIntoModel(result);
    }
  }
}
