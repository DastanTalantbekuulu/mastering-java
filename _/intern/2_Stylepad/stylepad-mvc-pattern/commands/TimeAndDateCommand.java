package commands;

import viewer.Viewer;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAndDateCommand implements Command {
    private Viewer viewer;

    public TimeAndDateCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatter.format(new Date());
        JTextPane textPane = viewer.getTextPane();
        try {
            textPane.getDocument().insertString(textPane.getDocument().getLength(), dateTime, null);
        } catch (BadLocationException ble) {
            System.out.println("BadLocationException: " + ble);
        }
    }

}