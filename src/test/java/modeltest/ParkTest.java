package modeltest;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ParkTest {

    @Test
    public void parkCreation(){
        GameManager gm = new GameManager();
        Park p = gm.getPark();
        assertEquals(p.getHeight(), 10);
        assertEquals(p.getWidth(), 10);
        gm = new GameManager();
        p = new Park(6,gm);
        assertEquals(p.getHeight(), 6);
        assertEquals(p.getWidth(), 6);
    }
    
    @Test
    public void canBuildTest(){
        GameManager gm = new GameManager();
        Park p = gm.getPark();
        p.build(BuildType.PAVEMENT, 1, 0, false);
        p.build(BuildType.PAVEMENT, 2, 0, false);
        p.build(BuildType.PAVEMENT, 3, 0, false);
        p.build(BuildType.TOILET, 3, 1, false);
        assertTrue(p.canBuild(BuildType.TOILET, 2, 1));
        assertFalse(p.canBuild(BuildType.CAROUSEL, 0, 0));
        assertTrue(p.canBuild(BuildType.CAROUSEL, 4, 0));
        assertFalse(p.canBuild(BuildType.CAROUSEL, 6, 6));
        //assertEquals(p.getBuildings().size(), 45);
    }
    
    @Test
    public void getterTest(){
        GameManager gm = new GameManager();
        Park p = gm.getPark();
        p.build(BuildType.PAVEMENT, 1, 0, false);
        p.build(BuildType.PAVEMENT, 2, 0, false);
        p.build(BuildType.PAVEMENT, 3, 0, false);
        p.build(BuildType.PAVEMENT, 3, 1, false);
        p.build(BuildType.TOILET, 4, 0, false);
        assertEquals(p.getPavementNeighbours(2,1).size(), 2);
        assertEquals(p.getNonPavementOrEntranceNeighbours(4,1).size(), 1);
        assertEquals(p.getNonPavementNeighbours(4,1).size(), 1);
    }
    
    @Test
    public void buildTest(){
        GameManager gm = new GameManager();
        Park p = gm.getPark();
        p.build(BuildType.TOILET, 0, 0, false);
        Building pave1 = p.build(BuildType.PAVEMENT, 1, 0, false);
        assertTrue(p.getTile(1, 0).getBuilding().getInfo() == BuildType.PAVEMENT);
        p.build(null, 2, 0, true);
        Building pave2 = p.build(BuildType.PAVEMENT, 2, 0, false);
        Building toilet = p.build(BuildType.TOILET, 2, 1, false);
        p.demolish(1, 0);
        assertTrue(pave2.getStatus() == BuildingStatus.FLOATING);
        pave1 = p.build(BuildType.PAVEMENT, 1, 0, false);
    }
    
    @Test
    public void calculateTest(){
        GameManager gm = new GameManager();
        Park p = new Park(10,10,gm);
        p.build(BuildType.PAVEMENT, 1, 0, false);
        p.build(BuildType.PAVEMENT, 2, 0, false);
        p.build(BuildType.PAVEMENT, 3, 0, false);
        p.build(BuildType.PAVEMENT, 3, 1, false);
        p.build(BuildType.TOILET, 2, 1, false);
        p.build(BuildType.BURGERJOINT, 4, 0, false);
        assertEquals(p.getValue(), 34000);
        p.getRating();
        assertEquals(p.getActiveValue(), 34000);
        p.getPathfinding();
        assertEquals(p.getMaxGuest(), 19);
        p.update(0);
        assertEquals(p.checkFoodPrice(BuildType.BURGERJOINT), 15);
        assertEquals(p.checkFoodPrice(BuildType.HOTDOGSTAND), 0);
        p.updateFoodPrice(BuildType.BURGERJOINT, 20);
        assertEquals(p.checkFoodPrice(BuildType.BURGERJOINT), 20);
    }
}
