import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Tile;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.*;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
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
public class JanitorTest{
    Park park;
    Building currentBuilding;
    Janitor janitor;
    
    @Before
    public void initPark(){
        //Inicializ�lj egy parkot, amiben csak egy Entrance van.
        park = new Park(1,1,null);
        //Az Entrance legyen teleszemetelve.
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Entrance)currentBuilding).setLittering(100);
        //Oda rakd le a takar�t�t.
        janitor = new Janitor(null, park, null);
    }
    
    @Test
    public void correctLifeCycle(){
        //Update-eld, am�g j�rk�lni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, janitor.getState());
        janitor.update(0);
        assertEquals(IDLE, janitor.getState());
        janitor.update(0);
        assertEquals(CLEANING, janitor.getState());
        //V�rd meg m�g kitakar�tja a bej�ratot, majd ellen�rizd, hogy t�nyleg tiszta lett-e.
        janitor.update(0);
        assertEquals(0, (int)((Entrance)currentBuilding).getLittering());
        //Kitakar�tja a j�rd�t is?
        park.build(BuildType.PAVEMENT, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        janitor.update(0);
        assertEquals(0, (int)((Pavement)currentBuilding).getLittering());
        //Kitakar�tja a mosd�t is?
        park.build(BuildType.TOILET, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        janitor.update(0);
        assertEquals(0, (int)((Toilet)currentBuilding).getLittering());
        //Ki�r�ti a szemeteseket is?
        park.build(BuildType.TRASHCAN, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        janitor.update(0);
        assertEquals(0, (int)((TrashCan)currentBuilding).getLittering());
        //Ellen�rizd, hogy takar�t�s ut�n vissza�ll-e WANDERING-be.
        assertEquals(WANDERING, janitor.getState());
    }
}
