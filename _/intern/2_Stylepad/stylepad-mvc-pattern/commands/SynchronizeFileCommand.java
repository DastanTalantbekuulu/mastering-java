package commands;

import file.FileWatcher;
import file.SaveDocumentModel;
import viewer.Viewer;
import viewer.ViewerFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SynchronizeFileCommand implements Command {
    private FileWatcher fileWatcher;
    private Viewer viewer;
    private File file;
    private JButton button;
    private ImageIcon imageEnable;
    private ImageIcon imageDisable;
    private boolean flag;
    private SaveDocumentModel saveDocumentModel;

    public SynchronizeFileCommand(Viewer viewer) {
        this.viewer = viewer;
        imageDisable = new ImageIcon(ViewerFactory.class.getResource("images/toolbar/syncDisable.png"));
        imageEnable = new ImageIcon(ViewerFactory.class.getResource("images/toolbar/syncEnable.png"));
        fileWatcher = new FileWatcher(viewer);
    }

    public void execute() {
        if (button == null) {
            setButton();
        }
        if (setFile()) {
            flag = !flag;
            button.setIcon(flag ? imageEnable : imageDisable);
            if (flag) {
                fileWatcher.watch(file, 1000);
            } else {
                try {
                    fileWatcher.wait();
                } catch (InterruptedException ie) {
                    System.out.println(ie);
                }
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
        if (viewer.getFile() != null) {
            file = viewer.getFile();
            return true;
        }
        if (viewer.getFile() == null) {
            return saveFile();
        }
        if (file != viewer.getFile()) {
            file = viewer.getFile();
            return true;
        }
        return file != null;
    }

    private boolean saveFile() {
        if (saveDocumentModel == null) {
            saveDocumentModel = new SaveDocumentModel();
        }
        File file = new File(getFileName());
        viewer.setFile(file);
        this.file = file;
        saveDocumentModel.saveToFile(file, viewer.getDocument());
        return false;
    }

    private String getFileName() {
        return "SPMVCP_" + DateTimeFormatter.ofPattern("yyyyddMM HHmmss").format(LocalDateTime.now()) + ".spd";
    }
}
