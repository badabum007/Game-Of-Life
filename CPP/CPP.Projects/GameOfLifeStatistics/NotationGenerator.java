package GameOfLifeStatistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NotationGenerator {
  private final static int WIDTH = 90;
  private final static int HEIGHT = 60;
  private final static int FORM_COUNT = 7;

  static public void GenerateSaves(int count) {
    int posX = 0;
    int posY = 0;
    int formNumber = 0;
    String oneMove = null;
    int movesCount = 0;
    FileWriter fileWriter = null;
    try {
      for (int i = 1; i <= count; i++) {
        movesCount = (int) (Math.random() * 90) + 10;
        File newSave = new File("C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot\\gamesave"
            + Integer.toString(i) + ".not");
        fileWriter = new FileWriter(newSave);
        fileWriter.write("100/70\n");
        while (movesCount-- != 0) {
          posX = (int) (Math.random() * WIDTH);
          posY = (int) (Math.random() * HEIGHT);
          formNumber = (int) (Math.random() * FORM_COUNT);
          oneMove = Integer.toString(posX) + "/" + Integer.toString(posY) + "/"
              + Integer.toString(formNumber) + "\n";
          fileWriter.write(oneMove);
        }
        fileWriter.write("");
        fileWriter.close();
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
