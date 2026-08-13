package commands;

import viewer.Viewer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutCommand implements Command {
    private Viewer viewer;
    private JDialog dialog;

    public AboutCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        if (dialog == null) {
            dialog = createDialog();
        }
        dialog.setVisible(true);
    }

    private JDialog createDialog() {
        JDialog dialog = new JDialog(viewer.getFrame());
        AboutDialogAction action = new AboutDialogAction();
        dialog.setBounds(100, 100, 500, 550);
        dialog.addWindowListener(action);
        dialog.setTitle("About");
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(viewer.getFrame());
        ImageIcon imageIcon = new ImageIcon("images/duke.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(250, 200, Image.SCALE_FAST));
        JLabel labelIcon = new JLabel(imageIcon);
        labelIcon.setBounds(125, 50, 250, 200);
        JTextArea textArea = createTextArea();
        JButton button = new JButton("OK");
        button.addActionListener(action);
        button.setBounds(200, 450, 100, 30);
        dialog.add(labelIcon);
        dialog.add(textArea);
        dialog.add(button);
        return dialog;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setText(getAboutText());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBounds(50, 270, 400, 150);
        textArea.setFont(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0].deriveFont(14f));
        return textArea;
    }

    private String getAboutText() {
        return "    Stylepad training program is created by Mediator design pattern group, " +
                "participants of InternLabs 6.0 internship program. " +
                "\n    The program implements the MVC pattern. \n    Program developers " +
                "Aisuluu Sharipova, Alydin Alykulov, Argen Azanov, Bektur Mavlyanov, " +
                "Dastan Makkambayev, Dastan Sazanov, Tilek Tashtanbekov.";
    }

    private class AboutDialogAction extends WindowAdapter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            dialog.setVisible(false);
        }

        public void windowClosing(WindowEvent event) {
            dialog.setVisible(false);
        }
    }
}
