// package graph;

// import grid.Type;

// public class Vertex {
//     private Type type;
//     private Vertex right;
//     private Vertex bottom;
//     private int x;
//     private int y;

//     public Vertex(Point point, boolean init) {
//         x = point.getLocationX();
//         y = point.getLocationY();
//         System.out.print("(" + x + ", " + y + "), ");
//         if(init) {
//             init(point);
//         }
//     }

//     private void init(Point point) {
//         if (point.getLocationX() <= x) {
//             Point p = new Point(1, point.getY());
//             if (bottom == null) {
//                 bottom = new Vertex(p, false);
//             }
//             bottom.addEdge(p);
//         }
//         if (point.moveToRight()) {
//             if (right == null) {
//                 right = new Vertex(point, true);
//             }
//             right.init(point);
//         }
//     }

//     public void addEdge(Point point) {
//         if (point.isTarget()) {
//             return;
//         } else if (point.moveToRight()) {
//             if (right == null) {
//                 right = new Vertex(point, false);
//                 right.addEdge(point);
//             } else {
//                 right.addEdge(point);
//             }
//         } else if (point.moveToBottom()) {
//             if (bottom == null) {
//                 bottom = new Vertex(point,false);
//                 bottom.addEdge(point);
//             } else {
//                 bottom.addEdge(point);
//             }
//         }
//     }

//     public void setType(Type type, Point point) {
//         if (point.isTarget()) {
//             this.type = type;
//         } else if (point.moveToRight()) {
//             if (right == null) {
//                 right = new Vertex(point,false);
//                 right.setType(type, point);
//             } else {
//                 right.setType(type, point);
//             }
//         } else if (point.moveToBottom()) {
//             if (bottom == null) {
//                 bottom = new Vertex(point,false);
//                 bottom.setType(type, point);
//             } else {
//                 bottom.setType(type, point);
//             }
//         }
//     }

//     // public void addEdge(Vertex vertex, int x, int y) {
//     // if (right == null && x > 1) {
//     // right = new Vertex();
//     // right.addEdge(vertex, x - 1, y);
//     // } else if (right != null && x > 1) {
//     // right.addEdge(vertex, x - 1, y);
//     // }

//     // if (bottom == null && x == 1 && y > 2) {
//     // bottom = new Vertex();
//     // bottom.addEdge(vertex, x, y - 1);
//     // } else if (bottom != null && x == 1 && y > 2) {
//     // bottom.addEdge(vertex, x, y - 1);
//     // }

//     // if (bottom == null && x == 1 && y == 2) {
//     // bottom = vertex;
//     // }
//     // }

//     // public void setType(VertexType type, int x, int y) {
//     // if (x == 1 && y == 1) {
//     // this.type = type;
//     // }

//     // if (x > 1) {
//     // if (right == null) {
//     // right = new Vertex();
//     // }
//     // right.setType(type, x - 1, y);
//     // }

//     // if (x == 1 && y > 1) {
//     // if (bottom == null) {
//     // bottom = new Vertex();
//     // }
//     // bottom.setType(type, x, y - 1);
//     // }
//     // }

// }
