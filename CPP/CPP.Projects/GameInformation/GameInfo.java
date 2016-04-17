package GameInformation;

public class GameInfo {
  private String name = null;
  private int figureCount = 0;
  
  public GameInfo(String name, int figureCount) {
    this.name = name;
    this.figureCount = figureCount;
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
  
  
}
