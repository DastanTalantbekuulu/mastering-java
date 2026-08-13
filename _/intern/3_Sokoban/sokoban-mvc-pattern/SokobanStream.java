import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class SokobanStream {

    private InputStream inputStream;
    private final int bufferSize;
    private final byte[] bufferByte;
    private final int[][] bufferMap;
    private final int u0;
    private final int u9;
    private int maxY;
    private int maxX;

    public SokobanStream() {
        maxY = maxX = 64;
        bufferSize = maxY * maxX;
        bufferByte = new byte[bufferSize];
        bufferMap = new int[maxY][maxX];
        u0 = (int) '0';
        u9 = (int) '9';
        resetMap(maxY, maxX);
    }

    public int[][] read(String fileName) throws FileNotFoundException {
        inputStream = SokobanStream.class.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException(fileName + " not found");
        }
        return read(inputStream);
    }

    public int[][] read(InputStream inputStream) {
        try {
            int[][] map = null;
            int read;
            if ((read = inputStream.read(bufferByte, 0, bufferSize)) != -1) {
                map = parse(bufferByte, read);
            }
            return map;
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            close();
        }
        return null;
    }

    public int[][] parse(String input) {
        return parse(input.getBytes(), input.length());
    }

    private int[][] parse(byte[] bufferByte, int read) {
        resetMap(maxY, maxX);
        maxY = maxX = 0;
        int x = 0, y = 0;
        for (int i = 0; i < read; i++) {
            char c = (char) bufferByte[i];
            if (c == '\r') {
                continue;
            }
            if (c == '\n') {
                y = y + 1;
                x = 0;
                maxY = Math.max(maxY, y);
            } else if (c == ' ') {
                bufferMap[y][x] = -1;
                x = x + 1;
                maxX = Math.max(maxX, x);
            } else if (u0 <= c && c <= u9) {
                bufferMap[y][x] = (c - u0);
                x = x + 1;
                maxX = Math.max(maxX, x);
                if (read - i == 1) {
                    maxY = maxY + 1;
                }
            }
        }
        return mapping(bufferMap, maxY, maxX);
    }

    public int[][] mapping(int[][] desk, int y, int x) {
        int[][] map = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = desk[i][j];
            }
        }
        return map;
    }

    private void resetMap(int maxY, int maxX) {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                bufferMap[y][x] = -1;
            }
        }
    }

    public void close() {
        try {
            if (inputStream != null) {
                InputStream in = inputStream;
                inputStream = null;
                in.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public Map<Integer, String> getFileLevelMaps(String dir) {
        Map<Integer, String> result = new HashMap<>();
        FileSystem fileSystem = null;
        Stream<Path> walk = null;
        try {
            URI uri = Level.class.getResource(dir).toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(dir);
            } else {
                myPath = Paths.get(uri);
            }
            walk = Files.walk(myPath, 1);
            Iterator<Path> iterator = walk.iterator();
            while (iterator.hasNext()) {
                Path path = iterator.next();
                String name = path.getFileName().toString();
                if (name.endsWith(".txt")) {
                    int i = 5;
                    char c = name.charAt(i);
                    int number = 0;
                    while (++i < name.length() && u0 <= c && c <= u9) {
                        number = number * 10 + (c - u0);
                        c = name.charAt(i);
                    }
                    if (0 < number) {
                        result.put(number, dir + name);
                    }
                }
            }
        } catch (URISyntaxException use) {
            System.out.println(use);
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            try {
                if (fileSystem != null) {
                    fileSystem.close();
                }
                if (walk != null) {
                    walk.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
        return result;
    }
}
