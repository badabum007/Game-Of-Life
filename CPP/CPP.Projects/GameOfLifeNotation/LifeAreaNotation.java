package GameOfLifeNotation;

import java.util.ArrayDeque;

/** Class-notaton for Game Of Life */
public class LifeAreaNotation {
  ArrayDeque<Dot> dotList = new ArrayDeque<Dot>();
  
  /** Adds the coordinates in the array 
   * @param dot coordinates if alive cell
   * */
  public void addDot(Dot dot) {
    dotList.addLast(dot);
  }

  /** Pop the coordinates from array 
   * @return dot coordinates if alive cell
   * */
  public Dot popDot() {
    return dotList.pop();
  }

  /** Return array of dots
   * @return dotList.toArray() array of dots
   * */
  public Object[] getAll() {
    return dotList.toArray();
  }

  /** Clean array */
  public void clear() {
    dotList.clear();
  }

  /** Check empty array */
  public boolean isEmpty() {
    return dotList.isEmpty();
  }

}
