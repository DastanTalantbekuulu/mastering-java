public class Main {
    private static String map = """
            4444444444
            4100000004
            4020220204
            4000000004
            4000000004
            4000000004
            4000000004
            4000000004
            4030330304
            4444444444
            """;

    public static void main(String[] args) {
        Type area = new AreaType();
        Type target = new TargetType();
        Type wall = new WallType();
        Player player = new Player();
        Box box = Box.create();

        Node node = Node.create(50, 30);

        Coordinates coordinates = new Coordinates();

        int i = -1;
        int x = 0;
        int y = 1;
        while (++i < map.length()) {
            char c = map.charAt(i);
            x = x + 1;
            if (c == '\n') {
                x = 0;
                y = y + 1;
            } else if (c == '3') {
                coordinates.setCoordinates(x, y);
                node.setType(target, coordinates);
            } else if (c == '4') {
                coordinates.setCoordinates(x, y);
                node.setType(wall, coordinates);
            } else {
                coordinates.setCoordinates(x, y);
                node.setType(area, coordinates);
            }
            if (c == '1') {
                player.setNode(node.get(x, y));
            } else if (c == '2') {
                box.setNode(node.get(x, y));
            }
        }

        // player.getNode().log();
        // node.get(5, 5).log();
        // node.get(8, 9).log();
        // node.get(10, 10).log();
        System.out.println(player);
        // System.out.println(box);
        box.log();
        // box.reset();
        // box.log();

        // node.setType(area, direction);

        // direction.setCoordinates(3, 4);
        // node.setType(wall, direction);

        // direction.setCoordinates(2, 4);
        // node.setType(target, direction);

        // Node cell33 = node.get(3, 3);
        // cell33.log();

        // Node cell34 = cell33.get(3, 4);
        // cell34.log();

        // Node cell24 = cell34.get(2, 4);
        // cell24.log();

    }
}
