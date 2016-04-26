package GameOfLifeStatistics;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import GameOfLife.Form;

public class StatisticTable {
  JDialog dialog = null;
  JTable table = null;
  private int figureCount = 7;
  
  public StatisticTable(String name, int[] answer, int max){
    dialog = new JDialog();
    dialog.setTitle(name);
    DefaultTableModel model = new DefaultTableModel
        (new Object[] {"Name", "Count"}, 0);
    table = new JTable(model);
    
    for(int i = 0; i < figureCount; i++){
      model.addRow(new Object[] {Form.getName(i), answer[i]});
    }
    
    model.addRow(new Object[] {"Most common: " + Form.getName(max), answer[max]});
    
    dialog.add(new JScrollPane(table));
    dialog.setSize(new Dimension(300, 200));
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.setVisible(true);
  }
}
