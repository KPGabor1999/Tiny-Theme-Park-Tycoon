package Idlethemeparkworld.view;

import Idlethemeparkworld.misc.Highscore;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class HighscoreWindow extends JDialog {

    public HighscoreWindow(ArrayList<Highscore> campaignHighscores, ArrayList<Highscore> sandboxHighscores, JFrame parent) {
        super(parent, true);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        
        JTable table1 = new JTable(new HighscoreTable(campaignHighscores));
        table1.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(table1.getModel());
        List<RowSorter.SortKey> sortKeys1 = new ArrayList<>();
        sortKeys1.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys1.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter1.setSortKeys(sortKeys1);
        table1.setRowSorter(sorter1);
        
        JTable table2 = new JTable(new HighscoreTable(sandboxHighscores));
        table2.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter2 = new TableRowSorter<>(table2.getModel());
        List<RowSorter.SortKey> sortKeys2 = new ArrayList<>();
        sortKeys2.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sortKeys2.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter2.setSortKeys(sortKeys2);
        table2.setRowSorter(sorter2);
        
        tabbedPane.addTab("Campaign", new JScrollPane(table1));
        tabbedPane.addTab("Sandbox", new JScrollPane(table2));

        add(tabbedPane);
        setSize(400, 250);
        setTitle("Leaderboards");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
