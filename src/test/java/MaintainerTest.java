import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.ENTERINGPARK;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.IDLE;
import static Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState.WANDERING;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
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
    Building attraction;
    Maintainer maintainer;
    
    @BeforeClass
    public void initPark(){
        //Inicializ�lj egy parkot, amiben csak egy Attrakci� van.
        park = new Park(1,1,null);
        park.build(BuildType.ROLLERCOASTER, 0, 0, true);
        //Az attrakci� legyen tot�lk�ros legyen teleszemetelve.
        attraction = park.getTile(0,0).getBuilding();
        ((Attraction)attraction).setCondition(0);
        //Oda rakd le a karbantart�t.
        maintainer = new Maintainer(null, null, null);
    }
    
    public void correctLifeCycle(){
        //Update-eld, am�g j�rk�lni nem kezd: ENTERINGPARK -> IDLE -> WANDERING
        assertEquals(ENTERINGPARK, maintainer.getState());
        maintainer.update(0);
        assertEquals(IDLE, maintainer.getState());
        maintainer.update(0);
        assertEquals(WANDERING, maintainer.getState());
        //V�rd meg m�g kitakar�tja a bej�ratot, majd ellen�rizd, hogy t�nyleg tiszta lett-e.
        maintainer.update(0);
        assertEquals(0, ((Attraction)attraction).getCondition());
        //Ellen�rizd, hogy a takar�t� vissza�llt-e WANDERING-be.
        assertEquals(WANDERING, maintainer.getState());
    }
    
    //Tesztelj�k minden attrakci�ra?
}
