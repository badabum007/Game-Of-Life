package GameOfLife;

import GameOfLifeNotation.LifeAreaInformation;

/**
 * Class describes a mathematical model of the game and containing the data and methods for creating
 * the matrix, saving matrix size, cell size and status (alive/dead) and methods to generate the
 * next generation
 */
public class LifeArea {
  /** Information about current generation of life */
  private byte[][] currentGeneration = null;

  /** Information about next generation of life */
  private byte[][] nextGeneration = null;

  /** Width of the playing field */
  private int width;

  /** Height of the playing field */
  private int height;
  private int cellSize;

  /** Coordinates of all neighboring cells */
  private byte[][] neighbourCell = null;

  /**
   * Creates an object of class LifeArea
   * 
   * @param width width of the playing field
   * @param height height of the playing field
   * @see LifeArea#LifeArea(int, int)
   */
  public LifeArea(int width, int height) {
    this.width = width;
    this.height = height;
    this.cellSize = 10;
    currentGeneration = new byte[this.width][this.height];
    nextGeneration = new byte[this.width][this.height];

    neighbourCell =
        new byte[][] {{0, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}, {-1, 1}, {-1, -1}, {1, 1}};
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getCellSize() {
    return cellSize;
  }

  public void setCellSize(int size) {
    cellSize = size;
  }

  /**
   * Clearing the playing field (state of all cells is dead)
   * 
   * @see LifeArea#clear()
   */
  public void clear() {
    for (short i = 0; i < width; i++)
      for (short j = 0; j < height; j++) {
        currentGeneration[i][j] = 0;
      }
  }

  /**
   * Field filled randomly
   * 
   * @see LifeArea#randomField()
   */
  public void randomField() {
    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++) {
        currentGeneration[i][j] = (int) (Math.random() * 2) == 0 ? (byte) 1 : (byte) 0;
      }
  }

  /**
   * Setting new field parameters
   * 
   * @param width width width of the playing field
   * @param height height height of the playing field
   * @see LifeArea#setField(int, int)
   */
  public void setField(int width, int height) {
    this.width = width;
    this.height = height;

    currentGeneration = new byte[this.width][this.height];
    nextGeneration = new byte[this.width][this.height];
  }

  /**
   * Setting new field parameters
   * 
   * @param area LifeAreaInformation
   * @see LifeArea#setField(LifeAreaInformation)
   */
  public void setField(LifeAreaInformation area) {
    this.width = area.getWidth();
    this.height = area.getHeight();
    cellSize = area.getCellSize();
    currentGeneration = new byte[this.width][this.height];
    nextGeneration = new byte[this.width][this.height];
    this.currentGeneration = area.getArea();
  }

  public void setCell(int x, int y, byte cellState) {
    currentGeneration[x][y] = cellState;
  }

  public byte getCell(int x, int y) {
    return currentGeneration[x][y];
  }

  public byte[][] getCurrentGeneration() {
    return currentGeneration;
  }

  /**
   * Generation of the next generation by the rules of Games of Life
   * 
   * @see LifeArea#createNextGeneration()
   */
  public void createNextGeneration() {
    for (short i = 0; i < width; i++)
      for (short j = 0; j < height; j++) {
        byte count = countOfNeighbour(i, j);
        nextGeneration[i][j] = newCellState(currentGeneration[i][j], count);
      }

    byte temp[][] = currentGeneration;
    currentGeneration = nextGeneration;
    nextGeneration = temp;
  }

  /**
   * Emulation of the torus
   * 
   * @param x
   * @see LifeArea#X(short)
   */
  private short X(short x) {
    if (x == -1) {
      return (short) (width - 1);
    }
    if (x == width) {
      return (short) 0;
    }
    return x;
  }

  /**
   * emulation of the torus
   * 
   * @param y
   * @see LifeArea#Y(short)
   **/
  private short Y(short y) {
    if (y == -1) {
      return (short) (height - 1);
    }
    if (y == height) {
      return (short) 0;
    }
    return y;
  }

  /**
   * Counting living neighboring cells
   * 
   * @param x coordinate cells on the Ox axis
   * @param y coordinate cells on the Oy axis
   * @see LifeArea#countOfNeighbour(int, int)
   * @return count count of neighbors
   */
  private byte countOfNeighbour(int x, int y) {
    byte count = 0;
    short posX, posY;
    for (int i = 0; i < 8; i++) {
      posX = X((short) (x + neighbourCell[i][0]));
      posY = Y((short) (y + neighbourCell[i][1]));
      count += currentGeneration[posX][posY];
    }
    return count;
  }

  /**
   * Setting new cell state by rule the Game of Life
   * 
   * @param cellSelf
   * @param neighbourCount
   * @return
   */
  private byte newCellState(byte cellSelf, byte neighbourCount) {
    if (cellSelf == 0) {
      if (neighbourCount == 3) {
        return (byte) 1;
      } else {
        return (byte) 0;
      }
    } else {
      if (neighbourCount == 2 || neighbourCount == 3) {
        return (byte) 1;
      } else {
        return (byte) 0;
      }
    }
  }
}
