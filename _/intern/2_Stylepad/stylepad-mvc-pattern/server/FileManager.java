package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileManager {
    private Properties properties;
    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        loadProperties();
    }

    public void loadProperties() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            System.out.println("Successfull");
        } catch (IOException e) {
            System.err.println(filePath + " not found");
            System.exit(0);
        }
    }

    private String getResourceString(String key) {
        return properties.getProperty(key, null);
    }

    public void setResourceString(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    private void saveProperties() {
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "Updated properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLastFilePath(String filePath) {
        setResourceString("lastFilePath", filePath);
    }

    public String getLastFilePath() {
        return getResourceString("lastFilePath");
    }
}
