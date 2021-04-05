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
        //Sz�val, janitor specifikus adattagok...
    }

    @Override
    public void update(long tickCount) {
        
        //Randomra j�rk�l fel al�, �s ha infrastruktur�lis mez�re l�p, kitakar�tja.
        //Minden eg�sz �rakor levonjuk az �rab�r�t.
    }
    
}
