package GameOfLife;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import GameOfLifeNotation.*;
import GameOfLifeNotation.Dot;

/**
 * Class to draw the game board. Set the listeners for the panel
 */
public class LifePane extends JPanel {
  private static final long serialVersionUID = 0;

  /** Thread for start the game */
  private Thread theGameThread = null;

  /** Thread for start the bot */
  private Thread theBotThread = null;

  /** Thread for start saved the bot */
  private Thread theSavedBotThread = null;

  private LifeAreaNotation areaNotation = null;

  /** A mathematical model of the game */
  private LifeArea lifeArea = null;

  /** Delay updates generations */
  private int delay = 50;

  /** Size of separator between cells */
  private int cellGap = 1;

  private Color deadCell = new Color(0x008B8B);
  private Color aliveCell = new Color(0xFFFFFF);
  private boolean pressedLeft = false;
  private boolean pressedRight = false;

  /** Creates a panel and adds listeners */
  public LifePane() {
    areaNotation = new LifeAreaNotation();
    setBackground(new Color(0x008080));
    addMouseListener(new MyMouseListener());
    addMouseMotionListener(new MyMouseMotionListener());
  }

  public LifeAreaInformation getAreaInformation() {
    LifeAreaInformation info = new LifeAreaInformation(lifeArea);
    Object dot[] = areaNotation.getAll();
    int x = 0, y = 0;
    for (int i = 0; i < dot.length; i++) {
      x = ((Dot) dot[i]).getX();
      y = ((Dot) dot[i]).getY();
      if (info.getArea()[x][y] == 1) {
        info.getArea()[x][y] = 0;
      }
    }
    return info;
  }

  public LifeAreaNotation getAreaNotation() {
    return areaNotation;
  }

  /** Mouse listener to change the cell state by pressing */
  private class MyMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent event) {}

    public void mouseExited(MouseEvent event) {}

    public void mouseReleased(MouseEvent event) {}

    public void mouseEntered(MouseEvent event) {}

    public void mousePressed(MouseEvent event) {
      if (event.getButton() == MouseEvent.BUTTON1) {
        pressedLeft = true;
        pressedRight = false;
        setCellState(event);
      }
      if (event.getButton() == MouseEvent.BUTTON3) {
        pressedLeft = false;
        pressedRight = true;
        setCellState(event);
      }
    }
  }
  /** Mouse listener to change the cell state by dragged */
  private class MyMouseMotionListener implements MouseMotionListener {

    public void mouseDragged(MouseEvent event) {
      setCellState(event);
    }

    public void mouseMoved(MouseEvent event) {

    }

  }

  public int getCellSize() {
    return lifeArea.getCellSize();
  }

  public int getGapSize() {
    return cellGap;
  }

  /**
   * Method calculates the coordinates of the mouse click and change the state of the cell
   * 
   * @param event
   * @see LifePane#setCellState(MouseEvent)
   */
  private void setCellState(MouseEvent event) {
    if (lifeArea != null) {
      synchronized (lifeArea) {
        int x = event.getX() / (lifeArea.getCellSize() + cellGap);
        int y = event.getY() / (lifeArea.getCellSize() + cellGap);
        if (x >= 0 && y >= 0 && x < lifeArea.getWidth() && y < lifeArea.getHeight()) {
          areaNotation.addDot(new Dot(x, y));
          if (pressedLeft == true) {
            lifeArea.setCell(x, y, (byte) 1);
            repaint();
          }
          if (pressedRight == true) {
            lifeArea.setCell(x, y, (byte) 0);
            repaint();
          }
        }
      }
    }
  }

  public LifeArea getLifeArea() {
    return lifeArea;
  }

  public void initialize(int width, int height) {
    lifeArea = new LifeArea(width, height);
  }

  public void setAreaSize(int width, int height) {
    lifeArea.setField(width, height);
  }

  public void setDelay(int delay) {
    if ((this.delay > 999 && this.delay < delay) || (this.delay < 6 && this.delay > delay)) {
      return;
    }
    else {
      this.delay = delay;
    }
  }

  public int getDelay() {
    return delay;
  }

  public void startSimulation() {
    theGameThread = new Thread(new GameThread());
    theGameThread.start();
  }

  public void clearNotation() {
    areaNotation = null;
  }

  public void startBot() {
    theBotThread = new Thread(new BotThread());
    theBotThread.start();
  }

  public void startSavedBot(LifeAreaNotation notation) {
    areaNotation = notation;
    theSavedBotThread = new Thread(new SavedBotThread());
    theSavedBotThread.start();
  }

  public void stopSimulation() {
    theGameThread = null;
  }

  public void stopBot() {
    theBotThread = null;
  }

  public void stopSavedBot() {
    theSavedBotThread = null;
  }

  public boolean isGameSimulating() {
    return theGameThread != null;
  }

  public boolean isBotSimulating() {
    return theBotThread != null;
  }

  public boolean isSavedBotSimulating() {
    return theSavedBotThread != null;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension((lifeArea.getCellSize() + cellGap) * lifeArea.getWidth() + cellGap,
        (lifeArea.getCellSize() + cellGap) * lifeArea.getHeight() + cellGap);
  }

  /**
   * Method for rendering content pane
   * 
   * @param g
   */
  @Override
  protected void paintComponent(Graphics g) {
    synchronized (lifeArea) {
      super.paintComponent(g);
      for (int y = 0; y < lifeArea.getHeight(); y++) {
        for (int x = 0; x < lifeArea.getWidth(); x++) {
          byte c = lifeArea.getCell(x, y);
          g.setColor(c == 1 ? aliveCell : deadCell);
          g.fillRect(cellGap + x * (lifeArea.getCellSize() + cellGap),
              cellGap + y * (lifeArea.getCellSize() + cellGap), lifeArea.getCellSize(),
              lifeArea.getCellSize());
        }
      }
    }
  }

  /** Thread to run the game */
  private class GameThread implements Runnable {
    public void run() {
      while (theGameThread != null) {
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        synchronized (lifeArea) {
          lifeArea.createNextGeneration();
        }
        repaint();
      }
      repaint();
    }
  }

  /** Thread to run the bot */
  private class BotThread implements Runnable {

    public BotThread() {
      areaNotation = new LifeAreaNotation();
    }

    public void run() {
      while (theBotThread != null) {
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        synchronized (lifeArea) {
          System.out.println("x");
          int X = (int) (lifeArea.getWidth() * Math.random());
          int Y = (int) (lifeArea.getHeight() * Math.random());
          lifeArea.setCell(X, Y, (byte) 1);
          areaNotation.addDot(new Dot(X, Y));
        }
        repaint();
      }
      repaint();
    }

  }

  private class SavedBotThread implements Runnable {
    public void run() {
      int x = 0, y = 0;
      Dot dot = null;
      if (areaNotation == null)
        return;
      while (theSavedBotThread != null) {
        if (areaNotation.isEmpty())
          return;
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        synchronized (lifeArea) {
          System.out.println("x");
          dot = areaNotation.popDot();
          x = dot.getX();
          y = dot.getY();
          lifeArea.setCell(x, y, (byte) 1);
        }
        repaint();
      }
      repaint();
    }

  }
}
