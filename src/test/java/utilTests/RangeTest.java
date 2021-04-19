package utilTests;

import Idlethemeparkworld.misc.utils.Range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RangeTest {
    
    @Test
    public void rangeCreation(){
        Range r = new Range(0,10);
        assertEquals(r.getLow(), 0);
        assertEquals(r.getHigh(), 10);
        r.setRange(5, 16);
        assertEquals(r.getLow(), 5);
        assertEquals(r.getHigh(), 16);
    }
    
    @Test
    public void rangeTransform(){
        Range r = new Range(0,10);
        r.add(3, 4);
        assertEquals(r.getLow(), 3);
        assertEquals(r.getHigh(), 14);
        assertTrue(r.inRange(3));
        assertTrue(r.inRange(8));
        assertTrue(r.inRange(14));
        assertFalse(r.inRange(2));
        assertFalse(r.inRange(15));
    }
    
    @Test
    public void randomTest(){
        Range r = new Range(0,10);
        for (int i = 0; i < 15; i++) {
            int randomNumber = r.getNextRandom();
            assertTrue(r.getLow() <= randomNumber && randomNumber <= r.getHigh());
        }
    }
}
