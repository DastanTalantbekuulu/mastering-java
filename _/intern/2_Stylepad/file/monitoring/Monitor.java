package monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Monitor {
    public static void main(String[] args) {
        URL url = Monitor.class.getResource("example.txt");
        System.out.println(url.getPath());
        File file = new File(url.getPath());
        try {
            monitorFile(file);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private static void monitorFile(File file) throws IOException {
        final int POLL_INTERVAL = 1000;
        FileReader reader = new FileReader(file);
        BufferedReader buffered = new BufferedReader(reader);
        try {
            while (true) {
                String line = buffered.readLine();
                if (line == null) {
                    Thread.sleep(POLL_INTERVAL);
                } else {
                    System.out.println(line);
                }
            }
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
