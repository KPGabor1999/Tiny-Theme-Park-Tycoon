package utilTests;

import Idlethemeparkworld.misc.utils.Direction;
import Idlethemeparkworld.misc.utils.Position;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PositionTest {
    
    @Test
    public void positionCreation(){
        Position p = new Position();
        assertEquals(p.x, 0);
        assertEquals(p.y, 0);
    }
    
    @Test
    public void setterTest(){
        Position p = new Position(5,5);
        assertEquals(p.x, 5);
        assertEquals(p.y, 5);
        p.setCoords(10, 2);
        assertEquals(p.x, 10);
        assertEquals(p.y, 2);
    }
    
    @Test
    public void transformTest(){
        Position p = new Position(5,5);
        assertEquals(p.translate(Direction.UP).x, 5);
        assertEquals(p.translate(Direction.UP).y, 4);
        assertEquals(p.lerp(new Position(0,0), 0.8), new Position(1,1));
    }
    
    @Test
    public void equalityTest(){
        assertNotEquals(new Position(1,1), new Position(2,1));
        assertNotEquals(new Position(1,1), new Position(1,2));
        assertEquals(new Position(1,1), new Position(1,1));
        assertTrue(new Position(1,1).hashCode() == new Position(1,1).hashCode());
        assertNotEquals(new Position(1,1), "DUM");
        assertNotEquals(new Position(1,1), null);
        
        Position p = new Position(1,1);
        Position p2 = p;
        assertEquals(p,p2);
        
        assertEquals(new Position(1,1).toString(), "(x=1, y=1)");
    }
}
