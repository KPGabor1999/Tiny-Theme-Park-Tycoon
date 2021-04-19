package utilTests;

import Idlethemeparkworld.misc.utils.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PairTest {
    
    @Test
    public void pairCreation(){
        Pair p = new Pair("key", "value");
        assertEquals(p.getKey(), "key");
        assertEquals(p.getValue(), "value");
    }
    
    @Test
    public void equalityTest(){
        assertNotEquals(new Pair("1", "1"), new Pair("1", "2"));
        assertNotEquals(new Pair("1", "1"), new Pair("2", "1"));
        assertEquals(new Pair("1", "1"), new Pair("1", "1"));
        assertTrue(new Pair("1", "1").hashCode() == new Pair("1", "1").hashCode());
        assertNotEquals(new Pair("1", "1"), "DUM");
        assertNotEquals(new Pair("1", "1"), null);
        
        Pair p = new Pair(1,1);
        Pair p2 = p;
        assertEquals(p,p2);
        
        assertNotEquals(new Pair("1", "1").toString(), "1-1");
    }
}
