package commands;

import file.ExportDocumentModel;
import file.SynchronizeTextModel;
import listener.StylepadDocumentListener;
import viewer.Viewer;
import viewer.ViewerFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import java.awt.Component;
import java.io.File;

public class SynchronizeCommand implements Command {
    private Viewer viewer;
    private JButton button;
    private File file;
    private boolean flag;
    private ImageIcon imageEnable;
    private ImageIcon imageDisable;
    private JTextPane textPane;
    private StylepadDocumentListener stylepadDocumentListener;
    private ExportDocumentModel exportDocumentModel;

    public SynchronizeCommand(Viewer viewer) {
        this.viewer = viewer;
        imageDisable = new ImageIcon(ViewerFactory.class.getResource("images/toolbar/syncDisable.png"));
        imageEnable = new ImageIcon(ViewerFactory.class.getResource("images/toolbar/syncEnable.png"));
        exportDocumentModel = new ExportDocumentModel();
    }

    public void execute() {
        if (button == null) {
            setButton();
        }

        if (setFile()) {
            flag = !flag;
            button.setIcon(flag ? imageEnable : imageDisable);
            if (textPane == null) {
                textPane = viewer.getTextPane();
                stylepadDocumentListener = new StylepadDocumentListener(new SynchronizeTextModel(file));
                textPane.getDocument().addDocumentListener(stylepadDocumentListener);
            }
            stylepadDocumentListener.setFlag(flag);
            if (!flag) {
                viewer.setFile(null);
            }
        }
    }

    private void setButton() {
        if (button == null) {
            Component[] components = viewer.getToolBar().getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton but = (JButton) component;
                    if (but.getActionCommand().equals("Synchronize")) {
                        button = but;
                        return;
                    }
                }
            }
        }
    }

    private boolean setFile() {
        if (file == null && viewer.getFile() != null) {
            file = viewer.getFile();
            return true;
        }

        if (viewer.getFile() == null && doClickSaveButton()) {
            if (viewer.getFile() != null) {
                file = viewer.getFile();
                return true;
            }
            return false;
        }

        if (file != viewer.getFile()) {
            file = viewer.getFile();
            stylepadDocumentListener.setFile(file);
            return true;
        }
        return file != null;
    }

    private boolean doClickSaveButton() {
        Component[] components = viewer.getMenuBar().getComponents();
        for (Component component : components) {
            if (component instanceof JMenu ) {
                JMenu menu = (JMenu) component;
                if(menu.getText().equals("File")) {
                    Component[] menuComponents = menu.getMenuComponents();
                    for (Component menuComponent : menuComponents) {
                        if (menuComponent instanceof JMenuItem) {
                            JMenuItem item = (JMenuItem) menuComponent;
                            if (item.getText().equals("Export")) {
                                item.doClick();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
