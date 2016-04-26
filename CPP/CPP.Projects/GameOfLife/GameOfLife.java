package GameOfLife;

import javax.swing.SwingUtilities;



/**
 * Game launcher
 * @author Zhartun Matthew
 * @version 1.1
 */
public class GameOfLife {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new LifeWindow("Matthew's Game of Life");
      }
    });
  }
}
