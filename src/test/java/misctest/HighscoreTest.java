package misctest;

import Idlethemeparkworld.misc.Highscore;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HighscoreTest {
    
    @Test
    public void highscoreCreation(){
        Highscore hs = new Highscore("name", 11);
        assertEquals(hs.getName(), "name");
        assertEquals(hs.getScore(), 11);
        assertEquals(hs.toString(), "Highscore{name=name, score=11}");
    }
}