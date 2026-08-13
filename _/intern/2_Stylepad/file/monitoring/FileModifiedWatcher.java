package monitoring;

import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

public class FileModifiedWatcher {
    public static void main(String[] args) {

    }
    private static File file;
    private static int pollingInterval;
    private static Timer fileWatcher;
    private static long lastReadTimeStamp = 0L;

    public static boolean init(String _file, int _pollingInterval) {
        file = new File(_file);
        pollingInterval = _pollingInterval;
        watchFile();
        return true;
    }

    private static void watchFile() {
        if (null == fileWatcher) {
            System.out.println("START");
            fileWatcher = new Timer();
            fileWatcher.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (file.lastModified() > lastReadTimeStamp) {
                        System.out.println(file.getPath() + " File Modified");
                    }
                    lastReadTimeStamp = System.currentTimeMillis();
                }
            }, 0, 1000 * pollingInterval);
        }

    }
}
