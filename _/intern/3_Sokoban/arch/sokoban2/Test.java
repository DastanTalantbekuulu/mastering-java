public class Test {
    //    0 - area
//    1 - player
//    2 - player on target
//    3 - box
//    4 - box on target
//    9 - wall
    private static String map = """
            999999999999999999999999999999
            900000000000001000000000000009
            900300300300300003003003003009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900500500500500005005005005009
            900000000000000000000000000009
            999999999999999999999999999999
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            900000000000000000000000000009
            999999999999999999999999999999
            """;

    public static void main(String[] args) {
        Type target = TargetType.getInstance();
        Type wall = WallType.getInstance();
        Player player = new Player();
        Box box = Box.getTop();

        Node node = Node.create(30, 30);

        Coordinates coordinates = new Coordinates();

        long start = System.currentTimeMillis();

        int i = -1;
        int x = 0;
        int y = 1;
        while (++i < map.length()) {
            char c = map.charAt(i);
            x = x + 1;
            if (c == '0') {
                continue;
            }
            if (c == '\n') {
                x = 0;
                y = y + 1;
            } else if (c == '5' || c == '4' || c == '2') {
                node.setType(target, coordinates.set(x, y));
            } else if (c == '9') {
                node.setType(wall, coordinates.set(x, y));
            }
            if (c == '1' || c == '2') {
                player.setNode(node.get(coordinates.set(x, y)));
            } else if (c == '3' || c == '4') {
                box.addNode(node.get(coordinates.set(x, y)));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(start + " " + end + " " + (end - start));

//======================================================================================================================
//        coordinates.set(3, 3);
//        coordinates.println();
//        node.setType(wall, coordinates);

//        coordinates.set(3, 4);
//        coordinates.println();
//        node.setType(wall, coordinates);

//        coordinates.set(2,4);
//        coordinates.println();
//        node.setType(target, coordinates);

//        coordinates.set(3, 3);
//        coordinates.println();
//        Node cell33 = node.get(coordinates);
//        cell33.println();

        coordinates.set(19, 19);
        coordinates.println();
        Node cell2020 = node.get(coordinates);
//        cell2020.println();
        coordinates.set(30, 30);
        Node node3030 = node.get(coordinates);
        node3030.println();
        print(node);
        Node node30301 = cell2020.get(coordinates);
        coordinates.println();
        cell2020.println();
        print(node);
        node30301.println();
//        cell2020.getTop().println();
//        cell2020.getRight().println();
//        cell2020.getBottom().println();
//        cell2020.getLeft().println();
//======================================================================================================================
//        for (int j = 1; j < 31; j++) {
////            System.out.println(node.get(coordinates.setCoordinates(j, 15)).getType());
//            System.out.println(node.get(coordinates.setCoordinates(j, 3)).getMobile());
//        }
//        node.resetType();
//        for (int j = 1; j < 31; j++) {
////            System.out.println(node.get(coordinates.setCoordinates(j, 15)).getType());
//            System.out.println(node.get(coordinates.setCoordinates(j, 3)).getMobile());
//        }
//======================================================================================================================
//        box.println();
//
//        ((Box) node.get(coordinates.set(27, 3)).getMobile()).println();
//
//        player.println();
//        coordinates.set(10, 10);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();
//
//        coordinates.set(27, 3);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();
//
//        coordinates.set(27, 4);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();
//
//        coordinates.set(25, 25);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();
//
//        coordinates.set(12, 5);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();
//
//        coordinates.set(-50, -1);
//        coordinates.println();
//        player.teleportation(coordinates);
//        player.println();

//======================================================================================================================
//        coordinates.setDirection(1);
//        coordinates.println();
//        player.move(coordinates);
//        player.println();
//        player.move(coordinates);
//        player.println();
//        player.move(coordinates);
//        player.println();
//        player.move(coordinates);
//        player.println();
//
//        coordinates.setDirection(3);
//        coordinates.println();
//        player.move(coordinates);
//        player.println();
//
//        box.println(4);
//
//        coordinates.setDirection(2);
//        coordinates.println();
//        player.move(coordinates);
//        player.println();
//
//        box.println(4);

//======================================================================================================================
//        box.println();
//        box.reset();
//        box.println();
//        box.setNode(node);
//        System.out.println(Box.count);
//        box.setNode(node);
//        System.out.println(Box.count);
//        box.println();
//======================================================================================================================
    }

    private static void print(Node node) {
        for (int i = 1; i <= node.getWidth(); i++) {
            for (int j = 1; j <= node.getHeight(); j++) {
                node.print(i, j);
            }
        }
    }
}
