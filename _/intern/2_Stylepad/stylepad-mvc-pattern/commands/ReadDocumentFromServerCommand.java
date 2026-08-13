package commands;

import client.Service;
import model.StylepadDocument;
import viewer.Viewer;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

public class ReadDocumentFromServerCommand implements Command {
  private Viewer viewer;
  private Service service;

  public ReadDocumentFromServerCommand(Viewer viewer) {
    this.viewer = viewer;
    service = new Service();
  }

  public void execute() {
    StylepadDocument contentDocumentFromServer =(StylepadDocument) service.readDocumentFromServer();
    viewer.update(contentDocumentFromServer);
    System.out.println(contentDocumentFromServer);
  }
}
