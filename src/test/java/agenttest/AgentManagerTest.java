package agenttest;

import Idlethemeparkworld.model.GameManager;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AgentManagerTest {
    
    @Test
    public void highscoreTableCreation(){
        GameManager gm = new GameManager();
        gm.getAgentManager().manageJanitors(1);
        assertEquals(gm.getAgentManager().getJanitors().size(), 1);
        gm.getAgentManager().manageJanitors(0);
        assertEquals(gm.getAgentManager().getJanitors().size(), 0);
        gm.getAgentManager().manageMaintainers(1);
        assertEquals(gm.getAgentManager().getMaintainers().size(), 1);
        gm.getAgentManager().manageMaintainers(0);
        assertEquals(gm.getAgentManager().getMaintainers().size(), 0);
        gm.getAgentManager().update(0);
        gm.getAgentManager().reset();
    }
}
