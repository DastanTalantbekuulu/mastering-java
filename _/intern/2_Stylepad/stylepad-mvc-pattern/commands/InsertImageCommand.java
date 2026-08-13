package commands;

import viewer.Viewer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.io.File;

public class InsertImageCommand implements Command {
  private Viewer viewer;

  public InsertImageCommand(Viewer viewer) {
    this.viewer = viewer;
  }

  public void execute() {
    File file = viewer.showFileDialog("OpenDocument");
    if (file != null) {
      StyleContext styleContext = new StyleContext();
      Style style = styleContext.addStyle(null, null);
      Icon icon = new ImageIcon(file.getAbsolutePath());
      StyleConstants.setIcon(style, icon);
      Document document = viewer.getDocument();
      try {
        document.insertString(viewer.getTextPane().getCaretPosition(), "image://" + file.getAbsolutePath(), style);
      } catch (BadLocationException ble) {
        System.out.println("ble: " + ble);
      }
    }
  }
}
