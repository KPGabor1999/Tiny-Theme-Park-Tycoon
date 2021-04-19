package misctest;

import Idlethemeparkworld.misc.pathfinding.Node;
import Idlethemeparkworld.misc.utils.Position;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NodeTest {
    
    @Test
    public void nodeCreation(){
        Node node = new Node(1, null, new Position(1,1));
        assertEquals(node.distance, 1);
        assertEquals(node.current, new Position(1,1));
        assertEquals(node.parent, null);
    }
}
