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
        //Inicializ�lj egy parkot, amiben csak egy Entrance van.
        park = new Park(1,1,null);
        //Az Entrance legyen teleszemetelve.
        entrance = park.getTile(0,0).getBuilding();
        ((Entrance)entrance).setLittering(100);
        //Oda rakd le a takar�t�t.
        janitor = new Janitor("Ephran", null, null);
    }
    
    public void correctLifeCycle(){
        //Update-eld, am�g j�rk�lni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, janitor.getState());
        janitor.update(0);
        assertEquals(IDLE, janitor.getState());
        janitor.update(0);
        assertEquals(WANDERING, janitor.getState());
        //V�rd meg m�g kitakar�tja a bej�ratot, majd ellen�rizd, hogy t�nyleg tiszta lett-e.
        janitor.update(0);
        assertEquals(0, ((Entrance)entrance).getLittering());
        //Ellen�rizd, hogy a takar�t� vissza�llt-e WANDERING-be.
        assertEquals(WANDERING, janitor.getState());
    }
}
