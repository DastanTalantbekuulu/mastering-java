package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportDocumentModel {

    public String importFile(File file) {

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Stream<String> lines = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            lines = bufferedReader.lines();
            String content = lines.collect(Collectors.joining("\n"));
            return content;

        } catch (IOException ioe) {
            System.out.println("Exception " + ioe);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (lines != null) {
                    lines.close();
                }
            } catch (IOException ioe) {
                System.out.println("Exception: " + ioe);
            }
        }
        return null;
    }
}
