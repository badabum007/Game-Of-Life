package GameInformation;
/** Class save information about bot's games */
public class GameInfo {
  private String name = null;
  private int figureCount = 0;
  private int[] everyFigureCount = null;
  
  public GameInfo() {
    this.name = null;
    this.figureCount = 0;
    this.everyFigureCount = null;
  }
  
  public GameInfo(String name, int figureCount) {
    this.name = name;
    this.figureCount = figureCount;
  }
  
  public GameInfo(String name, int figureCount, int[] everyFigureCount) {
    this.name = name;
    this.figureCount = figureCount;
    this.everyFigureCount = everyFigureCount;
  }
  
  public String getName() {
    return name;
  }
  public int getFigureCount() {
    return figureCount;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setFigureCount(int figureCount) {
    this.figureCount = figureCount;
  }

  public int[] getEveryFigureCount() {
    return everyFigureCount;
  }

  public void setEveryFigureCount(int[] everyFigureCount) {
    this.everyFigureCount = everyFigureCount;
  }
  
  
}
