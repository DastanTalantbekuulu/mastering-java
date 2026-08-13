package listener;

import commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandActionListener implements ActionListener {
    private final Command command;

    public CommandActionListener(Command command) {
        this.command = command;
    }
    public void actionPerformed(ActionEvent e) {
        command.execute();
    }
}