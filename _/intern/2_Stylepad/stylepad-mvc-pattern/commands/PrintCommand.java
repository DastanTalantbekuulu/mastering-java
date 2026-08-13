package commands;

import print.PrintDocument;
import viewer.Viewer;

import java.awt.Font;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintCommand implements Command {
    private Viewer viewer;

    public PrintCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        String content = viewer.getTextPane().getText();
        Font fontForPrint = viewer.getTextPane().getFont();

        PrintDocument printDocument = new PrintDocument(content, fontForPrint);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(printDocument);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
                viewer.showResultPrintDocument();
            } catch (PrinterException pe) {
                System.out.println("PrinterException: " + pe);
            }
        }
    }
}
