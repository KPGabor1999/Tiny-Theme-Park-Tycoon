package agenttest;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.*;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import org.junit.Before;
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
        //Inicializálj egy parkot, amiben csak egy Entrance van.
        park = new Park(1,1,null);
        //Az Entrance legyen teleszemetelve.
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Entrance)currentBuilding).setLittering(100);
        //Oda rakd le a takarítót.
        janitor = new Janitor(null, park, null);
    }
    
    @Test
    public void correctLifeCycle(){
        //Update-eld, amíg járkálni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, janitor.getState());
        janitor.update(0);
        assertEquals(IDLE, janitor.getState());
        janitor.update(0);
        assertEquals(WANDERING, janitor.getState());
        //Várd meg még kitakarítja a bejáratot, majd ellenõrizd, hogy tényleg tisztább lett-e.
        while(janitor.getState() != AgentState.IDLE){
            janitor.update(0);
        }
        assertTrue((int)((Entrance)currentBuilding).getLittering() < 100);
        //Kitakarítja a járdát is?
        park.build(BuildType.PAVEMENT, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        while(janitor.getState() != AgentState.IDLE){
            janitor.update(0);
        }
        assertTrue((int)((Pavement)currentBuilding).getLittering() < 100);
        //Kitakarítja a mosdót is?
        park.build(BuildType.TOILET, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        while(janitor.getState() != AgentState.IDLE){
            janitor.update(0);
        }
        assertTrue((int)((Toilet)currentBuilding).getLittering() < 100);
        //Kiüríti a szemeteseket is?
        park.build(BuildType.TRASHCAN, 0, 0, true);
        currentBuilding = park.getTile(0,0).getBuilding();
        ((Infrastructure)currentBuilding).setLittering(100);
        janitor.update(0);
        janitor.update(0);
        assertEquals(0, (int)((TrashCan)currentBuilding).getFilled());
        assertEquals(CLEANING, janitor.getState());
    }
}
