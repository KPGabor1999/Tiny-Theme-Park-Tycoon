package infrastructureTests;

import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
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
public class TrashCanTest {
    TrashCan trashCan;
    
    @Test
    public void corectUseOfTrashCan(){
        //Hozz l�tre egy �res szemeteskuk�t.
        trashCan = new TrashCan(0,0,null);
        //Tedd tele szem�ttel.
        trashCan.use(trashCan.getCapacity());
        assertTrue(trashCan.isFull());
        //Nem lehet t�lt�lteni.
        trashCan.use(trashCan.getCapacity());
        assertEquals((int)trashCan.getCapacity(), (int)trashCan.getFilled());
    }

}
