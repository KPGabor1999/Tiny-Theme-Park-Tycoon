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
        //Inicializálj egy parkot.
        park = new Park(1,1,null);
        //Oda rakd le a karbantartót.
        maintainer = new Maintainer(null, park, null);
    }
    
    public void correctLifeCycle(){
        //Update-eld, amíg járkálni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, maintainer.getState());
        maintainer.update(0);
        assertEquals(IDLE, maintainer.getState());
        maintainer.update(0);
        assertEquals(WANDERING, maintainer.getState());
        //Megjavítja a körhintát?
        park.build(BuildType.CAROUSEL, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        assertEquals(FIXING, maintainer.getState());    //Észreveszi, hogy az attrakció javításra szorul.
        maintainer.update(0);                           //Megjavítja.
        assertEquals(0, (int)((Carousel)currentBuilding).getCondition());
        //Megjavítja az óriáskereket?
        park.build(BuildType.FERRISWHEEL, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((FerrisWheel)currentBuilding).getCondition());
        //Megjavítja a rémségek házát?
        park.build(BuildType.HAUNTEDMANSION, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((HauntedMansion)currentBuilding).getCondition());
        //Megjavítja a hullámvasutat?
        park.build(BuildType.ROLLERCOASTER, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((RollerCoaster)currentBuilding).getCondition());
        //Megjavítja a hajóhintát?
        park.build(BuildType.SWINGINGSHIP, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Attraction)currentBuilding).setCondition(0);
        maintainer.update(0);
        maintainer.update(0);
        assertEquals(0, (int)((SwingingShip)currentBuilding).getCondition());
        //Ellenőrizd, hogy a javítások után visszaáll-e WANDERING-be.
        assertEquals(WANDERING, maintainer.getState());
    }
    
    //Teszteljük minden attrakcióra?
}
