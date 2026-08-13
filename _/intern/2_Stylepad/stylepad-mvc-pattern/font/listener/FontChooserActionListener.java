package font.listener;

import font.dialog.FontChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FontChooserActionListener extends WindowAdapter implements ActionListener{
    private FontChooser fontChooser;

    public FontChooserActionListener(FontChooser fontChooser) {
        this.fontChooser = fontChooser;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("OK")) {
            fontChooser.set();
        } else if (event.getActionCommand().equals("Cancel")) {
            fontChooser.cancel();
        }
        fontChooser.setVisible(false);
    }

    public void windowClosing(WindowEvent event) {
        fontChooser.cancel();
        fontChooser.setVisible(false);
    }
}
