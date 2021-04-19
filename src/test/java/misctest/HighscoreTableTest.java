package misctest;

import Idlethemeparkworld.misc.Highscore;
import Idlethemeparkworld.view.HighscoreTable;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HighscoreTableTest {
    
    @Test
    public void highscoreTableCreation(){
        ArrayList<Highscore> highScores = new ArrayList<>();
        highScores.add(new Highscore("player1", 10));
        highScores.add(new Highscore("player2", 13));
        highScores.add(new Highscore("player3", 18));
        
        HighscoreTable hs = new HighscoreTable(highScores);
        assertEquals(hs.getColumnCount(), 2);
        assertEquals(hs.getRowCount(), 3);
        assertEquals(hs.getColumnName(0), "Name");
        assertEquals(hs.getValueAt(0, 0), "player1");
        assertEquals(hs.getValueAt(1, 1), 13);
    }
}