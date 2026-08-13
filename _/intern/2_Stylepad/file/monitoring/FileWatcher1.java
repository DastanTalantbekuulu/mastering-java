package monitoring;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class MyFileWatcher extends FileWatcher1 {
    public MyFileWatcher(String watchFile) {
        super(watchFile);
    }

    public void onModified() {
        System.out.println("Modified!");
    }

    public static void main(String[] args) throws Exception {
        String watchFile = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Test.txt";
        MyFileWatcher watcher = new MyFileWatcher(watchFile);
        watcher.watchFile();
    }
}

public abstract class FileWatcher1 {
    private Path folderPath;
    private  String watchFile;

    public FileWatcher1(String watchFile) {
        Path filePath = Paths.get(watchFile);
        boolean isRegularFile = Files.isRegularFile(filePath);
        if (!isRegularFile) {
            throw new IllegalArgumentException(watchFile + " is not a regular file");
        }
        folderPath = filePath.getParent();
        System.out.println(filePath.getFileName());
        this.watchFile = watchFile.replace(folderPath.toString() + File.separator, "");
    }

    public void watchFile() throws Exception {
        FileSystem fileSystem = folderPath.getFileSystem();
        try (WatchService service = fileSystem.newWatchService()) {
            folderPath.register(service, ENTRY_MODIFY);
            while (true) {
                WatchKey watchKey = service.take();
                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = watchEvent.kind();
                    if (kind == ENTRY_MODIFY) {
                        System.out.println("mod");
                        Path watchEventPath = (Path) watchEvent.context();
                        if (watchEventPath.toString().equals(watchFile)) {
                            onModified();
                        }
                    }
                }

                if (!watchKey.reset()) {
                    break;
                }
            }
        }
    }

    public abstract void onModified();
}

