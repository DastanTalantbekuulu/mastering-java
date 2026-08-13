package monitoring;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchService1 {
    public static void main(String[] args) {
        URL url = WatchService1.class.getResource("example.txt");
        System.out.println(url.getPath());
        File file = new File(url.getPath());
        url.getFile();

//        Path path;

        System.out.println(file.getParentFile());

//        if (!file.exists()) {
//            file = file.getParentFile();
//        }

//        file.getPath();
//        path = file.toPath();

        String dir = "";
        if (file.isDirectory()) {
            dir = file.getAbsolutePath();
        } else {
            dir = file.getAbsolutePath().replaceAll(file.getName(), "");
        }
        Path path = FileSystems.getDefault().getPath(dir);

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    Path changed = (Path) event.context();
                    System.out.println(changed);
                    if (changed.endsWith("example.txt")) {
                        System.out.println("My file has changed");
                    }
                }
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregisterede");
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
