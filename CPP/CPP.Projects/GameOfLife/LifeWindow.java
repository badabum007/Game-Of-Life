package GameOfLife;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

import GameInformation.*;
import GameOfLifeNotation.Figure;
import GameOfLifeNotation.LifeAreaInformation;
import GameOfLifeNotation.LifeAreaNotation;
import GameOfLifeStatistics.JavaSort;
import GameOfLifeStatistics.ScalaSort;
import GameOfLifeStatistics.ScalaStatistic;
import GameOfLifeStatistics.SortTable;
import GameOfLifeStatistics.NotationGenerator;
import GameOfLifeStatistics.NotationTransformer;

/** Class render the window contents */
public class LifeWindow {

  private LifePane lifePane = null;
  private JFrame frame = null;
  private JMenuBar menuBar = null;
  private JMenuItem startStopButton = null;
  private JMenuItem startStopBotButton = null;
  private JMenuItem startStopSavedBotButton = null;
  private JMenuItem clearButton = null;
  private JLabel delay = null;
  private LifeAreaNotation areaNotation = null;

  /**
   * It draws the window and associates it with listeners
   * 
   * @param title window title
   */
  public LifeWindow(String title) {
    frame = new JFrame(title);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    lifePane = new LifePane();
    lifePane.initialize(100, 70);
    frame.add(lifePane);

    menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    menuFile.setPreferredSize(new Dimension(40, 20));
    JMenuItem itemOpen = new JMenuItem("Open");
    itemOpen.addActionListener(new OpenButtonListener());
    JMenuItem itemSave = new JMenuItem("Save");
    itemSave.addActionListener(new SaveButtonListener());
    JMenuItem itemBotSave = new JMenuItem("Save bot");
    itemBotSave.addActionListener(new SaveBotButtonListener());
    JMenuItem itemBotOpen = new JMenuItem("Open saved bot");
    itemBotOpen.addActionListener(new OpenBotButtonListener());
    JMenuItem itemRand = new JMenuItem("Random Field");
    itemRand.addActionListener(new RandomButtonListener());
    JMenuItem itemQuit = new JMenuItem("Quit");
    itemQuit.addActionListener(new QuitButtonListener());
    menuFile.add(itemOpen);
    menuFile.add(itemSave);
    menuFile.addSeparator();
    menuFile.add(itemBotOpen);
    menuFile.add(itemBotSave);
    menuFile.addSeparator();
    menuFile.add(itemRand);
    menuFile.addSeparator();
    menuFile.add(itemQuit);

    menuBar.add(menuFile);

    menuBar.setBackground(new Color(0x48D1CC));
    frame.add(menuBar, BorderLayout.NORTH);

    startStopButton = new JMenuItem("Let be life");
    startStopButton.addActionListener(new StartStopButtonListener());
    startStopBotButton = new JMenuItem("Start bot");
    startStopBotButton.addActionListener(new StartStopBotButtonListener());
    startStopSavedBotButton = new JMenuItem("Start saved bot");
    startStopSavedBotButton.addActionListener(new StartStopSavedBotButtonListener());
    clearButton = new JMenuItem("Clear");
    clearButton.addActionListener(new ClearButtonListener());
    JMenu menuGame = new JMenu("Game");
    menuGame.add(startStopButton);
    menuGame.add(startStopBotButton);
    menuGame.add(startStopSavedBotButton);
    menuGame.add(clearButton);
    menuBar.add(menuGame);

    JMenu menuSettings = new JMenu("Window");

    JMenu menuSize = new JMenu("Size");
    JMenuItem smallSize = new JMenuItem("Small");
    JMenuItem middleSize = new JMenuItem("Middle");
    JMenuItem bigSize = new JMenuItem("Big");
    smallSize.addActionListener(new SizeButtonListener());
    middleSize.addActionListener(new SizeButtonListener());
    bigSize.addActionListener(new SizeButtonListener());
    menuSize.add(smallSize);
    menuSize.add(middleSize);
    menuSize.add(bigSize);

    JMenu menuCellSize = new JMenu("Cell size");
    JMenuItem tinyCellSize = new JMenuItem("Tiny");
    JMenuItem smallCellSize = new JMenuItem("Small");
    JMenuItem middleCellSize = new JMenuItem("Middle");
    JMenuItem bigCellSize = new JMenuItem("Big");
    tinyCellSize.addActionListener(new SizeCellButtonListener());
    smallCellSize.addActionListener(new SizeCellButtonListener());
    middleCellSize.addActionListener(new SizeCellButtonListener());
    bigCellSize.addActionListener(new SizeCellButtonListener());
    menuCellSize.add(tinyCellSize);
    menuCellSize.add(smallCellSize);
    menuCellSize.add(middleCellSize);
    menuCellSize.add(bigCellSize);

    JMenu itemLookAndFeel = new JMenu("Look and Feel");
    JMenuItem metalLookAndFeel = new JMenuItem("Metal");
    metalLookAndFeel.addActionListener(new ChangeLookAndFeelListener());
    JMenuItem systemLookAndFeel = new JMenuItem("System");
    systemLookAndFeel.addActionListener(new ChangeLookAndFeelListener());
    itemLookAndFeel.add(metalLookAndFeel);
    itemLookAndFeel.add(systemLookAndFeel);

    menuSettings.add(menuSize);
    menuSettings.add(menuCellSize);
    menuSettings.add(itemLookAndFeel);
    menuBar.add(menuSettings);

    JMenu statistics = new JMenu("Statistics");
    JMenuItem javaSortButton = new JMenuItem("Sort in Java");
    javaSortButton.addActionListener(new JavaSortButtonListener());
    JMenuItem scalaSortButton = new JMenuItem("Sort in Scala");
    scalaSortButton.addActionListener(new ScalaSortButtonListener());
    JMenuItem transformNotation = new JMenuItem("Transform notation");
    transformNotation.addActionListener(new NotationTransformator());

    JMenu saveGenerator = new JMenu("Save generator");
    JMenuItem ten = new JMenuItem("10");
    ten.addActionListener(new SaveGeneratorListener());
    JMenuItem thousand = new JMenuItem("1000");
    thousand.addActionListener(new SaveGeneratorListener());
    JMenuItem tenThousand = new JMenuItem("10000");
    tenThousand.addActionListener(new SaveGeneratorListener());

    saveGenerator.add(ten);
    saveGenerator.add(thousand);
    saveGenerator.add(tenThousand);

    JMenuItem gameStatistics = new JMenuItem("Statistics");
    gameStatistics.addActionListener(new GameStatistictsListener());

    statistics.add(gameStatistics);
    statistics.add(saveGenerator);
    statistics.add(javaSortButton);
    statistics.add(scalaSortButton);
    statistics.add(transformNotation);

    menuBar.add(statistics);


    delay = new JLabel();
    delay.setText("  Delay:" + lifePane.getDelay());
    menuBar.add(delay);


    frame.addKeyListener(new MyKeyListener());
    frame.pack();
    frame.setVisible(true);
  }

  /** Start/stop game thread */
  private class StartStopButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if (lifePane.isGameSimulating()) {
        lifePane.stopSimulation();
        startStopSavedBotButton.setEnabled(true);
        startStopButton.setText("Start");
      } else {
        lifePane.startSimulation();
        startStopSavedBotButton.setEnabled(false);
        startStopButton.setText("Stop");
      }
    }
  }

  /** Start/stop botgame thread */
  private class StartStopBotButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if (lifePane.isBotSimulating()) {
        lifePane.stopBot();
        if (lifePane.isGameSimulating() == false) {
          startStopButton.setEnabled(true);
        }
        startStopSavedBotButton.setEnabled(true);
        startStopBotButton.setText("Start bot");
      } else {
        lifePane.startBot();
        if (lifePane.isGameSimulating() == false) {
          startStopButton.setEnabled(false);
        }
        startStopSavedBotButton.setEnabled(false);
        startStopBotButton.setText("Stop bot");
      }
    }
  }

  /** Start/stop saved botgame thread */
  private class StartStopSavedBotButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if (areaNotation == null)
        return;
      if (lifePane.isSavedBotSimulating()) {
        lifePane.stopSavedBot();
        startStopButton.setEnabled(true);
        startStopBotButton.setEnabled(true);
        startStopSavedBotButton.setText("Start saved bot");
      } else {
        startStopSavedBotButton.setText("Stop saved bot");
        startStopButton.setEnabled(false);
        startStopBotButton.setEnabled(false);
        lifePane.startSavedBot(areaNotation);
      }
    }
  }


  /** Clearing gamefield */
  private class ClearButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        lifePane.getLifeArea().clear();
        lifePane.repaint();
      }
    }
  }
  /** Saving gamefield */
  private class SaveButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        try {
          String homeDir = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Player";
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File(homeDir));
          fileChooser.showSaveDialog(frame);
          File file = fileChooser.getSelectedFile();
          file = new File(file.getPath() + ".player");
          FileOutputStream fileStream = new FileOutputStream(file);
          ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

          objectStream.writeObject(new LifeAreaInformation(lifePane.getLifeArea()));

          objectStream.close();
        }

        catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  /** Saving botgame */
  private class SaveBotButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if (lifePane.getAreaNotation().isEmpty())
        return;
      synchronized (lifePane.getLifeArea()) {
        try {
          String homeDir = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File(homeDir));
          fileChooser.showSaveDialog(frame);
          File temp = fileChooser.getSelectedFile();
          String fileName = temp.getPath().replaceAll(".bot", "");
          fileName = fileName.replaceAll(".not", "");
          File file = new File(fileName + ".bot");
          FileOutputStream fileStream = new FileOutputStream(file);
          ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
          objectStream.writeObject(lifePane.getAreaInformation());

          File notation = new File(fileName + ".not");
          FileWriter fileWriter = new FileWriter(notation);
          fileWriter.write(Integer.toString(lifePane.getLifeArea().getWidth()));
          fileWriter.write("/");
          fileWriter.write(Integer.toString(lifePane.getLifeArea().getHeight()));
          fileWriter.write("\n");
          Figure figure = null;
          while (lifePane.getAreaNotation().isEmpty() == false) {
            figure = lifePane.getAreaNotation().popFigure();
            fileWriter.write(Integer.toString(figure.getX()));
            fileWriter.write("/");
            fileWriter.write(Integer.toString(figure.getY()));
            fileWriter.write("/");
            fileWriter.write(Integer.toString(figure.getNumber()));
            fileWriter.write("\n");
          }
          fileWriter.write("");

          fileWriter.close();
          objectStream.close();
        }

        catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  /** Opening saved game */
  private class OpenButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        try {
          String homeDir = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Player";
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File(homeDir));
          fileChooser.showOpenDialog(frame);
          File file = fileChooser.getSelectedFile();

          FileInputStream fileStream = new FileInputStream(file);
          ObjectInputStream objectStream = new ObjectInputStream(fileStream);
          LifeAreaInformation areaInformation = new LifeAreaInformation();
          areaInformation = (LifeAreaInformation) objectStream.readObject();
          lifePane.getLifeArea().setField(areaInformation);
          frame.setPreferredSize(new Dimension(
              lifePane.getLifeArea().getWidth() * (lifePane.getCellSize() + lifePane.getGapSize())
                  + 2 * lifePane.getGapSize() + 5,
              lifePane.getLifeArea().getHeight() * (lifePane.getCellSize() + lifePane.getGapSize())
                  + 2 * lifePane.getGapSize() + 50));
          frame.pack();
          lifePane.repaint();

          objectStream.close();
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  /** Opening saved bot */
  private class OpenBotButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        try {
          String homeDir = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File(homeDir));
          fileChooser.showOpenDialog(frame);
          File temp = fileChooser.getSelectedFile();
          String fileName = temp.getPath().replaceAll(".bot", "");
          fileName = fileName.replaceAll(".not", "");
          File file = new File(fileName + ".bot");
          ObjectInputStream objectStream = null;
          if (file.exists()) {
            FileInputStream fileStream = new FileInputStream(file);
            objectStream = new ObjectInputStream(fileStream);
            LifeAreaInformation areaInformation = new LifeAreaInformation();
            areaInformation = (LifeAreaInformation) objectStream.readObject();
            lifePane.getLifeArea().setField(areaInformation);
          }
          frame.setPreferredSize(new Dimension(
              lifePane.getLifeArea().getWidth() * (lifePane.getCellSize() + lifePane.getGapSize())
                  + 2 * lifePane.getGapSize() + 5,
              lifePane.getLifeArea().getHeight() * (lifePane.getCellSize() + lifePane.getGapSize())
                  + 2 * lifePane.getGapSize() + 50));
          frame.pack();
          lifePane.repaint();
          File notation = new File(fileName + ".not");
          areaNotation = new LifeAreaNotation();
          BufferedReader reader = new BufferedReader(new FileReader(notation));
          String tempStr = null;
          String coordinates[] = new String[3];
          tempStr = reader.readLine();
          while (true) {
            tempStr = reader.readLine();
            if (tempStr == null)
              break;
            coordinates = tempStr.split("/");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int number = Integer.parseInt(coordinates[2]);
            areaNotation.addFigure(new Figure(x, y, number));
          }
          reader.close();
          if (objectStream != null) {
            objectStream.close();
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  /** Filled field randomly */
  private class RandomButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        lifePane.getLifeArea().randomField();
        lifePane.repaint();
      }
    }
  }
  /** Change field size */
  private class SizeButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        areaNotation = null;
        int COEFFICIENT = 10 / lifePane.getLifeArea().getCellSize();
        int width = 0, height = 0;

        switch (event.getActionCommand()) {
          case "Small":
            width = 40 * COEFFICIENT;
            height = 25 * COEFFICIENT;
            break;

          case "Middle":
            width = 70 * COEFFICIENT;
            height = 45 * COEFFICIENT;
            break;

          case "Big":
            width = 100 * COEFFICIENT;
            height = 60 * COEFFICIENT;
            break;
        }

        lifePane.getLifeArea().setField(width, height);
        frame.setPreferredSize(new Dimension(
            lifePane.getLifeArea().getWidth() * (lifePane.getCellSize() + lifePane.getGapSize())
                + 2 * lifePane.getGapSize() + 5,
            lifePane.getLifeArea().getHeight() * (lifePane.getCellSize() + lifePane.getGapSize())
                + 2 * lifePane.getGapSize() + 50));
        frame.pack();
        lifePane.repaint();
      }
    }

  }
  /** Change cells size */
  private class SizeCellButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      synchronized (lifePane.getLifeArea()) {
        int size = 10;

        switch (event.getActionCommand()) {
          case "Tiny":
            size = 2;
            break;

          case "Small":
            size = 4;
            break;

          case "Middle":
            size = 5;
            break;

          case "Big":
            size = 10;
            break;
        }

        lifePane.getLifeArea().setCellSize(size);
        frame.setPreferredSize(new Dimension(
            lifePane.getLifeArea().getWidth() * (lifePane.getCellSize() + lifePane.getGapSize())
                + 2 * lifePane.getGapSize() + 5,
            lifePane.getLifeArea().getHeight() * (lifePane.getCellSize() + lifePane.getGapSize())
                + 2 * lifePane.getGapSize() + 50));
        frame.pack();
        lifePane.repaint();
      }
    }

  }

  private class QuitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      frame.setVisible(false);
      System.exit(0);
    }
  }

  /** Change look and feel */
  private class ChangeLookAndFeelListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      try {
        if (event.getActionCommand() == "System") {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          SwingUtilities.updateComponentTreeUI(frame);
        } else {
          MetalLookAndFeel look = new MetalLookAndFeel();
          SwingUtilities.updateComponentTreeUI(frame);
          UIManager.setLookAndFeel(look);
        }
      } catch (Exception ex) {
      }
    }
  }

  private static final FilenameFilter filter = new FilenameFilter() {
    public boolean accept(File pathname, String name) {
      String temp = name.toLowerCase();
      if (temp.endsWith(".not")) {
        return true;
      } else {
        return false;
      }
    }
  };

  /**
   * Gathering of game statistics
   */
  private class GameStatistictsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String tempStr = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
      String[] oneMove = null;
      File[] files = new File(tempStr).listFiles(filter);
      GameInfo[] gameInfo = new GameInfo[files.length];

      int figureCount = 0;
      int[] everyFigureCount;

      BufferedReader reader;
      try {
        for (int i = 0; i < files.length; i++) {
          everyFigureCount = new int[Form.count];
          reader = new BufferedReader(new FileReader(files[i]));
          reader.readLine();
          while (true) {
            tempStr = reader.readLine();
            if (tempStr == null) {
              break;
            }
            figureCount++;
            oneMove = tempStr.split("/");
            everyFigureCount[Integer.parseInt(oneMove[2])]++;

          }
          gameInfo[i] = new GameInfo(files[i].getName(), figureCount, everyFigureCount);
          figureCount = 0;
        }

      } catch (IOException ex) {
        ex.printStackTrace();
      }
      new ScalaStatistic().getStatistic(gameInfo);
    }
  }

  private class JavaSortButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      int figureCount = 0;
      String path = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
      File[] files = new File(path).listFiles(filter);
      GameInfo[] gameInfo = new GameInfo[files.length];

      JavaSort javaSort = new JavaSort();
      long time = System.currentTimeMillis();

      try {
        for (int i = 0; i < files.length; i++) {
          String fileName = files[i].getName();
          BufferedReader reader = new BufferedReader(new FileReader(files[i]));
          while (reader.readLine() != null) {
            figureCount++;
          }
          gameInfo[i] = new GameInfo(fileName, figureCount);
          figureCount = 0;
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      javaSort.qSort(gameInfo, 0, gameInfo.length - 1);

      time = System.currentTimeMillis() - time;
      new SortTable("Java", gameInfo, Long.toString(time));

    }
  }

  private class ScalaSortButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      int figureCount = 0;
      String path = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
      File[] files = new File(path).listFiles(filter);
      GameInfo[] gameInfo = new GameInfo[files.length];

      ScalaSort scalaSort = new ScalaSort();
      long time = System.currentTimeMillis();

      try {
        for (int i = 0; i < files.length; i++) {
          String fileName = files[i].getName();
          BufferedReader reader = new BufferedReader(new FileReader(files[i]));
          while (reader.readLine() != null) {
            figureCount++;
          }
          gameInfo[i] = new GameInfo(fileName, figureCount);
          figureCount = 0;
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      scalaSort.sort(gameInfo);

      time = System.currentTimeMillis() - time;
      new SortTable("Scala", gameInfo, Long.toString(time));
    }

  }

  private class NotationTransformator implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String homeDir = "C:\\Users\\Матвей\\Documents\\GameOfLifeFields\\Bot";
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(homeDir));
      fileChooser.showOpenDialog(frame);
      File temp = fileChooser.getSelectedFile();
      String fileName = temp.getPath().replaceAll(".bot", "");
      fileName = fileName.replaceAll(".not", "");
      File notation = new File(fileName + ".not");

      NotationTransformer transformer = new NotationTransformer();
      int data[] = new int[3];
      int size = 0;
      try {
        BufferedReader reader = new BufferedReader(new FileReader(notation));
        String tempStr = null;
        String coordinates[] = new String[3];
        while (true) {
          tempStr = reader.readLine();
          if (tempStr == null) {
            System.out.println(transformer.parse(size));
            break;
          }
          size++;
          coordinates = tempStr.split("/");
          data[0] = Integer.parseInt(coordinates[0]);
          data[1] = Integer.parseInt(coordinates[1]);
          data[2] = coordinates.length == 3 ? Integer.parseInt(coordinates[2]) : -1;
          System.out.println(transformer.parse(data));
        }
        reader.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

  }

  private class SaveGeneratorListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      NotationGenerator.GenerateSaves(Integer.parseInt(event.getActionCommand()));
    }

  }


  /** Change rendering speed. Press UP to speed up the rendering */
  private class MyKeyListener implements KeyListener {
    public void keyPressed(KeyEvent event) {

      if (event.getKeyCode() == KeyEvent.VK_C) {
        lifePane.getLifeArea().clear();
        lifePane.repaint();
      }

      if (event.getKeyCode() == KeyEvent.VK_SPACE) {
        lifePane.repaint();
      }

      if (event.getKeyCode() == KeyEvent.VK_UP) {
        lifePane.setDelay(lifePane.getDelay() - 1);
        delay.setText("  Delay:" + lifePane.getDelay());
      }

      if (event.getKeyCode() == KeyEvent.VK_DOWN) {
        lifePane.setDelay(lifePane.getDelay() + 1);
        delay.setText("  Delay:" + lifePane.getDelay());
      }
    }

    public void keyTyped(KeyEvent event) {}

    public void keyReleased(KeyEvent event) {}
  }
}
