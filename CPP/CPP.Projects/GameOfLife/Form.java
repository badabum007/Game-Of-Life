package GameOfLife;

/** Class store all figure, what bot can paint */
public class Form {
  public static int count = 7;
  private int[][] glider1 = {{0, 1}, {1, 0}, {2, 0}, {2, 1}, {2, 2}};

  private int[][] glider2 = {{0, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};

  private int[][] glider3 = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {2, 1}};

  private int[][] glider4 = {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 1}};

  private int[][] square = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

  private int[][] rhombus = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};

  private int[][] bigsquare = {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {1, 0}, {1, 1}, {1, 2}, {1, 3},
      {2, 0}, {2, 1}, {2, 2}, {2, 3}, {3, 0}, {3, 1}, {3, 2}, {3, 3}};

  /**
   * Return figure
   * 
   * @param i - figure number
   * @return int[][] - array cells
   */
  public int[][] getFigure(int i) {
    switch (i) {
      case 0:
        return glider1;
      case 1:
        return glider2;
      case 2:
        return glider3;
      case 3:
        return glider4;
      case 4:
        return square;
      case 5:
        return rhombus;
      case 6:
        return bigsquare;
    }
    return glider1;
  }

  public static String getName(int i) {
    switch (i) {
      case 0:
        return "Glider NORTH-WEST";
      case 1:
        return "Glider SOUTH-WEST";
      case 2:
        return "Glider NORTH-EAST";
      case 3:
        return "Glider SOUTH-EAST";
      case 4:
        return "Square";
      case 5:
        return "Rhombus";
      case 6:
        return "Big square";
    }
    return "";
  }
}
