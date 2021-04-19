package infrastructureTests;

import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KrazyXL
 */
public class ToiletTest {
    Park park;
    Toilet toilet;
    Visitor visitor;
    
    @Test
    public void correctQueueManagement(){
        //L�trehozunk egy parkot, egy mosd�t �s egy l�togat�t.
        park = new Park(1, 1, null);
        toilet = new Toilet(0, 0, null);
        visitor = new Visitor(null, 100, park, null);
        //A l�togat� be�ll a sorba.
        toilet.joinQueue(visitor);
        assertEquals(1, toilet.getQueueLength());
        //� az els� a sorban.
        assertTrue(toilet.isFirstInQueue(visitor));
        //V�g�l elhagyja a sort.
        toilet.leaveQueue(visitor);
        assertEquals(0, toilet.getQueueLength());
    }
}

