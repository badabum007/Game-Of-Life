package GameOfLifeNotation;

public class Figure {
  private int X = 0;
  private int Y = 0;
  private int number = 0;

  public Figure(int X, int Y, int N) {
    this.X = X;
    this.Y = Y;
    this.number = N;
  }

  public void set(int X, int Y, int N) {
    this.X = X;
    this.Y = Y;
    this.number = N;
  }

  public Figure get() {
    return this;
  }

  public int getX() {
    return X;
  }

  public int getY() {
    return Y;
  }

  public int getNumber() {
    return number;
  }

  public void setX(int x) {
    X = x;
  }

  public void setY(int y) {
    Y = y;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
