import java.util.Map;

public class Level {

    private final Service service;
    private final Map<Integer, String> files;
    private final String[] levels;

    private int level;

    public Level() {
        service = new Service();
        level = 1;
        levels = createLevels();
        files = service.getFileLevelMaps("resources/levels/");
    }

    public int[][] nextLevel() {
        int[][] currentLevel;
        if (level <= levels.length) {
            currentLevel = service.parse(levels[level - 1]);
        } else if (files.containsKey(level)) {
            currentLevel = service.getLevelFromFile(files.get(level));
        } else {
            currentLevel = service.getLevelFromServer(level);
        }
        if (currentLevel == null) {
            currentLevel = service.parse(levels[0]);
            level = 1;
        }
        level = level + 1;
        return currentLevel;
    }

    public void prevLevel() {
        level = 2 < level ? level - 2 : 1;
    }

    public void actualLevel() {
        level = level - 1;
    }

    public int getCurrentLevel() {
        return level - 1;
    }

    private String[] createLevels() {
        return new String[]{
                """
                 2222
                 2002
                 2302
                 2002
                224022
                203042
                212222
                222
                """,
                """
                22222
                20042
                20322
                22002
                 203222
                 200412
                 222222
                """,
                """
                 2222
                 2002
                22032
                20002222
                20300042
                22202222
                  202222
                 2200012
                 2400222
                 22222
                """,
                """
                        222
                        212
                    222 202
                22222422202
                20002004302
                20300002222
                22222002
                    2222
                """,
                """
                 22222222
                 2400004222
                22200210002
                20030240302
                20300222222
                222002
                  2222
                """,
        };
    }
}
