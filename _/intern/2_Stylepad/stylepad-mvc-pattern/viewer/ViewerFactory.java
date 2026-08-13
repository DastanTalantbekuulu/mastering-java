package viewer;

import model.StylepadDocument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

public class ViewerFactory {
    private ResourceBundle resources;

    public ViewerFactory(String baseName) {
        loadResource(baseName);
    }

    public void loadResource(String baseName) {
        try {
            resources = ResourceBundle.getBundle(baseName);
        } catch (MissingResourceException mre) {
            System.err.println(baseName + " not found");
            System.exit(0);
        }
    }

    private String getResourceString(String key) {
        try {
            return resources.getString(key);
        } catch (MissingResourceException mre) {
            return null;
        }
    }

    public JFrame createFrame() {
        JFrame frame = new JFrame(getResourceString("title"));
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon iconForFrame = new ImageIcon(ViewerFactory.class.getResource(getResourceString("icon")));
        frame.setIconImage(iconForFrame.getImage());
        return frame;
    }

    public JPanel createEditorPanel(JTextPane textPane) {
        JScrollPane scrollPane = new JScrollPane(textPane);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);
        return panel;
    }

    public JTextPane createEditor() {
        StyleContext styleContext = new StyleContext();
        StylepadDocument document = new StylepadDocument(styleContext);

        JTextPane textPane = new JTextPane(document);
        textPane.setFont(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0].deriveFont(24f));
        return textPane;
    }

    public JMenuBar createMenuBar(ActionListener actionListener) {
        String menu = getResourceString("menubar");
        JMenuBar menuBar = new JMenuBar();
        for (String menuKey : menu.split(" ")) {
            menuBar.add(createMenu(menuKey, actionListener));
        }
        return menuBar;
    }

    private JMenu createMenu(String key, ActionListener actionListener) {
        String[] itemKeys = getResourceString(key).split(" ");
        String menuString = getResourceString(key + "Label");
        JMenu menu = new JMenu(menuString);
        if (!key.equals("format")) {
            menu.setMnemonic(menuString.charAt(0));
        }
        for (String itemKey : itemKeys) {
            if (itemKey.equals("-")) {
                menu.addSeparator();
            } else if (itemKey.equals("statusBar")) {
                JRadioButton radioButton = createRadioButton(itemKey, actionListener);
                radioButton.setSelected(false);
                menu.add(radioButton);
            } else {
                JMenuItem menuItem = createMenuItem(itemKey, actionListener);
                menu.add(menuItem);
            }
        }
        return menu;
    }

    private JMenuItem createMenuItem(String key, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(getResourceString(key + "Label"));
        if (ViewerFactory.class.getResource(getResourceString(key + "Image")) != null) {
            menuItem.setHorizontalTextPosition(JButton.RIGHT);
            menuItem.setIcon(new ImageIcon(ViewerFactory.class.getResource(getResourceString(key + "Image"))));
        }
        String keyStroke = getResourceString(key + "KeyStroke");
        if (keyStroke != null) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
        }
        String action = getResourceString(key + "Action");
        if (action != null) {
            menuItem.setActionCommand(action);
            menuItem.addActionListener(actionListener);
        }
        return menuItem;
    }

    public JToolBar createToolbar(ActionListener actionListener) {
        String[] toolKeys = getResourceString("toolbar").split(" ");
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        for (String key : toolKeys) {
            if (key.equals("-")) {
                toolbar.addSeparator();
            } else {
                if (ViewerFactory.class.getResource(getResourceString(key + "Image")) != null) {
                    JButton button = new JButton(new ImageIcon(ViewerFactory.class.getResource(getResourceString(key + "Image"))));
                    button.setRequestFocusEnabled(false);
                    button.setMargin(new Insets(1, 1, 1, 1));
                    String action = getResourceString(key + "Action");
                    if (action != null) {
                        button.setActionCommand(action);
                        button.addActionListener(actionListener);
                    }
                    String tip = getResourceString(key + "Tooltip");
                    if (tip != null) {
                        button.setToolTipText(tip);
                    }
                    toolbar.add(button);
                }
            }
        }
        return toolbar;
    }

    public JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBackground(new Color(220, 220, 220));
        statusBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusBar.setPreferredSize(new Dimension(900, 30));
        statusBar.setVisible(false);
        return statusBar;
    }

    private JRadioButton createRadioButton(String key, ActionListener actionListener) {
        JRadioButton radioButton = new JRadioButton(getResourceString(key + "Label"));
        String action = getResourceString(key + "Action");
        radioButton.setSelected(true);
        if (action != null) {
            radioButton.setActionCommand(action);
            radioButton.addActionListener(actionListener);
        }
        return radioButton;
    }
}
