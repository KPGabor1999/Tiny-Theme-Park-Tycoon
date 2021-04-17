package utilTests;

import Idlethemeparkworld.misc.utils.Direction;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LevelTest {
  
    private Direction dir;
    
    @Test
    public void turnLeftTest(){
        dir = Direction.DOWN;
        dir = dir.turnLeft();
        
        assertEquals(dir, Direction.RIGHT);
    }
    
    @Test
    public void turnRightTest(){
        dir = Direction.LEFT;
        dir = dir.turnRight();
        
        assertEquals(dir, Direction.UP);
    }
    
    @Test
    public void turnOppositeTest(){
        dir = Direction.DOWN;
        dir = dir.turnOpposite();
        
        assertEquals(dir, Direction.UP);
    }
}
