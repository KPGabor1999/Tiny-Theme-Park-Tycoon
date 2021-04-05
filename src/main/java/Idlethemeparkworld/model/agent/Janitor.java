package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Updatable;

/**
 *
 * @author KrazyXL
 */
public class Janitor extends Agent implements Updatable{
    
    public Janitor(String name, Park park, AgentManager am){
        super(name, 100, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        //Szóval, janitor specifikus adattagok...
    }

    @Override
    public void update(long tickCount) {
        
        //Randomra járkál fel alá, és ha infrastrukturális mezõre lép, kitakarítja.
        //Minden egész órakor levonjuk az órabérét.
    }
    
}
