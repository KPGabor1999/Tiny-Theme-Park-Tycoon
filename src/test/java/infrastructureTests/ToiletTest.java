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
        //Létrehozunk egy parkot, egy mosdót és egy látogatót.
        park = new Park(1, 1, null);
        toilet = new Toilet(0, 0, null);
        visitor = new Visitor(null, 100, park, null);
        //A látogató beáll a sorba.
        toilet.joinQueue(visitor);
        assertEquals(1, toilet.getQueueLength());
        //Õ az elsõ a sorban.
        assertTrue(toilet.isFirstInQueue(visitor));
        //Végül elhagyja a sort.
        toilet.leaveQueue(visitor);
        assertEquals(0, toilet.getQueueLength());
    }
}

