import java.io.FileNotFoundException;
import java.util.Map;

public class Service {
    private final SokobanStream sokobanStream;

    public Service() {
        sokobanStream = new SokobanStream();
    }

    public int[][] getLevelFromServer(int level) {
        long start = System.nanoTime();
        int[][] map = null;
        ClientForBackendServerSokoban clientForBackend = new ClientForBackendServerSokoban("localhost", 4445);
        if (clientForBackend.isConnect()) {
            String response = clientForBackend.readLevelFromServer(level);
            if (response != null) {
                map = sokobanStream.parse(response);
            }
            long end = System.nanoTime();
            System.out.printf("Level %d reading time from a remote server: %,d nanoseconds\n\n", level, end - start);
        }
        return map;
    }

    public int[][] getLevelFromFile(String fileName) {
        int[][] map = null;
        try {
            long start = System.nanoTime();
            map = sokobanStream.read(fileName);
            long end = System.nanoTime();

            System.out.printf("File parsing time \"%s\": %,d nanoseconds\n\n", fileName, end - start);
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
        return map;
    }

    public int[][] parse(String level) {
        return sokobanStream.parse(level);
    }

    public Map<Integer, String> getFileLevelMaps(String dir) {
        return sokobanStream.getFileLevelMaps(dir);
    }
}
