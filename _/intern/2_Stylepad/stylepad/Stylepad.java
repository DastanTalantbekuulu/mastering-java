package stylepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TextAction;

public class Stylepad extends Notepad {

    private static ResourceBundle resources;
    private FileDialog fileDialog;

    private static final String[] MENUBAR_KEYS = {"file", "edit", "color", "font", "debug"};
    private static final String[] FONT_KEYS = {"family1", "family2", "family3", "family4", "-", "size1", "size2",
            "size3", "size4", "size5", "-", "bold", "italic", "underline"};
    private static final String[] TOOLBAR_KEYS = {"new", "open", "save", "-", "cut", "copy", "paste", "-", "bold",
            "italic", "underline", "-", "left", "center", "right"};

    static {
        try {
            properties.load(Stylepad.class.getResourceAsStream("resources/StylepadSystem.properties"));
            resources = ResourceBundle.getBundle("stylepad.resources.Stylepad");
        } catch (MissingResourceException | IOException mre) {
            System.err.println("Stylepad.properties or StylepadSystem.properties not found");
            System.exit(0);
        }
    }

    public Stylepad() {
        super();
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame();
                frame.setTitle(resources.getString("Title"));
                frame.setBackground(Color.lightGray);
                frame.getContentPane().setLayout(new BorderLayout());
                Stylepad stylepad = new Stylepad();
                frame.getContentPane().add("Center", stylepad);
                frame.setJMenuBar(stylepad.createMenubar());
                frame.addWindowListener(new AppCloser());
                frame.pack();
                frame.setSize(600, 480);
                frame.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(Stylepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Action[] getActions() {
        Action[] defaultActions = {
                new NewAction(),
                new OpenAction(),
                new SaveAction(),
                new StyledEditorKit.FontFamilyAction("font-family-SansSerif", "SansSerif")};
        return TextAction.augmentList(super.getActions(), defaultActions);
    }

    protected String getResourceString(String key) {
        String str;
        try {
            str = Stylepad.resources.getString(key);
        } catch (MissingResourceException mre) {
            str = super.getResourceString(key);
        }
        return str;
    }

    protected JTextComponent createEditor() {
        StyleContext styleContext = new StyleContext();
        DefaultStyledDocument defaultStyledDocument = new DefaultStyledDocument(styleContext);
        initDocument(defaultStyledDocument, styleContext);
        JTextPane textPane = new JTextPane(defaultStyledDocument);
        textPane.setDragEnabled(true);
        return textPane;
    }

    protected JMenu createMenu(String key) {
        if (key.equals("color")) {
            return createColorMenu();
        }
        return super.createMenu(key);
    }

    protected String[] getItemKeys(String key) {
        if (key.equals("font")) {
            return FONT_KEYS;
        }
        return super.getItemKeys(key);
    }

    protected String[] getMenuBarKeys() {
        return MENUBAR_KEYS;
    }

    protected String[] getToolBarKeys() {
        return TOOLBAR_KEYS;
    }

    private JMenu createColorMenu() {
        ActionListener actionListener;
        JMenuItem menuItem;
        JMenu menu = new JMenu(getResourceString("color" + labelSuffix));
        menuItem = new JMenuItem(resources.getString("Red"));
        menuItem.setHorizontalTextPosition(JButton.RIGHT);
        menuItem.setIcon(new ColoredSquare(Color.red));
        actionListener = new StyledEditorKit.ForegroundAction("set-foreground-red", Color.red);
        //a = new ColorAction(se, Color.red);
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);
        menuItem = new JMenuItem(resources.getString("Green"));
        menuItem.setHorizontalTextPosition(JButton.RIGHT);
        menuItem.setIcon(new ColoredSquare(Color.green));
        actionListener = new StyledEditorKit.ForegroundAction("set-foreground-green", Color.green);
        //a = new ColorAction(se, Color.green);
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);
        menuItem = new JMenuItem(resources.getString("Blue"));
        menuItem.setHorizontalTextPosition(JButton.RIGHT);
        menuItem.setIcon(new ColoredSquare(Color.blue));
        actionListener = new StyledEditorKit.ForegroundAction("set-foreground-blue", Color.blue);
        //a = new ColorAction(se, Color.blue);
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);
        return menu;
    }

    private void initDocument(DefaultStyledDocument defaultStyledDocument, StyleContext styleContext) {
        Wonderland wonderland = new Wonderland(defaultStyledDocument, styleContext);
        wonderland.loadDocument();
    }

    private JComboBox<String> createFamilyChoices() {
        JComboBox<String> comboBox = new JComboBox<>();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            comboBox.addItem(fontName);
        }
        return comboBox;
    }

    class OpenAction extends AbstractAction {

        OpenAction() {
            super(openAction);
        }

        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            if (fileDialog == null) {
                fileDialog = new FileDialog(frame);
            }
            fileDialog.setMode(FileDialog.LOAD);
            fileDialog.setVisible(true);

            String file = fileDialog.getFile();
            if (file == null) {
                return;
            }
            String directory = fileDialog.getDirectory();
            File f = new File(directory, file);
            if (f.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(f);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Document document = (Document) objectInputStream.readObject();
                    if (getEditor().getDocument() != null) {
                        getEditor().getDocument().removeUndoableEditListener(undoHandler);
                    }
                    getEditor().setDocument(document);
                    document.addUndoableEditListener(undoHandler);
                    resetUndoManager();
                    frame.setTitle(file);
                    validate();
                } catch (IOException io) {
                    System.err.println("IOException: " + io.getMessage());
                } catch (ClassNotFoundException cnf) {
                    System.err.println("Class not found: " + cnf.getMessage());
                }
            } else {
                System.err.println("No such file: " + f);
            }
        }
    }

    class SaveAction extends AbstractAction {

        SaveAction() {
            super(saveAction);
        }

        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            if (fileDialog == null) {
                fileDialog = new FileDialog(frame);
            }
            fileDialog.setMode(FileDialog.SAVE);
            fileDialog.setVisible(true);
            String file = fileDialog.getFile();
            if (file == null) {
                return;
            }
            String directory = fileDialog.getDirectory();
            File f = new File(directory, file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(f);
                ObjectOutput objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(getEditor().getDocument());
                objectOutputStream.flush();
                frame.setTitle(f.getName());
            } catch (IOException io) {
                System.err.println("IOException: " + io.getMessage());
            }
        }
    }

    class NewAction extends AbstractAction {

        NewAction() {
            super(newAction);
        }

        public void actionPerformed(ActionEvent event) {
            if (getEditor().getDocument() != null) {
                getEditor().getDocument().removeUndoableEditListener(undoHandler);
            }
            getEditor().setDocument(new DefaultStyledDocument());
            getEditor().getDocument().addUndoableEditListener(undoHandler);
            resetUndoManager();
            getFrame().setTitle(resources.getString("Title"));
            validate();
        }
    }

    static class ColoredSquare implements Icon {

        private final Color color;

        public ColoredSquare(Color color) {
            this.color = color;
        }

        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Color oldColor = graphics.getColor();
            graphics.setColor(color);
            graphics.fill3DRect(x, y, getIconWidth(), getIconHeight(), true);
            graphics.setColor(oldColor);
        }

        public int getIconWidth() {
            return 12;
        }

        public int getIconHeight() {
            return 12;
        }
    }
}
