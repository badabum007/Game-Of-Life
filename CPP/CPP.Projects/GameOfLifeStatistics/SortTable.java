package GameOfLifeStatistics;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import GameInformation.GameInfo;

/** Sorted game table */
public class SortTable {
  JDialog dialog = null;
  JTable table = null;

  public SortTable(String name, GameInfo info[], String time) {
    dialog = new JDialog();
    dialog.setTitle(name);
    DefaultTableModel model = new DefaultTableModel(new Object[] {"File", "Moves"}, 0);
    table = new JTable(model);
    for (GameInfo gameInfo : info) {
      model.addRow(new Object[] {(gameInfo.getName()), gameInfo.getFigureCount()});
    }
    dialog.setLayout(new BorderLayout());
    dialog.add(new JLabel("Time: " + time), BorderLayout.NORTH);
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    dialog.setTitle("Sort");
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.setVisible(true);

  }
}
