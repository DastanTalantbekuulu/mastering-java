package controller;

import commands.*;
import viewer.Viewer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

public class Controller implements ActionListener {
    private Map<String, Command> commandMap;

    public Controller(Viewer viewer) {
        commandMap = new HashMap<>();
        commandMap.put("NewDocument", new NewDocumentCommand(viewer));
        commandMap.put("SaveDocument", new SaveDocumentCommand(viewer));
        commandMap.put("SaveAsDocument", new SaveAsDocumentCommand(viewer));
        commandMap.put("Export", new ExportCommand(viewer));
        commandMap.put("Import", new ImportCommand(viewer));
        commandMap.put("OpenDocument", new OpenDocumentCommand(viewer));
        commandMap.put("SaveToServer", new SaveDocumentToServerCommand(viewer));
        commandMap.put("ReadFromServer", new ReadDocumentFromServerCommand(viewer));
        commandMap.put("Print", new PrintCommand(viewer));
        commandMap.put("TimeAndDate", new TimeAndDateCommand(viewer));
        commandMap.put("Undo", new UndoCommand(viewer));
        commandMap.put("Redo", new RedoCommand(viewer));
        commandMap.put("InsertImage", new InsertImageCommand(viewer));
        commandMap.put("FindPrevious", new FindPreviousCommand(viewer));
        commandMap.put("Find", new FindCommand(viewer));
        commandMap.put("FindNext", new FindNextCommand(viewer));
        commandMap.put("SelectAll", new SelectAllCommand(viewer));
        commandMap.put("About", new AboutCommand(viewer));
        commandMap.put("Cut", new CutCommand(viewer));
        commandMap.put("Copy", new CopyCommand(viewer));
        commandMap.put("Paste", new PasteCommand(viewer));
        commandMap.put("Go", new GoToCommand(viewer));
        commandMap.put("Exit", new ExitCommand(viewer));
        commandMap.put("Font", new FontCommand(viewer));
        commandMap.put("Synchronize", new SynchronizeFileCommand(viewer));
        commandMap.put("StatusBar", new StatusBarCommand(viewer));
        commandMap.put("ClearHighlight", new ClearHighlightCommand(viewer));
        commandMap.put("Clear", new ClearCommand(viewer));
    }

    public void actionPerformed(ActionEvent event) {
        if (commandMap.containsKey(event.getActionCommand())) {
            commandMap.get(event.getActionCommand()).execute();
        } else {
            System.out.println("Unknown command: " + event.getActionCommand());
        }
    }
}
