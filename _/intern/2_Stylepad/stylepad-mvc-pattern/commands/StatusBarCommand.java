package commands;

import listener.StatusBarListener;
import viewer.Viewer;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.DocumentListener;
import java.awt.Component;

public class StatusBarCommand implements Command {
    private Viewer viewer;
    private JRadioButton radioButtonStatusBar;
    private boolean flag;
    private JPanel statusBarPanel;
    private StatusBarListener statusBarListener;

    public StatusBarCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        if (radioButtonStatusBar == null) {
            radioButtonStatusBar = getRadioButtonStatusBar();
        }
        if (statusBarPanel == null) {
            statusBarPanel = viewer.getStatusBarPanel();
        }
        if (statusBarListener == null) {
            JLabel label = new JLabel();
            statusBarPanel.add(label);
            statusBarPanel.revalidate();
            statusBarListener = new StatusBarListener(label, viewer);
            viewer.getTextPane().getDocument().addDocumentListener(statusBarListener);
        }
        checkDocumentListener();
        flag = !flag;
        radioButtonStatusBar.setSelected(flag);
        statusBarPanel.setVisible(flag);
        statusBarListener.setFlag(flag);
    }

    private void checkDocumentListener() {
        StatusBarListener listenerFromDocument = statusBarListener;
        for (DocumentListener listener : viewer.getDocument().getDocumentListeners()) {
            if (listener instanceof StatusBarListener) {
                return;
            }
        }
        viewer.getDocument().addDocumentListener(listenerFromDocument);
    }

    private JRadioButton getRadioButtonStatusBar() {
        Component[] components = viewer.getMenuBar().getComponents();
        for (Component component : components) {
            if (component instanceof JMenu) {
                JMenu menu = (JMenu) component;
                for (Component menuComponent : menu.getMenuComponents()) {
                    if (menuComponent instanceof JRadioButton) {
                        JRadioButton statusButton = (JRadioButton) menuComponent;
                        if (statusButton.getText().equals("Status Bar")) {
                            return statusButton;
                        }
                    }
                }
            }
        }
        return null;
    }
}
