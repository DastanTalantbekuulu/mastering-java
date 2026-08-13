package monitoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class LastModifiedWatcher {
    public static void main(String[] args) {
        Path filePath = Paths.get("file/example.txt");
        if (!Files.exists(filePath)) {
            System.err.println("file not found: " + filePath);
            return;
        }
        try {
            FileTime lastModifiedTime = Files.getLastModifiedTime(filePath);
            System.out.println("file: "+filePath);
            System.out.println("last modified time: " + lastModifiedTime);
            while (true) {
                FileTime currentModifiedTime = Files.getLastModifiedTime(filePath);
                if (!currentModifiedTime.equals(lastModifiedTime)) {
                    System.out.println("file modified: " + currentModifiedTime);
                    lastModifiedTime = currentModifiedTime;
                }
                Thread.sleep(1000);
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }catch (InterruptedException ie) {
            System.err.println(ie);
        }
    }
}

