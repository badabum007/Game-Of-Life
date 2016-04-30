package GameOfLifeStatistics;

import java.util.Random;

import GameInformation.GameInfo;

/** QSort on Java */
public class JavaSort {
  static Random rand = new Random();

  public void qSort(GameInfo[] array, int begin, int end) {
    int i = begin;
    int j = end;
    int x = array[begin + rand.nextInt(end - begin + 1)].getFigureCount();
    while (i <= j) {
      while (array[i].getFigureCount() > x) {
        i++;
      }
      while (array[j].getFigureCount() < x) {
        j--;
      }
      if (i <= j) {
        GameInfo temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
      }
    }
    if (begin < j) {
      qSort(array, begin, j);
    }
    if (i < end) {
      qSort(array, i, end);
    }
  }
}
