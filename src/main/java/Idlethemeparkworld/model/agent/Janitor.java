package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Updatable;
import java.awt.Color;

/**
 *
 * @author KrazyXL
 */
public class Janitor extends Agent implements Updatable{
    
    public Janitor(String name, Park park, AgentManager am){
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        this.color = Color.WHITE;
    }

    @Override
    public void update(long tickCount) {
        //Randomra járkál fel alá, és ha infrastrukturális mezõre lép, kitakarítja.
        //Minden egész órakor levonjuk az órabérét.
        
    }
    
}
