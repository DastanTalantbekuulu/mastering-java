package sokoban.level;

import sokoban.model.board.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 0 area
 * 1 player
 * 2 player on target
 * 3 box
 * 4 box on target
 * 5 target
 * 9 wall
 */

public class Level {
    private int level;
    private String map;

    public Level() {
        level = 0;
    }

    public String nextLevel() {
        level = level + 1;
        return get(level);
    }

    public String get(int level) {
        map = readFile(level);
        if (!isValidMap(map)) {
        }
        return map;
    }

    private String readFile(int level) {
        try {
            Path path = Path.of("levels/level" + level + ".txt");
            map = Files.readString(path);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return map;
    }

    private boolean isValidMap(String input) {
        int target = 0;
        int boxes = 0;
        int player = 0;
        int i = -1;
        while (++i < input.length()) {
            char c = input.charAt(i);
            if (c == Grid.BOX || c == Grid.BOX_ON_TARGET) {
                boxes = boxes + 1;
            }
            if (c == Grid.PLAYER || c == Grid.PLAYER_ON_TARGET) {
                player = player + 1;
            }
            if (c == Grid.TARGET || c == Grid.BOX_ON_TARGET || c == Grid.PLAYER_ON_TARGET) {
                target = target + 1;
            }
        }
        if (player != 1 || boxes != target) {
            return false;
        }
        return true;
    }
    public Map<Integer, String> getMapLevels() {
        Map<Integer, String> mapLevels = new HashMap<>();
        try {
            Stream<Path> stream = Files.list(Paths.get("levels"));
            mapLevels = stream.filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(file -> file.startsWith("level"))
                    .collect(Collectors.toMap(file -> Integer.parseInt(file.substring(5, 6)), file -> file));
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return mapLevels;
    }
}
