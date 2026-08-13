package stylepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import javax.swing.text.TextAction;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class Notepad extends JPanel {

    protected static Properties properties;
    private static ResourceBundle resources;
    private static final String EXIT_AFTER_PAINT = "-exit";
    private static boolean exitAfterFirstPaint;

    private static final String[] MENUBAR_KEYS = {"file", "edit", "debug"};
    private static final String[] TOOLBAR_KEYS = {"new", "open", "save", "-", "cut", "copy", "paste"};
    private static final String[] FILE_KEYS = {"new", "open", "save", "-", "exit"};
    private static final String[] EDIT_KEYS = {"cut", "copy", "paste", "-", "undo", "redo"};
    private static final String[] DEBUG_KEYS = {"dump", "showElementTree"};

    static {
        try {
            properties = new Properties();
            properties.load(Notepad.class.getResourceAsStream(
                    "resources/NotepadSystem.properties"));
            resources = ResourceBundle.getBundle("stylepad.resources.Notepad",
                    Locale.getDefault());
        } catch (MissingResourceException | IOException e) {
            System.err.println("resources/Notepad.properties "
                    + "or resources/NotepadSystem.properties not found");
            System.exit(1);
        }
    }

    private final JTextComponent editor;
    private final Map<Object, Action> commands;
    private JComponent status;
    private JFrame elementTreeFrame;
    protected ElementTreePanel elementTreePanel;
    protected UndoableEditListener undoHandler = new UndoHandler();
    protected UndoManager undo = new UndoManager();
    public static final String imageSuffix = "Image";
    public static final String labelSuffix = "Label";
    public static final String actionSuffix = "Action";
    public static final String tipSuffix = "Tooltip";
    public static final String openAction = "open";
    public static final String newAction = "new";
    public static final String saveAction = "save";
    public static final String exitAction = "exit";
    public static final String showElementTreeAction = "showElementTree";
    private final UndoAction undoAction = new UndoAction();
    private final RedoAction redoAction = new RedoAction();
    private final Action[] defaultActions = {
            new NewAction(),
            new OpenAction(),
            new SaveAction(),
            new ExitAction(),
            new ShowElementTreeAction(),
            undoAction,
            redoAction
    };

    public void paintChildren(Graphics g) {
        super.paintChildren(g);
        if (exitAfterFirstPaint) {
            System.exit(0);
        }
    }

    public Notepad() {
        super(true);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());
        editor = createEditor();
        editor.getDocument().addUndoableEditListener(undoHandler);
        commands = new HashMap<>();
        Action[] actions = getActions();
        for (Action a : actions) {
            commands.put(a.getValue(Action.NAME), a);
        }
        JScrollPane scroller = new JScrollPane();
        JViewport port = scroller.getViewport();
        port.add(editor);
        String vpFlag = getProperty("ViewportBackingStore");
        if (vpFlag != null) {
            boolean bs = Boolean.parseBoolean(vpFlag);
            port.setScrollMode(bs
                    ? JViewport.BACKINGSTORE_SCROLL_MODE
                    : JViewport.BLIT_SCROLL_MODE);
        }
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("North", createToolbar());
        panel.add("Center", scroller);
        add("Center", panel);
        add("South", createStatusbar());
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals(EXIT_AFTER_PAINT)) {
            exitAfterFirstPaint = true;
        }
        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = new JFrame();
            frame.setTitle(resources.getString("Title"));
            frame.setBackground(Color.lightGray);
            frame.getContentPane().setLayout(new BorderLayout());
            Notepad notepad = new Notepad();
            frame.getContentPane().add("Center", notepad);
            frame.setJMenuBar(notepad.createMenubar());
            frame.addWindowListener(new AppCloser());
            frame.pack();
            frame.setSize(500, 600);
            frame.setVisible(true);
        });
    }

    public Action[] getActions() {
        return TextAction.augmentList(editor.getActions(), defaultActions);
    }

    protected JTextComponent createEditor() {
        JTextComponent c = new JTextArea();
        c.setDragEnabled(true);
        c.setFont(new Font("monospaced", Font.PLAIN, 12));
        return c;
    }

    protected JTextComponent getEditor() {
        return editor;
    }

    protected Frame getFrame() {
        for (Container p = getParent(); p != null; p = p.getParent()) {
            if (p instanceof Frame) {
                return (Frame) p;
            }
        }
        return null;
    }

    protected JMenuItem createMenuItem(String cmd) {
        JMenuItem mi = new JMenuItem(getResourceString(cmd + labelSuffix));
        URL url = getResource(cmd + imageSuffix);
        if (url != null) {
            mi.setHorizontalTextPosition(JButton.RIGHT);
            mi.setIcon(new ImageIcon(url));
        }
        String astr = getProperty(cmd + actionSuffix);
        if (astr == null) {
            astr = cmd;
        }
        mi.setActionCommand(astr);
        Action a = getAction(astr);
        if (a != null) {
            mi.addActionListener(a);
            a.addPropertyChangeListener(createActionChangeListener(mi));
            mi.setEnabled(a.isEnabled());
        } else {
            mi.setEnabled(false);
        }
        return mi;
    }

    protected Action getAction(String cmd) {
        return commands.get(cmd);
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected String getResourceString(String nm) {
        String str;
        try {
            str = resources.getString(nm);
        } catch (MissingResourceException mre) {
            str = null;
        }
        return str;
    }

    protected URL getResource(String key) {
        String name = getResourceString(key);
        if (name != null) {
            return this.getClass().getResource(name);
        }
        return null;
    }

    protected Component createStatusbar() {
        status = new StatusBar();
        return status;
    }

    protected void resetUndoManager() {
        undo.discardAllEdits();
        undoAction.update();
        redoAction.update();
    }

    private Component createToolbar() {
        JToolBar toolbar = new JToolBar();
        for (String toolKey : getToolBarKeys()) {
            if (toolKey.equals("-")) {
                toolbar.add(Box.createHorizontalStrut(5));
            } else {
                toolbar.add(createTool(toolKey));
            }
        }
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }

    protected Component createTool(String key) {
        return createToolbarButton(key);
    }

    protected JButton createToolbarButton(String key) {
        URL url = getResource(key + imageSuffix);
        JButton b = new JButton(new ImageIcon(url)) {

            @Override
            public float getAlignmentY() {
                return 0.5f;
            }
        };
        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1, 1, 1, 1));

        String astr = getProperty(key + actionSuffix);
        if (astr == null) {
            astr = key;
        }
        Action a = getAction(astr);
        if (a != null) {
            b.setActionCommand(astr);
            b.addActionListener(a);
        } else {
            b.setEnabled(false);
        }

        String tip = getResourceString(key + tipSuffix);
        if (tip != null) {
            b.setToolTipText(tip);
        }

        return b;
    }

    protected JMenuBar createMenubar() {
        JMenuBar mb = new JMenuBar();
        for (String menuKey : getMenuBarKeys()) {
            JMenu m = createMenu(menuKey);
            if (m != null) {
                mb.add(m);
            }
        }
        return mb;
    }

    protected JMenu createMenu(String key) {
        JMenu menu = new JMenu(getResourceString(key + labelSuffix));
        for (String itemKey : getItemKeys(key)) {
            if (itemKey.equals("-")) {
                menu.addSeparator();
            } else {
                JMenuItem mi = createMenuItem(itemKey);
                menu.add(mi);
            }
        }
        return menu;
    }

    protected String[] getItemKeys(String key) {
        return switch (key) {
            case "file" -> FILE_KEYS;
            case "edit" -> EDIT_KEYS;
            case "debug" -> DEBUG_KEYS;
            default -> null;
        };
    }

    protected String[] getMenuBarKeys() {
        return MENUBAR_KEYS;
    }

    protected String[] getToolBarKeys() {
        return TOOLBAR_KEYS;
    }

    protected PropertyChangeListener createActionChangeListener(JMenuItem b) {
        return new ActionChangedListener(b);
    }

    static final class AppCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private static class ActionChangedListener implements PropertyChangeListener {
        JMenuItem menuItem;

        ActionChangedListener(JMenuItem mi) {
            super();
            this.menuItem = mi;
        }

        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (e.getPropertyName().equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                menuItem.setText(text);
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                menuItem.setEnabled(enabledState);
            }
        }
    }

    class UndoHandler implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            undo.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }
    }

    static class StatusBar extends JComponent {
        public StatusBar() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        }

        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    class UndoAction extends AbstractAction {

        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                Logger.getLogger(UndoAction.class.getName()).log(Level.SEVERE,
                        "Unable to undo", ex);
            }
            update();
            redoAction.update();
        }

        protected void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {

        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                Logger.getLogger(RedoAction.class.getName()).log(Level.SEVERE,
                        "Unable to redo", ex);
            }
            update();
            undoAction.update();
        }

        protected void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }

    class OpenAction extends NewAction {

        OpenAction() {
            super(openAction);
        }

        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(frame);

            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File f = chooser.getSelectedFile();
            if (f.isFile() && f.canRead()) {
                Document oldDoc = getEditor().getDocument();
                if (oldDoc != null) {
                    oldDoc.removeUndoableEditListener(undoHandler);
                }
                if (elementTreePanel != null) {
                    elementTreePanel.setEditor(null);
                }
                getEditor().setDocument(new PlainDocument());
                frame.setTitle(f.getName());
                Thread loader = new FileLoader(f, editor.getDocument());
                loader.start();
            } else {
                JOptionPane.showMessageDialog(getFrame(),
                        "Could not open file: " + f,
                        "Error opening file",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SaveAction extends AbstractAction {

        SaveAction() {
            super(saveAction);
        }

        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showSaveDialog(frame);

            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File f = chooser.getSelectedFile();
            frame.setTitle(f.getName());
            Thread saver = new FileSaver(f, editor.getDocument());
            saver.start();
        }
    }

    class NewAction extends AbstractAction {
        NewAction() {
            super(newAction);
        }

        NewAction(String nm) {
            super(nm);
        }

        public void actionPerformed(ActionEvent e) {
            Document oldDoc = getEditor().getDocument();
            if (oldDoc != null) {
                oldDoc.removeUndoableEditListener(undoHandler);
            }
            getEditor().setDocument(new PlainDocument());
            getEditor().getDocument().addUndoableEditListener(undoHandler);
            resetUndoManager();
            getFrame().setTitle(resources.getString("Title"));
            revalidate();
        }
    }

    static class ExitAction extends AbstractAction {
        ExitAction() {
            super(exitAction);
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class ShowElementTreeAction extends AbstractAction {
        ShowElementTreeAction() {
            super(showElementTreeAction);
        }

        public void actionPerformed(ActionEvent e) {
            if (elementTreeFrame == null) {
                try {
                    String title = resources.getString("ElementTreeFrameTitle");
                    elementTreeFrame = new JFrame(title);
                } catch (MissingResourceException mre) {
                    elementTreeFrame = new JFrame();
                }

                elementTreeFrame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent weeee) {
                        elementTreeFrame.setVisible(false);
                    }
                });
                Container fContentPane = elementTreeFrame.getContentPane();

                fContentPane.setLayout(new BorderLayout());
                elementTreePanel = new ElementTreePanel(getEditor());
                fContentPane.add(elementTreePanel);
                elementTreeFrame.pack();
            }
            elementTreeFrame.setVisible(true);
        }
    }

    class FileLoader extends Thread {
        private final Document document;
        private final File file;

        public FileLoader(File file, Document document) {
            setPriority(4);
            this.file = file;
            this.document = document;
        }

        public void run() {
            try (Reader in = new FileReader(file)) {
                status.removeAll();
                JProgressBar progress = new JProgressBar();
                progress.setMinimum(0);
                progress.setMaximum((int) file.length());
                status.add(progress);
                status.revalidate();
                char[] buff = new char[4096];
                int nch;
                while ((nch = in.read(buff, 0, buff.length)) != -1) {
                    document.insertString(document.getLength(), new String(buff, 0, nch), null);
                    progress.setValue(progress.getValue() + nch);
                }
            } catch (IOException e) {
                final String msg = e.getMessage();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        getFrame(),
                        "Could not open file: " + msg,
                        "Error opening file",
                        JOptionPane.ERROR_MESSAGE));
            } catch (BadLocationException e) {
                System.err.println(e.getMessage());
            }
            document.addUndoableEditListener(undoHandler);
            status.removeAll();
            status.revalidate();

            resetUndoManager();

            if (elementTreePanel != null) {
                SwingUtilities.invokeLater(() -> elementTreePanel.setEditor(getEditor()));
            }
        }
    }

    class FileSaver extends Thread {
        private final Document document;
        private final File file;

        FileSaver(File file, Document document) {
            setPriority(4);
            this.file = file;
            this.document = document;
        }

        public void run() {
            try (Writer out = new FileWriter(file)) {
                status.removeAll();
                JProgressBar progress = new JProgressBar();
                progress.setMinimum(0);
                progress.setMaximum(document.getLength());
                status.add(progress);
                status.revalidate();

                Segment text = new Segment();
                text.setPartialReturn(true);
                int charsLeft = document.getLength();
                int offset = 0;
                while (charsLeft > 0) {
                    document.getText(offset, Math.min(4096, charsLeft), text);
                    out.write(text.array, text.offset, text.count);
                    charsLeft -= text.count;
                    offset += text.count;
                    progress.setValue(offset);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                out.flush();
            } catch (IOException e) {
                final String msg = e.getMessage();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        getFrame(),
                        "Could not save file: " + msg,
                        "Error saving file",
                        JOptionPane.ERROR_MESSAGE));
            } catch (BadLocationException e) {
                System.err.println(e.getMessage());
            }
            status.removeAll();
            status.revalidate();
        }
    }
}
