package monitoring;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileWatcher2 {
    private String fname;
    private Object lck = new Object();

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fname));
            String s;
            StringBuilder buf = new StringBuilder();
            while (true) {
                s = br.readLine();
                if (s == null) {
                    synchronized (lck) {
                        lck.wait(500);
                    }
                } else {
                    System.out.println("s = " + s);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
