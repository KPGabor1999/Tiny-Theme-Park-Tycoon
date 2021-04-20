package modeltest;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PathFindingTest {
    
    @Test
    public void getterTest(){
        GameManager gm = new GameManager();
        Park p = gm.getPark();
        p.build(BuildType.PAVEMENT, 1, 0, false);
        p.build(BuildType.PAVEMENT, 2, 0, false);
        p.build(BuildType.PAVEMENT, 3, 0, false);
        p.build(BuildType.PAVEMENT, 3, 1, false);
        Building dest = p.build(BuildType.TOILET, 4, 1, false);
        ArrayList<Position> path = p.getPathfinding().getPath(new Position(2,0), dest);
        assertEquals(path.size(), 3);
        assertEquals(path.get(0), new Position(3, 0));
        assertEquals(path.get(1), new Position(3, 1));
        assertEquals(path.get(2), new Position(4, 1));
        path = p.getPathfinding().getPath(new Position(2,0), p.getTile(0, 0).getBuilding());
        assertEquals(path.size(), 2);
    }
}
