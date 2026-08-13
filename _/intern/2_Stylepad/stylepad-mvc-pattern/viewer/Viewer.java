package viewer;

import controller.Controller;
import listener.MyUndoableEditListener;
import model.StylepadDocument;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Viewer {
    private final Map<String, Object> components;

    public Viewer() {
        Controller controller = new Controller(this);
        components = new HashMap<>();
        components.put("Controller", controller);
        components.put("ViewerFactory", getViewerFactory());
        JFrame frame = getViewerFactory().createFrame();
        frame.setJMenuBar(getMenuBar());
        frame.add(getPanel());
        frame.add(getToolBar(), BorderLayout.PAGE_START);
        frame.setVisible(true);
        getDocument().addUndoableEditListener(new MyUndoableEditListener(getUndoManager()));
        components.put("Frame", frame);
    }

    private ViewerFactory getViewerFactory() {
        if (!components.containsKey("ViewerFactory")) {
            components.put("ViewerFactory", new ViewerFactory("viewer.resources.Viewer"));
        }
        return (ViewerFactory) components.get("ViewerFactory");
    }

    public JFrame getFrame() {
        return (JFrame) components.get("Frame");
    }

    public JTextPane getTextPane() {
        if (!components.containsKey("TextPane")) {
            components.put("TextPane", getViewerFactory().createEditor());
        }
        return (JTextPane) components.get("TextPane");
    }

    public JPanel getPanel() {
        if (!components.containsKey("Panel")) {
            JPanel panel = getViewerFactory().createEditorPanel(getTextPane());
            JPanel statusBarPanel = getStatusBarPanel();
            panel.add(statusBarPanel, BorderLayout.SOUTH);
            components.put("Panel", panel);
        }
        return (JPanel) components.get("Panel");
    }

    public JMenuBar getMenuBar() {
        if (!components.containsKey("MenuBar")) {
            components.put("MenuBar", getViewerFactory().createMenuBar(getActionListener()));
        }
        return (JMenuBar) components.get("MenuBar");
    }

    private Controller getActionListener() {
        return (Controller) components.get("Controller");
    }

    public JToolBar getToolBar() {
        if (!components.containsKey("ToolBar")) {
            components.put("ToolBar", getViewerFactory().createToolbar(getActionListener()));
        }
        return (JToolBar) components.get("ToolBar");
    }

    public JFileChooser getFileChooser() {
        if (!components.containsKey("FileChooser")) {
            components.put("FileChooser", new JFileChooser());
        }
        return (JFileChooser) components.get("FileChooser");
    }

    public StylepadDocument getDocument() {
        StylepadDocument document = (StylepadDocument) ((JTextPane) components.get("TextPane")).getDocument();
        document.setFont(getTextPane().getFont());
        return document;
    }

    public void setDocument(StylepadDocument document) {
        ((JTextPane) components.get("TextPane")).setDocument(document);
    }

    public void update(StylepadDocument document) {
        if (getDocument() != null) {
            getDocument().removeUndoableEditListener(null);
        }
//        moveDocumentListener(document);
        try {
            update(document.getText(0, document.getLength()));
            getTextPane().setFont(document.getFont());
//            document.addUndoableEditListener(null);
            change();
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }

    public void update(String content) {
        if (getDocument() != null) {
            getDocument().removeUndoableEditListener(null);
        }
        getTextPane().setText(content);
    }

//    private void moveDocumentListener(StylepadDocument document) {
//        for (DocumentListener listener : getDocument().getDocumentListeners()) {
//            if (listener instanceof StatusBarListener) {
//                document.addDocumentListener(listener);
//            }
//        }
//    }

    public void change() {
        try {
            getDocument().insertString(0, " ", null);
            getDocument().remove(0, 1);
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }

    public File getFile() {
        return components.containsKey("File") ? (File) components.get("File") : null;
    }

    public void setFile(File file) {
        components.put("File", file);
    }

    public void defaultDocument() {
        getTextPane().setText("");
        setFile(null);
    }

    public String getLastSearchText() {
        return components.containsKey("LastSearchText") ? (String) components.get("LastSearchText") : "";
    }

    public void setLastSearchText(String lastSearchText) {
        components.put("LastSearchText", lastSearchText);
    }

    public int getLastSearchPos() {
        return components.containsKey("LastSearchPos") ? (int) components.get("LastSearchPos") : 0;
    }

    public void setLastSearchPos(int lastSearchPos) {
        components.put("LastSearchPos", lastSearchPos);
    }

    public JPanel getStatusBarPanel() {
        if (!components.containsKey("StatusBarPanel")) {
            components.put("StatusBarPanel", getViewerFactory().createStatusBar());
        }
        return (JPanel) components.get("StatusBarPanel");
    }

    public UndoManager getUndoManager() {
        if (!components.containsKey("UndoManager")) {
            components.put("UndoManager", new UndoManager());
        }
        return (UndoManager) components.get("UndoManager");
    }

    public File showFileDialog(String command) {
        File file = null;
        int resultVal = 0;
        if (command.equals("OpenDocument")) {
            resultVal = getFileChooser().showOpenDialog(null);
        } else if (command.equals("SaveAs")) {
            resultVal = getFileChooser().showSaveDialog(null);
        }

        if (resultVal == JFileChooser.APPROVE_OPTION) {
            file = getFileChooser().getSelectedFile();
        }
        return file;
    }

    public void showNotFoundFile() {
        JOptionPane.showMessageDialog(null,
                "File not found!",
                "About file",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showResultSaveDocumentIntoModel(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(null,
                    "The file was saved successfully.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "The file was not saved.",
                    "Error saving",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showResultPrintDocument() {
        JOptionPane.showMessageDialog(
                null,
                "Document was printed",
                "Print Document from Stylepad",
                JOptionPane.INFORMATION_MESSAGE,
                null);
    }
}
