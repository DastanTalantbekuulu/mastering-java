package monitoring;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatchDemo {

    public static void main(String[] args) {
        try {
            URL url = WatchService1.class.getResource("example.txt");
            System.out.println(url.getPath());
            File file = new File(url.getPath());
            System.out.println(file.getParentFile());
            String str;
            if (file.isDirectory()) {
                str = file.getAbsolutePath();
            } else {
                str = file.getAbsolutePath().replaceAll(file.getName(), "");
            }
            Path path = FileSystems.getDefault().getPath(str);

            WatchService watcher = FileSystems.getDefault().newWatchService();
            path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Watch Service registered for dir: " + path.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    System.out.println(kind.name() + ": " + fileName);

                    if (kind == ENTRY_MODIFY && fileName.toString().equals("example.txt")) {
                        System.out.println("My source file has changed!!!");
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}