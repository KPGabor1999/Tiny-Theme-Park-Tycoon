import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Tile;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.*;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
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
public class JanitorTest{
    Park park;
    Building entrance;
    Janitor janitor;
    
    @BeforeClass
    public void initPark(){
        //Inicializálj egy parkot, amiben csak egy Entrance van.
        park = new Park(1,1,null);
        //Az Entrance legyen teleszemetelve.
        entrance = park.getTile(0,0).getBuilding();
        ((Entrance)entrance).setLittering(100);
        //Oda rakd le a takarítót.
        janitor = new Janitor("Ephran", null, null);
    }
    
    public void correctLifeCycle(){
        //Update-eld, amíg járkálni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, janitor.getState());
        janitor.update(0);
        assertEquals(IDLE, janitor.getState());
        janitor.update(0);
        assertEquals(WANDERING, janitor.getState());
        //Várd meg még kitakarítja a bejáratot, majd ellenõrizd, hogy tényleg tiszta lett-e.
        janitor.update(0);
        assertEquals(0, ((Entrance)entrance).getLittering());
        //Ellenõrizd, hogy a takarító visszaállt-e WANDERING-be.
        assertEquals(WANDERING, janitor.getState());
    }
}
