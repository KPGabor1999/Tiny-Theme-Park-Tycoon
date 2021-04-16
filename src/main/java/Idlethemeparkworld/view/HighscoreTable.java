package Idlethemeparkworld.view;

import Idlethemeparkworld.misc.Highscore;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class HighscoreTable extends AbstractTableModel {

    private final ArrayList<Highscore> highScores;
    private final String[] colName = new String[]{"Name", "Score"};

    public HighscoreTable(ArrayList<Highscore> highScores) {
        this.highScores = highScores;
    }

    @Override
    public int getRowCount() {
        return highScores.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int r, int c) {
        Highscore h = highScores.get(r);
        if (c == 0) {
            return h.getName();
        } else {
            return h.getScore();
        }
    }

    @Override
    public String getColumnName(int i) {
        return colName[i];
    }

}
