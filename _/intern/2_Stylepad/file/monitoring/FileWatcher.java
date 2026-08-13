package monitoring;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatcher {

    private Thread thread;
    private WatchService watchService;

    public interface Callback {
        void run() throws Exception;
    }

    public static void onFileChange(Path file, Callback callback) throws IOException {
        FileWatcher fileWatcher = new FileWatcher();
        fileWatcher.start(file, callback);
        Runtime.getRuntime().addShutdownHook(new Thread(fileWatcher::stop));
    }

    public void start(Path file, Callback callback) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        Path parent = file.getParent();
        parent.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

        thread = new Thread(() -> {
            while (true) {
                WatchKey wk = null;
                try {
                    wk = watchService.take();
                    Thread.sleep(500);
                    for (WatchEvent<?> event : wk.pollEvents()) {
                        Path changed = parent.resolve((Path) event.context());
                        if (Files.exists(changed) && Files.isSameFile(changed, file)) {
                            callback.run();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                } finally {
                    if (wk != null) {
                        wk.reset();
                    }
                }
            }
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        try {
            watchService.close();
        } catch (IOException e) {
        }
    }

}
