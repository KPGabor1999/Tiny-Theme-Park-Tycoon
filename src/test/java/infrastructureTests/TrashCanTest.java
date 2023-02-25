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
        //Hozz létre egy üres szemeteskukát.
        trashCan = new TrashCan(0,0,null);
        //Tedd tele szeméttel.
        trashCan.use(trashCan.getCapacity());
        assertTrue(trashCan.isFull());
        //Nem lehet túltölteni.
        trashCan.use(trashCan.getCapacity());
        assertEquals((int)trashCan.getCapacity(), (int)trashCan.getFilled());
    }

}
