package monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileMonitor {

    public static void main(String[] args) throws Exception {
        if ((args != null) && (args.length != 0)) throw
                new IllegalArgumentException("args is not null and is not empty");

        File file = new File("example.txt");
        readLines(file);
    }

    private static void readLines(File file) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    System.out.println("no file data available; sleeping..");
                    Thread.sleep(2 * 1000);
                } else {
                    System.out.println(line);
                }
            }
        } finally {
            if (br != null) br.close();
        }
    }

}
