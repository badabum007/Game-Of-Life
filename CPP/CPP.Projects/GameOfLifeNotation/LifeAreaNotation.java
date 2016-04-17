package GameOfLifeNotation;

import java.util.ArrayDeque;

/** Class-notaton for Game Of Life */
public class LifeAreaNotation {
  ArrayDeque<Figure> figureList = new ArrayDeque<Figure>();
  
  /** Adds the coordinates in the array 
   * @param dot coordinates if alive cell
   * */
  public void addFigure(Figure dot) {
    figureList.addLast(dot);
  }

  /** Pop the coordinates from array 
   * @return dot coordinates if alive cell
   * */
  public Figure popFigure() {
    return figureList.pop();
  }

  /** Return array of dots
   * @return dotList.toArray() array of dots
   * */
  public Object[] getAll() {
    return figureList.toArray();
  }

  /** Clean array */
  public void clear() {
    figureList.clear();
  }

  /** Check empty array 
   * @return bool is DitList empty
   * */
  public boolean isEmpty() {
    return figureList.isEmpty();
  }

}
