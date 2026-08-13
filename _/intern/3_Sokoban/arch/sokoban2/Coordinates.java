public class Coordinates {
    private int tx;
    private int ty;

    public Coordinates() {
        tx = 0;
        ty = 0;
    }

    public Coordinates(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public boolean onTarget(Node node) {
        return tx == node.getX() && ty == node.getY();
    }
//    public int getDirection(int x, int y) {
//        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
//            return -1;
//            // OnTarget
//        } else if (tx == x && ty == y) {
//            return 0;
//        } else if ((tx == x && ty < y) || (tx > x && ty < y) || (tx < x || ty < y)) {
//            // toUp Вверх
//            return 1;
//        } else if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
//            // toRight Вправо
//            return 2;
//        } else if ((tx == x && ty > y) || (tx > x && ty > y) || (tx < x && ty > y)) {
//            // toDown Вниз
//            return 3;
//        } else if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
//            // toLeft Влево
//            return 4;
//        } else {
//            return -1;
//        }
//    }
//    public int getDirection(int x, int y) {
//        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
//            return -1;
//            // OnTarget
//        } else if (tx == x && ty == y) {
//            return 0;
//        } else if ((tx == x && ty < y) || (tx > x && ty < y) || (tx < x && ty < y)) {
////        } else if ((tx > x && ty < y)) {
//            // toUp Вверх
//            return 1;
//        } else if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
////        } else if ( (tx > x && ty > y)) {
//            // toRight Вправо
//            return 2;
//        } else if ((tx == x && ty > y) || (tx > x && ty > y) || (tx < x && ty > y)) {
////        } else if (  (tx < x && ty > y)) {
//            // toDown Вниз
//            return 3;
//        } else if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
////        } else if ((tx < x && ty < y)) {
//            // toLeft Влево
//            return 4;
//        } else {
//            return -1;
//        }
//    }

    private int getDirection1(int x, int y) {
        if ((tx == x && ty < y) || (tx > x && ty < y) || (tx < x || ty < y)) {
            // toUp Вверх
            return 1;
        }
        if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
            // toRight Вправо
            return 2;
        }
        if ((tx == x && ty > y) || (tx > x && ty > y) || (tx < x && ty > y)) {
            // toDown Вниз
            return 3;
        }
        if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
            // toLeft Влево
            return 4;
        }
        return -1;
    }

    private int getDirection2(int x, int y) {
        if ((tx == x && ty < y) || (tx > x && ty < y) || (tx < x || ty < y)) {
            // toUp Вверх
            return 1;
        }
        if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
            // toRight Вправо
            return 2;
        }
        if ((tx == x && ty > y) || (tx > x && ty > y) || (tx < x && ty > y)) {
            // toDown Вниз
            return 3;
        }
        if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
            // toLeft Влево
            return 4;
        }
        return -1;
    }
//    public int getDirection(int x, int y) {
//        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
//            return -1;
//        }
//        // OnTarget
//        if (tx == x && ty == y) {
//            return 0;
//        }
//        if ((tx == x && ty < y) || (tx > x && ty < y) || (tx < x || ty < y)) {
//            // toUp Вверх
//            return 1;
//        }
//        if ((tx > x && ty == y) || (tx > x && ty > y) || (tx > x && ty < y)) {
//            // toRight Вправо
//            return 2;
//        }
//        if ((tx == x && ty > y) || (tx > x && ty > y) || (tx < x && ty > y)) {
//            // toDown Вниз
//            return 3;
//        }
//        if ((tx < x && ty == y) || (tx < x && ty > y) || (tx < x && ty < y)) {
//            // toLeft Влево
//            return 4;
//        }
//        return -1;
//    }

    public int getDirection(int x, int y) {
        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
            return -1;
        }
        // OnTarget
        if (tx == x && ty == y) {
            return 0;
        } else if (ty < y) {
            // toUp Вверх
            return 1;
        } else if (tx > x) {
            // toRight Вправо
            return 2;
        } else if (ty > y) {
            // toDown Вниз
            return 3;
        } else {
            // toLeft Влево
            return 4;
        }
    }

    public int getDirection() {
        // OnTarget
        if (tx == 0 && ty == -1) {
            // toUp Вверх
            return 1;
        } else if (tx == 1 && ty == 0) {
            // toRight Вправо
            return 2;
        } else if (tx == 0 && ty == 1) {
            // toDown Вниз
            return 3;
        } else if (tx == -1 && ty == 0) {
            // toLeft Влево
            return 4;
        } else {
            return -1;
        }
    }

    public int getDirection(Node node) {

        int x = node.getX();
        int y = node.getY();
        int d;

        if (tx < 0 || ty < 0 || x < 0 || y < 0) {
            return -1; // Неверные координаты
        }

        // На месте
        if (tx == x && ty == y) {
            return 0;
        }

        // Диагональные перемещения
        if (tx > x && ty > y) d = 5; // Вниз-вправо
        if (tx > x && ty < y) d = 6; // Вверх-вправо
        if (tx < x && ty > y) d = 7; // Вниз-влево
        if (tx < x && ty < y) d = 8; // Вверх-влево

        if (ty < y) return 1;  // Вверх
        if (tx > x) return 2;  // Вправо
        if (ty > y) return 3;  // Вниз
        if (tx < x) return 4;  // Влево

        return -1;
    }

    public void setDirection(int direction) {
        switch (direction) {
            case 1:
                tx = 0;
                ty = -1;
                break;
            case 2:
                tx = 1;
                ty = 0;
                break;
            case 3:
                tx = 0;
                ty = 1;
                break;
            case 4:
                tx = -1;
                ty = 0;
                break;
            default:
                tx = 0;
                ty = 0;
        }
    }

    public Coordinates set(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        return this;
    }

    public int getX() {
        return tx;
    }

    public int getY() {
        return ty;
    }

    public void println() {
        System.out.println(this);
    }

    public String toString() {
        return "Coordinates [x=" + tx + ", y=" + ty + "]";
    }
}
