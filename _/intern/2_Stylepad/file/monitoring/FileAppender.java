package monitoring;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileAppender {

    public static void main(String[] args) throws Exception {
        if ((args != null) && (args.length != 0)) throw
                new IllegalArgumentException("args is not null and is not empty");

        File file = new File("example.txt");
        int numLines = 1000;
        writeLines(file, numLines);
    }

    private static void writeLines(File file, int numLines) throws Exception {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file), true);
            for (int i = 0; i < numLines; i++) {
                System.out.println("writing line number " + i);
                pw.println("line number " + i);
                Thread.sleep(100);
            }
        } finally {
            if (pw != null) pw.close();
        }
    }

}