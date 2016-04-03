package GameOfLifeNotation;

public class Dot {
  int X = 0;
  int Y = 0;

  public Dot(int X, int Y) {
    this.X = X;
    this.Y = Y;
  }

  public void set(int X, int Y) {
    this.X = X;
    this.Y = Y;
  }

  public Dot get() {
    return this;
  }

  public int getX() {
    return X;
  }

  public int getY() {
    return Y;
  }

  public void setX(int x) {
    X = x;
  }

  public void setY(int y) {
    Y = y;
  }
}
