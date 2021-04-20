package agenttest;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.ENTERINGPARK;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.FIXING;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.IDLE;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.WANDERING;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.attraction.Carousel;
import Idlethemeparkworld.model.buildable.attraction.FerrisWheel;
import Idlethemeparkworld.model.buildable.attraction.HauntedMansion;
import Idlethemeparkworld.model.buildable.attraction.RollerCoaster;
import Idlethemeparkworld.model.buildable.attraction.SwingingShip;
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
public class MaintainerTest {
    Park park;
    Building currentBuilding;
    Maintainer maintainer;
    
    @Before
    public void initPark(){
        //Inicializ�lj egy parkot.
        park = new Park(1,1,null);
        //Oda rakd le a karbantart�t.
        maintainer = new Maintainer(null, park, null);
    }
    
    public void correctLifeCycle(){
        //Update-eld, am�g j�rk�lni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, maintainer.getState());
        maintainer.update(0);
        assertEquals(IDLE, maintainer.getState());
        maintainer.update(0);
        assertEquals(WANDERING, maintainer.getState());
        //Megjav�tja a k�rhint�t?
        park.build(BuildType.CAROUSEL, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        assertEquals(FIXING, maintainer.getState());    //�szreveszi, hogy az attrakci� jav�t�sra szorul.
        maintainer.update(0);                           //Megjav�tja.
        assertEquals(0, (int)((Carousel)currentBuilding).getCondition());
        //Megjav�tja az �ri�skereket?
        park.build(BuildType.FERRISWHEEL, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((FerrisWheel)currentBuilding).getCondition());
        //Megjav�tja a r�ms�gek h�z�t?
        park.build(BuildType.HAUNTEDMANSION, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((HauntedMansion)currentBuilding).getCondition());
        //Megjav�tja a hull�mvasutat?
        park.build(BuildType.ROLLERCOASTER, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((RollerCoaster)currentBuilding).getCondition());
        //Megjav�tja a haj�hint�t?
        park.build(BuildType.SWINGINGSHIP, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((SwingingShip)currentBuilding).getCondition());
        //Ellen�rizd, hogy a jav�t�sok ut�n vissza�ll-e WANDERING-be.
        assertEquals(WANDERING, maintainer.getState());
    }
    
    //Tesztelj�k minden attrakci�ra?
}
