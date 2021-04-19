package utilTests;

import Idlethemeparkworld.misc.utils.Direction;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DirectionTest {
  
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
    
    @Test
    public void randomTest(){
        dir = Direction.randomDirection();
        assertTrue(Arrays.asList(Direction.values()).contains(dir));
    }
}
