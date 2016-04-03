package GameOfLifeNotation;

import java.io.Serializable;

import GameOfLife.LifeArea;

/**
 * Serializable class to save the current generation in file
 */
public class LifeAreaInformation implements Serializable {

  private static final long serialVersionUID = 8535496533011692775L;
  private int width;
  private int height;
  private byte field[][];
  private int cellSize;

  public LifeAreaInformation() {
    width = 0;
    height = 0;
    cellSize = 0;
    field = null;
  }

  public LifeAreaInformation(LifeArea area) {
    this.width = area.getWidth();
    this.height = area.getHeight();
    this.cellSize = area.getCellSize();
    this.field = area.getCurrentGeneration();
  }

  public LifeAreaInformation(int width, int height, int size, byte field[][]) {
    this.width = width;
    this.height = height;
    this.cellSize = size;
    this.field = field;
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

  public byte[][] getArea() {
    return field;
  }

}
