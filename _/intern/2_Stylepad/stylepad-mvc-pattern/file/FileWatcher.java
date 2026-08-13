package file;

import model.StylepadDocument;
import viewer.Viewer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FileWatcher extends Thread {

    private Viewer viewer;
    private Path filePath;
    private File file;
    private FileTime lastModifiedTime;
    private AtomicInteger interval;
    private AtomicBoolean isDocument;
    private OpenDocumentModel openDocumentModel;
    private AtomicBoolean running;
    private AtomicBoolean paused;

    public FileWatcher(Viewer viewer) {
        this.viewer = viewer;
        openDocumentModel = new OpenDocumentModel();
        running = new AtomicBoolean(true);
        paused = new AtomicBoolean(false);
        isDocument = new AtomicBoolean(false);
        interval = new AtomicInteger(1000);
        start();
    }

    public void run() {
        try {
            while (running.get()) {
                if (paused.get()) {
                    update();
                }
                sleep(interval.get());
            }
        } catch (InterruptedException ie) {
            System.err.println(ie);
        }
    }

    public void watch(File file, int interval) {
        try {

            this.file = file;
            this.interval.set(interval);
            filePath = Paths.get(file.getAbsolutePath());

            if (!Files.exists(filePath)) {
                System.err.println("file not found: " + filePath);
                return;
            }

            lastModifiedTime = Files.getLastModifiedTime(filePath);
            System.out.println("file: " + filePath);
            System.out.println("last modified time: " + lastModifiedTime);

            StylepadDocument document = (StylepadDocument) openDocumentModel.openFile(file);
            System.out.println(document);
            if (document == null) {
                isDocument.set(false);
                viewer.update(Files.readString(filePath));
            } else {
                isDocument.set(true);
                viewer.update((StylepadDocument) openDocumentModel.openFile(file));
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void update() {
        try {
            FileTime currentModifiedTime = Files.getLastModifiedTime(filePath);
            if (!currentModifiedTime.equals(lastModifiedTime)) {
                System.out.println("file modified: " + currentModifiedTime);
                lastModifiedTime = currentModifiedTime;
                if (isDocument.get()) {
                    viewer.update((StylepadDocument) openDocumentModel.openFile(file));
                } else {
                    viewer.update(Files.readString(filePath));
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }

    public void setPaused(boolean paused) {
        this.paused.set(paused);
    }
}
